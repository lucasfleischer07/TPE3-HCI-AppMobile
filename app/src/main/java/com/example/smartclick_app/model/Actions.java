package com.example.smartclick_app.model;

import java.util.List;

public class Actions {
    private final String name;
    private final List<String> params;

    public Actions(String name, List<String> params){
        this.name=name;
        this.params=params;
    }

    public String getActionName(){
        return name;
    }

    public List<String> getParams(){
        return params;
    }
}
