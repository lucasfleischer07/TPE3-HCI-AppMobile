package com.example.smartclick_app.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RemoteError {

    public static final int LOCAL_UNEXPECTED_ERROR = 10;

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("description")
    @Expose
    private List<String> description = null;

    public RemoteError()  {
    }

    public RemoteError(Integer code) {
        this.code = code;
    }

    public RemoteError(Integer code, List<String> description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
}