package com.example.smartclick_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteStringAction extends RemoteAction<String> {

    public RemoteStringAction(String data){
        super(data);
    }

    public RemoteStringAction() {

    }
}
