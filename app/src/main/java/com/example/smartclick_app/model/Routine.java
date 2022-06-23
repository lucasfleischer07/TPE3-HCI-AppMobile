package com.example.smartclick_app.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Routine {
    private final String id;
    private final String name;
    private final String houseId;
    private final Map<Device,Actions> deviceAndActionsMap = new HashMap<>();

    public Routine(String id, String name, String houseId){
        this.id=id;
        this.name=name;
        this.houseId=houseId;
    }

    public Map<Device, Actions> getDeviceAndActionsMap() {
        return deviceAndActionsMap;
    }

    public String getId() {
            return id;
        }
    
    public String getName() {
        return name;
    }

    public String getHouseId() {
        return houseId;
    }

    public void addDeviceAndActions(Device device,Actions actions){
        getDeviceAndActionsMap().put(device,actions);
    }

}
