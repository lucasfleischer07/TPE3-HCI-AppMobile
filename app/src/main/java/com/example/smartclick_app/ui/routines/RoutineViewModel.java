package com.example.smartclick_app.ui.routines;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.smartclick_app.data.AbsentLiveData;
import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.data.Status;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.RepositoryViewModel;

import java.util.List;

public class RoutineViewModel extends RepositoryViewModel<RoutineRepository> {

    private final MediatorLiveData<Resource<List<Routine>>> routines = new MediatorLiveData<>();
    private final MutableLiveData<String> routineId = new MutableLiveData<>();
    //private final LiveData<Resource<Routine>> routine;

    public RoutineViewModel(RoutineRepository repository) {
        super(repository);
        
        /*routine = Transformations.switchMap(routineId, routineId -> {
            if (routineId == null) {
                return AbsentLiveData.create();
            } else {
                return repository.getRoutine(routineId);
            }
        });*/
    }

    public LiveData<Resource<List<Routine>>> getRoutines() {
        //loadRoutines();
        //return routines;
        return repository.getRoutines();
    }

    /*public LiveData<Resource<Routine>> getRoutine() {
        return routine;
    }

     */

//    public LiveData<Resource<Routine>> addRoutine(Routine routine) {
//        return repositor
//    }

//    public LiveData<Resource<Routine>> modifyRoutine(Routine routine) {
//        return repository.modifyRoutine(room);
//    }

//    public LiveData<Resource<Void>> deleteRoutine(Routine routine) {
//        return repository.deleteRoutine(routine);
//    }

//    public void setRoutineId(String roomId) {
//        if ((this.routineId.getValue() != null) &&
//                (roomId.equals(this.routineId.getValue()))) {
//            return;
//        }
//
//        this.routineId.setValue(roomId);
//    }

    public LiveData<Resource<Void>> executeRoutine(String routineId) {
        return repository.executeRoutine(routineId);
    }

    private void loadRoutines() {
        routines.addSource(repository.getRoutines(), resource -> {
            if (resource.status == Status.SUCCESS) {
                routines.setValue(Resource.success(resource.data));
            } else {
                routines.setValue(resource);
            }
        });
    }
    
    
}
