package com.example.smartclick_app.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteResult<T> {

    @SerializedName("result")
    @Expose
    private T result = null;

    public RemoteResult() {
    }

    public RemoteResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
