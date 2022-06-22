package com.example.smartclick_app;

import android.app.Application;

import androidx.room.Room;

import com.example.smartclick_app.data.AppExecutors;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.remote.ApiClient;
import com.example.smartclick_app.data.remote.device.ApiDeviceService;
import com.example.smartclick_app.data.remote.house.ApiHomeService;
import com.example.smartclick_app.data.remote.room.ApiRoomService;
import com.example.smartclick_app.data.remote.routine.ApiRoutineService;


public class MyApplication extends Application {

    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;
    HouseRepository houseRepository;
    RoutineRepository routineRepository;
    DeviceRepository deviceRepository;

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public HouseRepository getHouseRepository(){
        return houseRepository;
    }

    public RoutineRepository getRoutineRepository() {
        return routineRepository;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
        roomRepository = new RoomRepository(appExecutors, roomService, database);

        ApiHomeService houseService = ApiClient.create(ApiHomeService.class);
        houseRepository = new HouseRepository(appExecutors, houseService, database);

        ApiRoutineService routineService = ApiClient.create(ApiRoutineService.class);
        routineRepository = new RoutineRepository(appExecutors, routineService, database);

        ApiDeviceService deviceService = ApiClient.create(ApiDeviceService.class);
        deviceRepository = new DeviceRepository(appExecutors, deviceService, database);
    }
}
