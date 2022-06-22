package com.example.smartclick_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteStringAction {
    @SerializedName("params")
    @Expose
    private String[] params = new String[5];

    public String[] getParams() {
        return params;
    }

    public void setParams(String parameter) {
        this.params[0] = parameter;
    }
}
