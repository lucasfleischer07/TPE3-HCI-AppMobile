package com.example.smartclick_app.model;

public class Device {
    public String id;
    public String name;
    public String typeId;

    public Device(String id,String name,String typeId){
        this.id=id;
        this.name=name;
        this.typeId=typeId;
    }
    public void changeName(String name){
        this.name=name;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getTypeId(){
        return typeId;
    }


}
