package com.example.smartclick_app.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.local.room.LocalRoom;
import com.example.smartclick_app.data.local.routine.LocalRoutine;
import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;
import com.example.smartclick_app.data.remote.device.ApiDeviceService;
import com.example.smartclick_app.data.remote.device.RemoteAction;
import com.example.smartclick_app.data.remote.device.RemoteDevice;
import com.example.smartclick_app.data.remote.device.RemoteIntAction;
import com.example.smartclick_app.data.remote.device.RemoteStringAction;
import com.example.smartclick_app.data.remote.room.RemoteRoom;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Devices.Oven;
import com.example.smartclick_app.model.Devices.Refrigerator;
import com.example.smartclick_app.model.Devices.Speaker;
import com.example.smartclick_app.model.Room;


import java.util.concurrent.TimeUnit;

public class DeviceRepository {

    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiDeviceService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public DeviceRepository(AppExecutors executors, ApiDeviceService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

//    private Device mapdeviceLocalToModel(LocalDevice local) {
//        return new device(local.id, local.name, local.homeId);
//    }

//    private Localdevice mapdeviceRemoteToLocal(Remotedevice remote) {
//        return new Localdevice(remote.getId(), remote.getName(),remote.getHome().getHouseId());
//    }

//    private device mapdeviceRemoteToModel(Remotedevice remote) {
//        return new device(remote.getId(), remote.getName(),remote.getHome().getHouseId());
//    }

//    private Remotedevice mapdeviceModelToRemote(device model) {
//        Remotedevice remote = new Remotedevice();
//        remote.setId(model.getId());
//        remote.setName(model.getName());
//        RemoteHome remoteHome=new RemoteHome();
//        remoteHome.setHouseId(model.getHomeId());
//        remote.setHome(remoteHome);
//        return remote;
//    }

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
                return new Speaker(device.getId(),device.getName(),device.getState().getVolume(),device.getState().getStatus(),device.getState().getGenre(), device.getState().getSong() == null ? null : device.getState().getSong().getTitle() + " (" +device.getState().getSong().getAlbum() + ")\"", device.getState().getSong() == null ? null : device.getState().getSong().getProgress(),device.getState().getSong() == null ? null : device.getState().getSong().getDuration());
            default:
                return null;
        }
    }

    public LiveData<Resource<Device>> getDevice(String deviceId) {
        Log.d(TAG, "getRoom()");
        return new NetworkBoundResource<Device, LocalRoom, RemoteDevice>(
                executors,
                null,
                null,
                this::mapDeviceRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalRoom local) {
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoom local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteDevice remote) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoom> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteDevice>>> createCall() {
                return service.getDevice(deviceId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> executeAction(String deviceId,String actionName) {
        Log.d(TAG, "executeAction()");
        return new NetworkBoundResource<Void, LocalRoutine, Object>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Object remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
//                return database.routineDao().findById(RoutineId);
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Object>>> createCall() {
                RemoteStringAction action = new RemoteStringAction();
                return service.executeAction(deviceId, actionName,action);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> executeIntAction(String deviceId,String actionName,int parameter) {
        Log.d(TAG, "executeAction()");
        return new NetworkBoundResource<Void, LocalRoutine, Object>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Object remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
//                return database.routineDao().findById(RoutineId);
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Object>>> createCall() {
                RemoteIntAction action = new RemoteIntAction(parameter);
                return service.executeAction(deviceId, actionName,action);
            }
        }.asLiveData();
    }
    public LiveData<Resource<Void>> executeStringAction(String deviceId,String actionName,String parameter) {
        Log.d(TAG, "executeAction()");
        return new NetworkBoundResource<Void, LocalRoutine, Object>(
                executors,
                local -> null,
                remote -> null,
                remote -> null) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return true;
            }

            @Override
            protected boolean shouldPersist(@Nullable Object remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
//                return database.routineDao().findById(RoutineId);
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<Object>>> createCall() {
                RemoteStringAction action = new RemoteStringAction(parameter);
                return service.executeAction(deviceId, actionName,action);
            }
        }.asLiveData();
    }

}
