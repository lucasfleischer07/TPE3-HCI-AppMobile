package com.example.smartclick_app.data.remote.house;

import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;
import com.example.smartclick_app.data.remote.room.RemoteRoom;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiHomeService {
    @GET("homes")
    LiveData<ApiResponse<RemoteResult<List<RemoteHouse>>>> getHouses();

    @GET("homes/{homeId}")
    LiveData<ApiResponse<RemoteResult<RemoteHouse>>> getHouse(@Path("homeId") String homeId);

    @GET("homes/{homeId}/rooms")
    LiveData<ApiResponse<RemoteResult<List<RemoteRoom>>>> getHouseRooms(@Path("homeId") String homeId);
}
