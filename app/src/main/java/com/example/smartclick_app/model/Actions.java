package com.example.smartclick_app.model;

import java.io.ObjectStreamException;
import java.util.List;

public class Actions {
    private final String name;
    private final List<Object> params;

    public Actions(String name, List<Object> params){
        this.name=name;
        this.params=params;
    }

    public String getActionName(){
        return name;
    }

    public List<Object> getParams(){
        return params;
    }
}
