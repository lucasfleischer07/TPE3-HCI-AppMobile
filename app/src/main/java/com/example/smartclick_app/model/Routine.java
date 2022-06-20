package com.example.smartclick_app.model;

import java.util.List;
import java.util.Map;

public class Routine {
    private final int id;
    private final String name;
    private final int houseId;
    private final Map<Device,List<Actions>> devicesAndActionsMap;

    public Routine(int id,String name,int houseId,Map<Device,List<Actions>> devicesAndActionsMap){
        this.id=id;
        this.name=name;
        this.houseId=houseId;
        this.devicesAndActionsMap=devicesAndActionsMap;
    }

    public void executeRoutine(){
        /*
        boolean succes=llamado a api
        if(succes)
            dar feedback positivo
        else
            dar feedback negativo
         */
    }

}
