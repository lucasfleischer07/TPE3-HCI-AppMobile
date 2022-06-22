package com.example.smartclick_app.data.remote.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteHome {
    @SerializedName("id")
    @Expose
    private String id;

    public String getHouseId() {
        return id;
    }

    public void setHouseId(String houseId) {
        this.id = houseId;
    }
}
