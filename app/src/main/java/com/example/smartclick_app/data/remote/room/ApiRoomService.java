package com.example.smartclick_app.data.remote.room;

import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRoomService {

    @GET("rooms")
    LiveData<ApiResponse<RemoteResult<List<RemoteRoom>>>> getRooms();

    @POST("rooms")
    LiveData<ApiResponse<RemoteResult<RemoteRoom>>> addRoom(@Body RemoteRoom room);

    @GET("rooms/{roomId}")
    LiveData<ApiResponse<RemoteResult<RemoteRoom>>> getRoom(@Path("roomId") String roomId);

    @PUT("rooms/{roomId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> modifyRoom(@Path("roomId") String roomId, @Body RemoteRoom room);

    @DELETE("rooms/{roomId}")
    LiveData<ApiResponse<RemoteResult<Boolean>>> deleteRoom(@Path("roomId") String roomId);
}
