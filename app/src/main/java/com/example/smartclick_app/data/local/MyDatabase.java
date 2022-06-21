package com.example.smartclick_app.data.local;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.smartclick_app.data.local.house.HomeDao;
import com.example.smartclick_app.data.local.house.LocalHome;
import com.example.smartclick_app.data.local.room.LocalRoom;
import com.example.smartclick_app.data.local.room.RoomDao;
import com.example.smartclick_app.data.local.routine.LocalRoutine;
import com.example.smartclick_app.data.local.routine.RoutineDao;

@Database(entities = {LocalRoom.class, LocalHome.class, LocalRoutine.class}, exportSchema = true,
        autoMigrations =
        @AutoMigration(
                from = 4,
                to = 5
        ),version = 5)

public abstract class MyDatabase extends RoomDatabase {

    abstract public RoomDao roomDao();
    abstract public HomeDao homeDao();
    abstract public RoutineDao routineDao();
}