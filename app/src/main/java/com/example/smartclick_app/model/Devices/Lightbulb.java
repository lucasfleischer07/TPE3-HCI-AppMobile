package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Lightbulb extends Device {
    private String color;
    private int brightness;
    private String status;
    public final static String TYPE_ID="go46xmbqeomjrsjr";

    public Lightbulb(String id,String name,String color,int brightness,String status){
        super(id,name,TYPE_ID);
        this.color=color;
        this.brightness=brightness;
        this.status=status;
    }

    public void changeBrightness(int newBrightness){
        if(newBrightness!=brightness) {
            //llamar a la api
        }
    }

    public void changeColor(String newColor) {
        if (!newColor.equals(color)) {
            //llamar a la api

        }
    }

    public void change(){
        if(status.equals("on")) {
            //llama a la api con turnOff
        }else{
            //llama a la api on turnOn
        }
    }

}
