package com.example.smartclick_app.ui.devices;

import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.DeviceRepository;

import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.ui.RepositoryViewModel;




public class DeviceViewModel extends RepositoryViewModel<DeviceRepository> {



    public DeviceViewModel(DeviceRepository repository) {
        super(repository);
    }

    public LiveData<Resource<Void>> executeRoutine(String routineId,String actionName) {
        return repository.executeAction(routineId,actionName);
    }
}
