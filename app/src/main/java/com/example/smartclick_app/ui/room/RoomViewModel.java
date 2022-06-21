package com.example.smartclick_app.ui.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.smartclick_app.data.AbsentLiveData;
import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.Status;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.RepositoryViewModel;

import java.util.List;


public class RoomViewModel extends RepositoryViewModel<RoomRepository> {

    private final MediatorLiveData<Resource<List<Room>>> rooms = new MediatorLiveData<>();
    private final MutableLiveData<String> roomId = new MutableLiveData<>();
    private final LiveData<Resource<Room>> room;

    public RoomViewModel(RoomRepository repository) {
        super(repository);

        room = Transformations.switchMap(roomId, roomId -> {
            if (roomId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getRoom(roomId);
            }
        });
    }

    public LiveData<Resource<List<Room>>> getRooms() {
        loadRooms();
        return rooms;
    }

    public LiveData<Resource<Room>> getRoom() {
        return room;
    }

    public LiveData<Resource<Room>> addRoom(Room room) {
        return repository.addRoom(room);
    }

    public LiveData<Resource<Room>> modifyRoom(Room room) {
        return repository.modifyRoom(room);
    }

    public LiveData<Resource<Void>> deleteRoom(Room room) {
        return repository.deleteRoom(room);
    }

    public void setRoomId(String roomId) {
        if ((this.roomId.getValue() != null) &&
            (roomId.equals(this.roomId.getValue()))) {
            return;
        }

        this.roomId.setValue(roomId);
    }

    private void loadRooms() {
        rooms.addSource(repository.getRooms(), resource -> {
            if (resource.status == Status.SUCCESS) {
                rooms.setValue(Resource.success(resource.data));
            } else {
                rooms.setValue(resource);
            }
        });
    }
}
