package com.example.smartclick_app.ui.devices;

import androidx.lifecycle.LiveData;

import com.example.smartclick_app.data.DeviceRepository;

import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.ui.RepositoryViewModel;




public class DeviceViewModel extends RepositoryViewModel<DeviceRepository> {



    public DeviceViewModel(DeviceRepository repository) {
        super(repository);
    }

    public LiveData<Resource<Void>> executeDeviceAction(String routineId,String actionName) {
        return repository.executeAction(routineId,actionName);
    }

    public LiveData<Resource<Void>> executeDeviceActionWithInt(String routineId,String actionName,int parameter) {
        return repository.executeIntAction(routineId,actionName,parameter);
    }

    public LiveData<Resource<Void>> executeDeviceActionWithString(String routineId,String actionName,String parameter) {
        return repository.executeStringAction(routineId,actionName,parameter);
    }

}
