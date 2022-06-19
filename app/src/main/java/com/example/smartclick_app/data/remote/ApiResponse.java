package com.example.smartclick_app.data.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ApiResponse<T> {

    private T data;
    private RemoteError error;

    public T getData() {
        return data;
    }

    public RemoteError getError() {
        return error;
    }

    public ApiResponse(Response<T> response) {
        parseResponse(response);
    }

    public ApiResponse(Throwable throwable) {
        this.error = buildError(throwable.getMessage());
    }

    private void parseResponse(Response<T> response) {
        if (response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if (response.errorBody() == null) {
            this.error = buildError("Missing error body");
            return;
        }

        String message;

        try {
            message = response.errorBody().string();
        } catch (IOException exception) {
            Log.e("API", exception.toString());
            this.error = buildError(exception.getMessage());
            return;
        }

        if (message.trim().length() > 0) {
            Gson gson = new Gson();
            RemoteErrorResult errorResult = gson.fromJson(message, new TypeToken<RemoteErrorResult>() {}.getType());
            this.error = errorResult.getError();
        }
    }

    private static RemoteError buildError(String message) {
        RemoteError error = new RemoteError(RemoteError.LOCAL_UNEXPECTED_ERROR);
        if (message != null) {
            List<String> description = new ArrayList<>();
            description.add(message);
            error.setDescription(description);
        }
        return error;
    }
}







