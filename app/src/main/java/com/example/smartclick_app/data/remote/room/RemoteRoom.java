package com.example.smartclick_app.data.remote.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteRoom {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("meta")
    @Expose
    private RemoteRoomMeta meta;

    public RemoteHome getHome() {
        return home;
    }

    public void setHome(RemoteHome home) {
        this.home = home;
    }

    @SerializedName("home")
    @Expose
    private RemoteHome home;

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

    public void setMeta(RemoteRoomMeta meta){
        this.meta=meta;
    }

}