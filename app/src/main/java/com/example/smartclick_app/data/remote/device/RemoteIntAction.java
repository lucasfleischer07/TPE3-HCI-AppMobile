package com.example.smartclick_app.data.remote.device;

import com.example.smartclick_app.data.remote.room.RemoteHome;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteIntAction {
    @SerializedName("params")
    @Expose
    private int[] params= new int[5];

    public int[] getParams() {
        return params;
    }

    public void setParams(int parameter) {
        this.params[0] = parameter;
    }
}
