package com.example.smartclick_app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.smartclick_app.data.AbsentLiveData;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.Status;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.House;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.ui.RepositoryViewModel;

import java.util.List;


public class HouseViewModel extends RepositoryViewModel<HouseRepository> {

    private final MediatorLiveData<Resource<List<House>>> houses = new MediatorLiveData<>();
    private final MutableLiveData<String> houseId = new MutableLiveData<>();
    private final LiveData<Resource<House>> house;

    public HouseViewModel(HouseRepository repository) {
        super(repository);

        house = Transformations.switchMap(houseId, houseId -> {
            if (houseId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getHouse(houseId);
            }
        });
    }

    public LiveData<Resource<List<House>>> gethouses() {
        loadHouses();
        return houses;
    }

    public LiveData<Resource<House>> getHouse() {
        return house;
    }

    /*public LiveData<Resource<Room>> addRoom(Room house) {
        return repository.addRoom(room);
    }

    public LiveData<Resource<Room>> modifyRoom(Room house) {
        return repository.modifyRoom(room);
    }

    public LiveData<Resource<Void>> deleteRoom(Room room) {
        return repository.deleteRoom(room);
    }
*/
    public void setRoomId(String houseId) {
        if ((this.houseId.getValue() != null) &&
            (houseId.equals(this.houseId.getValue()))) {
            return;
        }

        this.houseId.setValue(houseId);
    }

    private void loadHouses() {
        houses.addSource(repository.getHouses(), resource -> {
            if (resource.status == Status.SUCCESS) {
                houses.setValue(Resource.success(resource.data));
            } else {
                houses.setValue(resource);
            }
        });
    }

    /*public LiveData<Resource<List<Device>>> getRoomDevices(Room room){
        return repository.getRoomDevices(room);
    }*/
}
