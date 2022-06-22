package com.example.smartclick_app.data.remote.device;

import com.example.smartclick_app.data.remote.room.RemoteHome;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RemoteIntAction extends RemoteAction<Integer> {

    public RemoteIntAction(int data){
        super(data);
    }

    public RemoteIntAction(double data){
        super( (int)data);
    }


}
