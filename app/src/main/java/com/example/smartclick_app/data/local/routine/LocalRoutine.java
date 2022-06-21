package com.example.smartclick_app.data.local.routine;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.smartclick_app.model.Actions;
import com.example.smartclick_app.model.Device;

import java.util.List;
import java.util.Map;

@Entity(tableName = "Routine", indices = {@Index("id")}, primaryKeys = {"id"})
public class LocalRoutine {

    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "houseId")
    public String houseId;

    public LocalRoutine(String id, String name,String houseId) {
        this.id = id;
        this.name = name;
        this.houseId=houseId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}