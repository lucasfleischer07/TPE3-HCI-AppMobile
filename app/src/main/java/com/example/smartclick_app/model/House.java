package com.example.smartclick_app.model;

import java.util.ArrayList;

public class House {
    private final String id;
    private ArrayList<Room> rooms;
    private String name;

    public House(String id, String name) {
        this.id = id;
        this.name = name;
        rooms=new ArrayList<Room>();
    }
    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void changeName(String name){
        this.name=name;
    }
}
