package com.example.smartclick_app.data;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.smartclick_app.data.remote.ApiResponse;
import com.example.smartclick_app.data.remote.RemoteError;
import com.example.smartclick_app.data.remote.RemoteResult;

import java.util.Objects;
import java.util.function.Function;

public abstract class NetworkBoundResource<modelType, LocalType, RemoteType> {
    private static final String TAG = "data";

    private final AppExecutors appExecutors;
    private final Function<LocalType, modelType> mapLocalTomodel;
    private final Function<RemoteType, LocalType> mapRemoteToLocal;
    private final Function<RemoteType, modelType> mapRemoteTomodel;

    private final MediatorLiveData<Resource<modelType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors,
                                Function<LocalType, modelType> mapLocalTomodel,
                                Function<RemoteType, LocalType> mapRemoteToLocal,
                                Function<RemoteType, modelType> mapRemoteTomodel) {
        this.appExecutors = appExecutors;
        this.mapLocalTomodel = mapLocalTomodel;
        this.mapRemoteToLocal = mapRemoteToLocal;
        this.mapRemoteTomodel = mapRemoteTomodel;

        result.setValue(Resource.loading(null));
        Log.d(TAG,"NetworkBoundResource - loadFromDb()");
        LiveData<LocalType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                Log.d(TAG,"NetworkBoundResource - fetchFromNetwork()");
                fetchFromNetwork(dbSource);
            } else {
                Log.d(TAG,"NetworkBoundResource - no need to fetch network, processing database value");
                result.addSource(dbSource, newData -> {
                    modelType model = (newData != null) ?
                            mapLocalTomodel.apply(newData) :
                            null;
                    setValue(Resource.success(model));
                });
            }
        });
    }

    @MainThread
    private void setValue(Resource<modelType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<LocalType> dbSource) {
        Log.d(TAG,"NetworkBoundResource - fetching API");
        LiveData<ApiResponse<RemoteResult<RemoteType>>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource,
                newData -> {
                    Log.d(TAG,"NetworkBoundResource - processing database value");
                    modelType model = (newData != null) ?
                            mapLocalTomodel.apply(newData) :
                            null;
                    setValue(Resource.loading(model));
                }
        );
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            if (response.getError() != null) {
                Log.d(TAG,"NetworkBoundResource - processing fetch error");
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> {
                            modelType model = (newData != null) ?
                                    mapLocalTomodel.apply(newData) :
                                    null;
                            RemoteError remoteError = response.getError();
                            com.example.smartclick_app.model.Error modelError = new com.example.smartclick_app.model.Error(remoteError.getCode(), remoteError.getDescription().get(0));
                            setValue(Resource.error(modelError, model));
                        }
                );
            } else /*if (response.getData() != null)*/ {
                Log.d(TAG,"NetworkBoundResource - processing fetch response");
                RemoteType remote = processResponse(response);
                if (shouldPersist(remote)) {
                    appExecutors.diskIO().execute(() -> {
                        LocalType local = mapRemoteToLocal.apply(remote);
                        saveCallResult(local);
                        appExecutors.mainThread().execute(() ->
                                // we specially request a new live data,
                                // otherwise we will get immediately last cached value,
                                // which may not be updated with latest results received from network.
                                result.addSource(loadFromDb(),
                                        newData -> {
                                            modelType model = (newData != null) ?
                                                    mapLocalTomodel.apply(newData) :
                                                    mapRemoteTomodel.apply(remote);
                                            setValue(Resource.success(model));
                                        })
                        );
                    });
                } else {
                    appExecutors.mainThread().execute(() -> {
                        modelType model = mapRemoteTomodel.apply(remote);
                        setValue(Resource.success(model));
                    });
                }
            }  /*else {
                appExecutors.mainThread().execute(() ->
                        result.addSource(loadFromDb(),
                                newData -> {
                                    modelType model = mapLocalTomodel.apply(newData);
                                    setValue(Resource.success(model));
                                })
                );
            }*/
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<modelType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RemoteType processResponse(ApiResponse<RemoteResult<RemoteType>> response) {
        return response.getData().getResult();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull LocalType local);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable LocalType local);

    @MainThread
    protected abstract boolean shouldPersist(@Nullable RemoteType remote);

    @NonNull
    @MainThread
    protected abstract LiveData<LocalType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RemoteResult<RemoteType>>> createCall();
}
