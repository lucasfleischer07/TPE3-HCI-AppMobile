package com.example.smartclick_app.data;

import static java.util.stream.Collectors.toList;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.local.routine.LocalRoutine;
import com.example.smartclick_app.data.remote.routine.ApiRoutineService;
import com.example.smartclick_app.data.remote.routine.ApiRoutineService;
import com.example.smartclick_app.data.remote.routine.RemoteRoutinActionsDeviceType;
import com.example.smartclick_app.data.remote.routine.RemoteRoutine;
import com.example.smartclick_app.data.remote.routine.RemoteRoutineActions;
import com.example.smartclick_app.data.remote.routine.RemoteRoutineActionsDevice;
import com.example.smartclick_app.data.remote.routine.RemoteRoutineMeta;
import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;
import com.example.smartclick_app.model.Actions;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Devices.Oven;
import com.example.smartclick_app.model.Devices.Refrigerator;
import com.example.smartclick_app.model.Devices.Speaker;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.model.Routine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RoutineRepository {
    private static final String TAG = "data";
    private static final String RATE_LIMITER_ALL_KEY = "@@all@@";

    private AppExecutors executors;
    private ApiRoutineService service;
    private MyDatabase database;
    private final RateLimiter<String> rateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    public AppExecutors getExecutors() {
        return executors;
    }

    public RoutineRepository(AppExecutors executors, ApiRoutineService service, MyDatabase database) {
        this.executors = executors;
        this.service = service;
        this.database = database;
    }

    private Routine mapRoutineLocalToModel(LocalRoutine local) {
        return new Routine(local.getId(), local.getName(), local.getHouseId());
    }

    private LocalRoutine mapRoutineRemoteToLocal(RemoteRoutine remote) {
        return new LocalRoutine(remote.getId(), remote.getName(), remote.getMeta().getHouseId());
    }

    private Routine mapRoutineRemoteToModel(RemoteRoutine remote) {
        Routine newRoutine = new Routine(remote.getId(), remote.getName(), remote.getMeta().getHouseId());
        Device newDevice;
        for(RemoteRoutineActions actions : remote.getActions()){
            newDevice = new Device(actions.getDevice().getId(),actions.getDevice().getName(),actions.getDevice().getType().getId());
            newRoutine.addDeviceAndActions(newDevice,new Actions(actions.getActionName(),actions.getParams()));
        }
        return newRoutine;
    }

    private RemoteRoutine mapRoutineModelToRemote(Routine model) {

        RemoteRoutine remote = new RemoteRoutine();
        List<RemoteRoutineActions> remoteRoutineActionsList = new ArrayList<>() ;
        for(Map.Entry<Device,Actions> deviceActionsEntry : model.getDeviceAndActionsMap().entrySet()){
            RemoteRoutineActions newRemoteRoutineActions = new RemoteRoutineActions();
            newRemoteRoutineActions.setActionName(deviceActionsEntry.getValue().getActionName());
            RemoteRoutinActionsDeviceType remoteRoutinActionsDeviceType = new RemoteRoutinActionsDeviceType();
            remoteRoutinActionsDeviceType.setId(deviceActionsEntry.getKey().getId());

            RemoteRoutineActionsDevice remoteRoutineActionsDevice = new RemoteRoutineActionsDevice();
            remoteRoutineActionsDevice.setType(remoteRoutinActionsDeviceType);
            remoteRoutineActionsDevice.setId(deviceActionsEntry.getKey().getId());
            remoteRoutineActionsDevice.setName(deviceActionsEntry.getKey().getName());
            newRemoteRoutineActions.setDevice(remoteRoutineActionsDevice);
            newRemoteRoutineActions.setParams(deviceActionsEntry.getValue().getParams());
            remoteRoutineActionsList.add(newRemoteRoutineActions);
        }
        RemoteRoutineMeta remoteMeta = new RemoteRoutineMeta();
        remoteMeta.setHouseId(model.getHouseId());
        remote.setId(model.getId());
        remote.setName(model.getName());
        remote.setMeta(remoteMeta);
        remote.setActions(remoteRoutineActionsList);
        return remote;
    }

    public LiveData<Resource<List<Routine>>> getRoutines() {
        Log.d(TAG, "RoutineRepository - getRoutines()");
        return new NetworkBoundResource<List<Routine>, List<LocalRoutine>, List<RemoteRoutine>>(
                executors,
                null,
                null,
                remotes -> {
                    return remotes.stream()
                            .map(this::mapRoutineRemoteToModel)
                            .collect(toList());
                }) {
            @Override
            protected void saveCallResult(@NonNull List<LocalRoutine> locals) {
                database.routineDao().deleteAll();
                database.routineDao().insert(locals);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<LocalRoutine> locals) {
                return ((locals == null) || (locals.size() == 0) || rateLimit.shouldFetch(RATE_LIMITER_ALL_KEY));
            }

            @Override
            protected boolean shouldPersist(@Nullable List<RemoteRoutine> remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<LocalRoutine>> loadFromDb() {
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> createCall() {
                return service.getRoutines();
            }
        }.asLiveData();
    }

    public LiveData<Resource<Routine>> getRoutine(String RoutineId) {
        Log.d(TAG, "getRoutine()");
        return new NetworkBoundResource<Routine, LocalRoutine, RemoteRoutine>(
                executors,
                this::mapRoutineLocalToModel,
                this::mapRoutineRemoteToLocal,
                this::mapRoutineRemoteToModel) {

            @Override
            protected void saveCallResult(@NonNull LocalRoutine local) {
                database.routineDao().insert(local);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocalRoutine local) {
                return (local == null);
            }

            @Override
            protected boolean shouldPersist(@Nullable RemoteRoutine remote) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<LocalRoutine> loadFromDb() {
                return database.routineDao().findById(RoutineId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> createCall() {
                return service.getRoutine(RoutineId);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Void>> executeRoutine(String RoutineId) {
        Log.d(TAG, "getRoutine()");
        return new NetworkBoundResource<Void, LocalRoutine, Object>(
                executors,
                local->null,
                remote->null,
                remote->null) {

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
                return service.executeRoutine(RoutineId);
            }
        }.asLiveData();

//        private Device mapDeviceRemoteToModel(RemoteDevice device){
//            switch (device.getType().getId())
//            {
//                case Door.TYPE_ID:
//                    return new Door(device.getId(),device.getName(),device.getState().getStatus(), device.getState().getLock());
//                case Lightbulb.TYPE_ID:
//                    return new Lightbulb(device.getId(),device.getName(),device.getState().getColor(), device.getState().getBrightness(),device.getState().getStatus());
//                case Oven.TYPE_ID:
//                    return new Oven(device.getId(),device.getName(),device.getState().getConvection(),device.getState().getGrill(),device.getState().getHeat(),device.getState().getStatus(),device.getState().getTemperature());
//                case Refrigerator.TYPE_ID:
//                    return new Refrigerator(device.getId(),device.getName(),device.getState().getTemperature(),device.getState().getFreezerTemperature(),device.getState().getMode());
//                case Speaker.TYPE_ID:
//                    return new Speaker(device.getId(),device.getName(),device.getState().getVolume(),device.getState().getStatus(),device.getState().getGenre());
//                default:
//                    return null;
//            }
//        }
//
//        public LiveData<Resource<List<Device>>> getRoomDevices(Room room){
//            Log.d(TAG, "RoomRepository - getRoomDevices()");
//            return new NetworkBoundResource<List<Device>, List<LocalRoom>, List<RemoteDevice>>(
//                    executors,
//                    locals -> {
//                        return null;
//                    },
//                    remotes -> {
//                        return null;
//                    },
//                    remotes -> {
//                        return remotes.stream()
//                                .map(this::mapDeviceRemoteToModel)
//                                .collect(toList());
//                    }) {
//                @Override
//                protected void saveCallResult(@NonNull List<LocalRoom> locals) {
//                }
//
//                @Override
//                protected boolean shouldFetch(@Nullable List<LocalRoom> locals) {
//                    return true;
//                }
//
//                @Override
//                protected boolean shouldPersist(@Nullable List<RemoteDevice> remote) {
//                    return false;
//                }
//
//                @NonNull
//                @Override
//                protected LiveData<List<LocalRoom>> loadFromDb() {
//                    return null;
//                }
//
//                @NonNull
//                @Override
//                protected LiveData<ApiResponse<RemoteResult<List<RemoteDevice>>>> createCall() {
//                    return service.getRoomDevices(room.getId());
//                }
//            }.asLiveData();
//        }

    }
}
