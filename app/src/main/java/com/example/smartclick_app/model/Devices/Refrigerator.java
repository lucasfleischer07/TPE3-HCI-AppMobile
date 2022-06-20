package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Refrigerator extends Device {
    private int temperature;
    private int freezerTemperature;
    private String mode;

    public Refrigerator(String id,String name,String typeId,int temperature,int freezerTemperature, String mode){
        super(id,name,typeId);
        this.temperature=temperature;
        this.freezerTemperature=freezerTemperature;
        this.mode=mode;
    }

    public void changeTemprature(int newTemperature){
        if(temperature!=newTemperature){
            //llamar api
        }
    }

    public void changeFreezerTemprature(int newFreezerTemprature){
        if(freezerTemperature!=newFreezerTemprature) {
            //llamar api
        }
    }

    public void changeMode(String newMode){
        if(!(mode.equals(newMode))) {
            //llamar api
        }
    }



}
