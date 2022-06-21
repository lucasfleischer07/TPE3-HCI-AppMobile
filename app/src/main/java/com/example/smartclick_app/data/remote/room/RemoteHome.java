package com.example.smartclick_app.data.remote.room;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteHome {
    @SerializedName("houseId")
    @Expose
    private String houseId;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }
}
