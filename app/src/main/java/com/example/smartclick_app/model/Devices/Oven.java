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

    public final static String HEAT_TYPE_BOTTOM = "bottom";
    public final static String HEAT_TYPE_CONVENTIONAL = "conventional";
    public final static String HEAT_TYPE_TOP = "top";
    public final static String GRILL_TYPE_OFF = "off";
    public final static String GRILL_TYPE_ECO = "eco";
    public final static String GRILL_TYPE_LARGE = "large";
    public final static String CONVECTION_TYPE_OFF = "off";
    public final static String CONVECTION_TYPE_ECO = "eco";
    public final static String CONVECTION_TYPE_NORMAL = "normal";


    public Oven(String id,String name,String convection,String grillMode, String heatZone,String status,int temperature){
        super(id,name,TYPE_ID);
        this.convection=convection;
        this.grillMode=grillMode;
        this.heatZone=heatZone;
        this.status=status;
        this.temperature=temperature;
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

}
