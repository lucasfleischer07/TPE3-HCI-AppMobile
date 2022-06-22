package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Oven extends Device {
    private String convection;
    private String grillMode;
    private String heatZone;
    private String status;
    private int temperature;
    public final static String TYPE_ID="im77xxyulpegfmv8";
    public final static String ACTION_TURN_ON = "turnOn";
    public final static String ACTION_TURN_OFF = "turnOff";
    public final static String ACTION_SET_TEMPERATURE = "setTemperature";
    public final static String ACTION_SET_HEAT = "setHeat";
    public final static String ACTION_SET_GRILL = "setGrill";
    public final static String ACTION_SET_CONVECTION = "setConvection";


    public Oven(String id,String name,String convection,String grillMode, String heatZone,String status,int temperature){
        super(id,name,TYPE_ID);
        this.convection=convection;
        this.grillMode=grillMode;
        this.heatZone=heatZone;
        this.status=status;
        this.temperature=temperature;
    }

    public void setConvection(String newConvection){
        if (!newConvection.equals(convection)){
            //Llamar a la api
        }

    }

    public String getConvection() {
        return convection;
    }

    public String getGrillMode() {
        return grillMode;
    }

    public String getHeatZone() {
        return heatZone;
    }

    public String getStatus() {
        return status;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setGrillMode(String newGrillMode){
        if (!newGrillMode.equals(grillMode)){
            //Llamar a la api
        }

    }

    public void setHeatZone(String newHeatZone){
        if (!newHeatZone.equals(heatZone)){
            //Llamar a la api
        }

    }

    public void setConvection(int newTemprature){
        if (temperature!=newTemprature){
            //Llamar a la api
        }

    }





}
