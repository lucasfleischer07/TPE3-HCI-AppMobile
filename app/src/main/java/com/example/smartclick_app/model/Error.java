package com.example.smartclick_app.model;

public class Error {

    private Integer code;
    private String description;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Error(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
