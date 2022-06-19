package com.example.smartclick_app.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteErrorResult {

    @SerializedName("error")
    @Expose
    private RemoteError error;

    public RemoteErrorResult() {
    }

    public RemoteErrorResult(RemoteError result) {
        this.error = result;
    }

    public RemoteError getError() {
        return error;
    }

    public void setError(RemoteError error) {
        this.error = error;
    }
}
