package com.example.smartclick_app.data.remote.routine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteRoutineMeta {

    @SerializedName("houseId")
    @Expose
    private String houseId;


    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId){
        this.houseId=houseId;
    }

   


}
