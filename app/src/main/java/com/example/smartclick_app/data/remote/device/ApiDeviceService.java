package com.example.smartclick_app.data.remote.device;

import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;
import com.example.smartclick_app.data.remote.room.RemoteRoom;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiDeviceService {

    @POST("devices/{deviceId}/{actionName}")
    LiveData<ApiResponse<RemoteResult<Object>>> executeActionInt(@Path("deviceId")String deviceId, @Path("actionName")String actionName,@Body RemoteIntAction parameter);

    @POST("devices/{deviceId}/{actionName}")
    LiveData<ApiResponse<RemoteResult<Object>>> executeActionString(@Path("deviceId")String deviceId, @Path("actionName")String actionName,@Body RemoteStringAction parameter);
}
