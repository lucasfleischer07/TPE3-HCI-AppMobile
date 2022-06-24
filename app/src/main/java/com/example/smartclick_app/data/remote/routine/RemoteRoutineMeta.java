package com.example.smartclick_app.data.remote.routine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteRoutineMeta {

    @SerializedName("homeId")
    @Expose
    private String homeId;

    public String getHouseId() {
        return homeId;
    }

    public void setHouseId(String homeId){
        this.homeId=homeId;
    }

}
