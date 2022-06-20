package com.example.smartclick_app;

import android.app.Application;

import androidx.room.Room;

import com.example.smartclick_app.data.AppExecutors;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.remote.ApiClient;
import com.example.smartclick_app.data.remote.house.ApiHomeService;
import com.example.smartclick_app.data.remote.room.ApiRoomService;


public class MyApplication extends Application {

    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;
    HouseRepository houseRepository;

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public HouseRepository getHouseRepository(){
        return houseRepository;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
        ApiHomeService houseService = ApiClient.create(ApiHomeService.class);
        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).build();

        houseRepository= new HouseRepository(appExecutors,houseService,database);
        roomRepository = new RoomRepository(appExecutors, roomService, database);
    }
}
