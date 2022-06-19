package com.example.smartclick_app;

import android.app.Application;

import androidx.room.Room;

import com.example.smartclick_app.data.AppExecutors;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.remote.ApiClient;
import com.example.smartclick_app.data.remote.room.ApiRoomService;


public class MyApplication extends Application {

    public static String DATABASE_NAME = "my-db";

    AppExecutors appExecutors;
    RoomRepository roomRepository;

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);

        MyDatabase database = Room.databaseBuilder(this, MyDatabase.class, DATABASE_NAME).build();

        roomRepository = new RoomRepository(appExecutors, roomService, database);
    }
}
