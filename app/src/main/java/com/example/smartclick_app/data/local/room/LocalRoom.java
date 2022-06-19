package com.example.smartclick_app.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Room", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalRoom {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;


    public LocalRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }
}