package com.example.smartclick_app.data.remote.routine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.crypto.interfaces.PBEKey;

public class RemoteRoutineActions {
    @SerializedName("device")
    @Expose
    private RemoteRoutineActionsDevice device;
    @SerializedName("actionName")
    @Expose
    private String actionName;
    @SerializedName("params")
    @Expose
    private List<Object> params;

    public RemoteRoutineActionsDevice getDevice() {
        return device;
    }

    public void setDevice(RemoteRoutineActionsDevice device) {
        this.device = device;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}


