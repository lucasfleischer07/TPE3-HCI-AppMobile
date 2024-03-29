package com.example.smartclick_app.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.AppExecutors;
import com.example.smartclick_app.data.NetworkBoundResource;
import com.example.smartclick_app.data.RateLimiter;
import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Device.*;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Devices.Oven;
import com.example.smartclick_app.model.Devices.Refrigerator;
import com.example.smartclick_app.model.Devices.Speaker;
import com.example.smartclick_app.data.remote.room.RemoteHome;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.local.room.LocalRoom;
import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;
import com.example.smartclick_app.data.remote.room.ApiRoomService;
import com.example.smartclick_app.data.remote.room.RemoteRoom;
import com.example.smartclick_app.data.remote.device.RemoteDevice;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RoomRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoomService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(1, TimeUnit.SECONDS);

    public AppExecutors getExecutors() {
        return executors;
    }

    public RoomRepository(AppExecutors executors, ApiRoomService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Room mapRoomLocalToModel(LocalRoom local) {
        return new Room(local.id, local.name, local.homeId);
    }

    private LocalRoom mapRoomRemoteToLocal(RemoteRoom remote) {
        return new LocalRoom(remote.getId(), remote.getName(),remote.getHome().getHouseId());
    }

    private Room mapRoomRemoteToModel(RemoteRoom remote) {
        return new Room(remote.getId(), remote.getName(),remote.getHome().getHouseId());
    }

    private RemoteRoom mapRoomModelToRemote(Room model) {
        RemoteRoom remote = new RemoteRoom();
        remote.setId(model.getId());
        remote.setName(model.getName());
        RemoteHome remoteHome=new RemoteHome();
        remoteHome.setHouseId(model.getHomeId());
        remote.setHome(remoteHome);
        return remote;
    }

    public LiveData<Resource<List<Room>>> getRooms() {
        Log.d(TAG, "RoomRepository - getRooms()");
        return new NetworkBoundResource<List<Room>, List<LocalRoom>, List<RemoteRoom>>(
                executors,
                locals -> {
                    return locals.stream()
                            .map(this::mapRoomLocalToModel)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoomRemoteToLocal)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoomRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoom> locals) {
                database.roomDao().deleteAll();
                database.roomDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoom> locals) {
                return ((locals == null) || (locals.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoom> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoom>> loadFromDb() {
                return database.roomDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoom>>>> createCall() {
                return service.getRooms();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Room>> getRoom(String roomId) {
        Log.d(TAG, "getRoom()");
        return new NetworkBoundResource<Room, LocalRoom, RemoteRoom>(
                executors,
                this::mapRoomLocalToModel,
                this::mapRoomRemoteToLocal,
                this::mapRoomRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalRoom local) {
                database.roomDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoom local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteRoom remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoom> loadFromDb() {
                return database.roomDao().findById(roomId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteRoom>>> createCall() {
                return service.getRoom(roomId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Room>> addRoom(Room room) {
        Log.d(TAG, "RoomRepository - addRoom()");
        return new NetworkBoundResource<Room, LocalRoom, RemoteRoom>(
                executors,
                this::mapRoomLocalToModel,
                this::mapRoomRemoteToLocal,
                this::mapRoomRemoteToModel) {
            String roomId = null;

            @Override
            protected void saveCallResult(@NonNull LocalRoom local) {
                roomId = local.id;
                database.roomDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoom local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteRoom remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoom> loadFromDb() {
                if (roomId == null)
                    return AbsentLiveData.create();
                else
                    return database.roomDao().findById(roomId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteRoom>>> createCall() {
                RemoteRoom remote = mapRoomModelToRemote(room);
                return service.addRoom(remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Room>> modifyRoom(Room room) {
        Log.d(TAG, "RoomRepository - modifyRoom()");
        return new NetworkBoundResource<Room, LocalRoom, Boolean>(
                executors,
                this::mapRoomLocalToModel,
                remote -> {
                    RemoteRoom remote2 = mapRoomModelToRemote(room);
                    return mapRoomRemoteToLocal(remote2);
                },
                remote -> { return room; }) {

            @Override
            protected void saveCallResult(@NonNull LocalRoom local) {
                database.roomDao().update(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoom local) {
                return local != null;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoom> loadFromDb() {
                return database.roomDao().findById(room.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                RemoteRoom remote = mapRoomModelToRemote(room);
                return service.modifyRoom(remote.getId(), remote);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> deleteRoom(Room room) {
        Log.d(TAG, "RoomRepository - deleteRoom()");
        return new NetworkBoundResource<Void, LocalRoom, Boolean>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalRoom local) {
                database.roomDao().delete(room.getId());
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoom local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Boolean remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoom> loadFromDb() {
                return database.roomDao().findById(room.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Boolean>>> createCall() {
                return service.deleteRoom(room.getId());
            }
        }.asLiveData();
    }

    private Device mapDeviceRemoteToModel(RemoteDevice device) {
        switch (device.getType().getId())
        {
            case Door.TYPE_ID:
                return new Door(device.getId(),device.getName(),device.getState().getStatus(), device.getState().getLock());
            case Lightbulb.TYPE_ID:
                return new Lightbulb(device.getId(),device.getName(),device.getState().getColor(), device.getState().getBrightness(),device.getState().getStatus());
            case Oven.TYPE_ID:
                return new Oven(device.getId(),device.getName(),device.getState().getConvection(),device.getState().getGrill(),device.getState().getHeat(),device.getState().getStatus(),device.getState().getTemperature());
            case Refrigerator.TYPE_ID:
                return new Refrigerator(device.getId(),device.getName(),device.getState().getTemperature(),device.getState().getFreezerTemperature(),device.getState().getMode());
            case Speaker.TYPE_ID:
                return new Speaker(device.getId(),device.getName(),device.getState().getVolume(),device.getState().getStatus(),device.getState().getGenre(), device.getState().getSong() == null ? null : device.getState().getSong().getTitle() + " " + " - " + " " + "(" +device.getState().getSong().getAlbum() + ")", device.getState().getSong() == null ? null : device.getState().getSong().getProgress(),device.getState().getSong() == null ? null : device.getState().getSong().getDuration());
            default:
                return null;
        }
    }

    public LiveData<Resource<List<Device>>> getRoomDevices(String roomId){
        Log.d(TAG, "RoomRepository - getRoomDevices()");
        return new NetworkBoundResource<List<Device>, List<LocalRoom>, List<RemoteDevice>>(
                executors,
                locals -> {
                    return null;
                },
                remotes -> {
                    return null;
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapDeviceRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoom> locals) {
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoom> locals) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteDevice> remote) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoom>> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> createCall() {
                return service.getRoomDevices(roomId);
            }
        }.asLiveData();
    }
}

