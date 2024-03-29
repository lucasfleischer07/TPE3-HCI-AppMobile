package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Lightbulb extends Device {
    private String color;
    private int brightness;
    private String status;
    public final static String TYPE_ID="go46xmbqeomjrsjr";
    public final static String ACTION_TURN_ON = "turnOn";
    public final static String ACTION_TURN_OFF = "turnOff";
    public final static String ACTION_SET_COLOR = "setColor";
    public final static String ACTION_SET_BRIGHTNESS = "setBrightness";


    public Lightbulb(String id,String name,String color,int brightness,String status){
        super(id,name,TYPE_ID);
        this.color=color;
        this.brightness=brightness;
        this.status=status;
    }

    public String getColor() {
        return color;
    }

    public int getBrightness() {
        return brightness;
    }

    public String getStatus() {
        return status;
    }

}
