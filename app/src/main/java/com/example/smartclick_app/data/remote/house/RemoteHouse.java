package com.example.smartclick_app.data.remote.house;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteHouse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("meta")
    @Expose
    private RemoteHouseMeta meta;

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

    public RemoteHouseMeta getMeta() {
        return meta;
    }

    public void setMeta(RemoteHouseMeta meta) {
        this.meta = meta;
    }
}
