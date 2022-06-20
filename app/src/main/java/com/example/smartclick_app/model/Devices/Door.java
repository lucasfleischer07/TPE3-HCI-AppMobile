package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Door extends Device {
    private String status;
    private String lock;

    public Door(String id,String name,String typeId,String status,String lock){
        super(id,name,typeId);
        this.status=status;
        this.lock=lock;
    }

    public void close(){
        this.status="closed";
    }
    public void open(){
        if(!this.lock.equals("locked"))
        this.status="opened";
    }
    public void lock(){
        if(!this.status.equals("opened"))
            this.lock="locked";
    }
    public void unlock(){
        this.lock="unlocked";
    }

}
