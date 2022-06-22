package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Door extends Device {
    private String status;
    private String lock;
    public final static String TYPE_ID="lsf78ly0eqrjbz91";
    public final static String ACTION_OPEN = "open";
    public final static String ACTION_CLOSE = "close";
    public final static String ACTION_LOCK = "lock";
    public final static String ACTION_UNLOCK = "unlock";

    public final static String OPEN = "opened";
    public final static String CLOSE = "closed";
    public final static String LOCK = "locked";
    public final static String UNLOCK = "unlocked";



    public Door(String id,String name,String status,String lock){
        super(id,name,TYPE_ID);
        this.status = status;
        this.lock = lock;
    }

    public void setClose(){
        this.status = "closed";
    }

    public void setOpen(){
        if(!this.lock.equals("locked"))
            this.status = "opened";
    }

    public void setLock(){
        if(!this.status.equals("opened"))
            this.lock = "locked";
    }

    public void setUnlock(){
        this.lock = "unlocked";
    }

    public String getDoorStatus() {
        return status;
    }

    public String getDoorLock() {
        return lock;
    }
}
