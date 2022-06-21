package com.example.smartclick_app.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.smartclick_app.model.House;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.local.house.LocalHome;
import com.example.smartclick_app.data.local.room.LocalRoom;
import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;
import com.example.smartclick_app.data.remote.house.ApiHomeService;
import com.example.smartclick_app.data.remote.house.RemoteHouse;
import com.example.smartclick_app.data.remote.house.RemoteHouseMeta;
import com.example.smartclick_app.data.remote.room.RemoteRoom;
import com.example.smartclick_app.data.remote.room.RemoteRoomMeta;
import com.example.smartclick_app.model.House;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HouseRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiHomeService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public HouseRepository(AppExecutors executors, ApiHomeService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private House mapHomeLocalToModel(LocalHome local) {
        return new House(local.id, local.name);
    }

    private LocalHome mapHomeRemoteToLocal(RemoteHouse remote) {
        return new LocalHome(remote.getId(), remote.getName());
    }

    private House mapHomeRemoteToModel(RemoteHouse remote) {
        return new House(remote.getId(), remote.getName());
    }

    private RemoteHouse mapHomeModelToRemote(House model) {
        RemoteHouseMeta remoteMeta = new RemoteHouseMeta();
        RemoteHouse remote = new RemoteHouse();
        remote.setId(model.getId());
        remote.setName(model.getName());
        remote.setMeta(remoteMeta);

        return remote;
    }
    //Necesito los maps de room para el get homeRooms
    private Room mapRoomLocalToModel(LocalRoom local) {
        return new Room(local.id, local.name);
    }

    private LocalRoom mapRoomRemoteToLocal(RemoteRoom remote) {
        return new LocalRoom(remote.getId(), remote.getName(),remote.getHome().getHouseId());
    }

    private Room mapRoomRemoteToModel(RemoteRoom remote) {
        return new Room(remote.getId(), remote.getName(),remote.getHome().getHouseId());
    }

    private RemoteRoom mapRoomModelToRemote(Room model) {
        RemoteRoomMeta remoteMeta = new RemoteRoomMeta();
        RemoteRoom remote = new RemoteRoom();
        remote.setId(model.getId());
        remote.setName(model.getName());
        remote.setMeta(remoteMeta);

        return remote;
    }

    public LiveData<Resource<List<House>>> getHouses() {
        Log.d(TAG, "HouseRepository - getHouses()");
        return new NetworkBoundResource<List<House>, List<LocalHome>, List<RemoteHouse>>(
                executors,
                locals -> {
                    return locals.stream()
                            .map(this::mapHomeLocalToModel)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapHomeRemoteToLocal)
                            .collect(toList());
                },
                remotes -> {
                    return remotes.stream()
                            .map(this::mapHomeRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalHome> locals) {
                database.homeDao().deleteAll();
                database.homeDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalHome> locals) {
                return ((locals == null) || (locals.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteHouse> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalHome>> loadFromDb() {
                return database.homeDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteHouse>>>> createCall() {
                return service.getHouses();
            }
        }.asLiveData();
    }

    public LiveData<Resource<House>> getHouse(String homeId) {
        Log.d(TAG, "getHouse()");
        return new NetworkBoundResource<House, LocalHome, RemoteHouse>(
                executors,
                this::mapHomeLocalToModel,
                this::mapHomeRemoteToLocal,
                this::mapHomeRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalHome local) {
                database.homeDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalHome local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteHouse remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalHome> loadFromDb() {
                return database.homeDao().findById(homeId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteHouse>>> createCall() {
                return service.getHouse(homeId);
            }
        }.asLiveData();
    }

    /*public LiveData<Resource<List<Room>>> getHouseRooms(String homeId) {
        Log.d(TAG, "getHouseRooms()");
        return new NetworkBoundResource<List<Room>, List<LocalRoom>,List<RemoteRoom>>(
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
            protected void saveCallResult(@NonNull List<LocalRoom> local) {
                database.homeDao().insert(local,null);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoom> local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoom> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoom>> loadFromDb() {
                return database.homeDao().findAll(null);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoom>>>> createCall() {
                return service.getHouseRooms(homeId);
            }
        }.asLiveData();
    }

     */






}
