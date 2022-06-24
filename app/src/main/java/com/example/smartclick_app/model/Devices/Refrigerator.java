package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Refrigerator extends Device {
    private int temperature;
    private int freezerTemperature;
    private String mode;
    public final static String TYPE_ID="rnizejqr2di0okho";
    public final static String ACTION_SET_FREZ_TEMP = "setFreezerTemperature";
    public final static String ACTION_SET_TEMP = "setTemperature";
    public final static String ACTION_SET_MODE = "setMode";

    public final static String MODE_DEFAULT = "default";
    public final static String MODE_PARTY = "party";
    public final static String MODE_VACATION = "vacation";

    public final static int MODE_PARTY_TEMPERATURE = 3;
    public final static int MODE_PARTY_TEMPERATURE_FREEZER = -20;
    public final static int MODE_VACATION_TEMPERATURE = 6;
    public final static int MODE_VACATION_TEMPERATURE_FREEZER = -17;
    public final static int MODE_DEFAULT_TEMPERATURE_FREEZER = -17;


    public Refrigerator(String id,String name,int temperature,int freezerTemperature, String mode){
        super(id,name,TYPE_ID);
        this.temperature=temperature;
        this.freezerTemperature=freezerTemperature;
        this.mode=mode;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getFreezerTemperature() {
        return freezerTemperature;
    }

    public String getMode() {
        return mode;
    }

}
