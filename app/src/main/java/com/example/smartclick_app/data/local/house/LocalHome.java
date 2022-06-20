package com.example.smartclick_app.data.local.house;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "Home", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalHome {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;


    public LocalHome(String id, String name) {
        this.id = id;
        this.name = name;
    }
}