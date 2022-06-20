package com.example.smartclick_app.data.local;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.smartclick_app.data.local.house.HomeDao;
import com.example.smartclick_app.data.local.house.LocalHome;
import com.example.smartclick_app.data.local.room.LocalRoom;
import com.example.smartclick_app.data.local.room.RoomDao;

@Database(entities = {LocalRoom.class, LocalHome.class}, exportSchema = true,
        autoMigrations =
        @AutoMigration(
                from = 3,
                to = 4
        ),version = 4)
public abstract class MyDatabase extends RoomDatabase {

    abstract public RoomDao roomDao();
    abstract public HomeDao homeDao();
}