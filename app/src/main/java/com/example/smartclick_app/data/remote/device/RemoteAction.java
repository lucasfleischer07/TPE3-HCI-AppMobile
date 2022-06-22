package com.example.smartclick_app.data.remote.device;

import java.util.ArrayList;

public class RemoteAction<T> extends ArrayList<T> {

    public RemoteAction(T data){
        super();
        add(data);
    }

    public RemoteAction(){
        super();
    }

}
