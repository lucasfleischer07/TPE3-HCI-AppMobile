package com.example.smartclick_app.data.remote.routine;

import androidx.lifecycle.LiveData;

import java.util.List;

import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteResult;


import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiRoutineService {

    @GET("routines")
    LiveData<ApiResponse<RemoteResult<List<RemoteRoutine>>>> getRoutines();

    @GET("routines/{routineId}")
    LiveData<ApiResponse<RemoteResult<RemoteRoutine>>> getRoutine(@Path("routineId") String RoutineId);

    @PUT("routines/{routineId}/execute")
    LiveData<ApiResponse<RemoteResult<Object>>> executeRoutine(@Path("routineId") String RoutineId);

}
