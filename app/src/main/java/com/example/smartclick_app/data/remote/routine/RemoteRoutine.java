package com.example.smartclick_app.data.remote.routine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class RemoteRoutine {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("meta")
    @Expose
    private RemoteRoutineMeta meta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RemoteRoutineMeta getMeta() {
        return meta;
    }

    public void setMeta(RemoteRoutineMeta meta) {
        this.meta = meta;
    }
}