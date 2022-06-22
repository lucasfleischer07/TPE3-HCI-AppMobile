package com.example.smartclick_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDevice {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private RemoteDeviceType type;
    @SerializedName("state")
    @Expose
    private RemoteDeviceState state;
    @SerializedName("meta")
    @Expose
    private RemoteDeviceMeta meta;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public RemoteDeviceType getType() {
        return type;
    }

    public RemoteDeviceState getState() {
        return state;
    }

    public RemoteDeviceMeta getMeta() {
        return meta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(RemoteDeviceType type) {
        this.type = type;
    }

    public void setState(RemoteDeviceState state) {
        this.state = state;
    }

    public void setMeta(RemoteDeviceMeta meta) {
        this.meta = meta;
    }
}

