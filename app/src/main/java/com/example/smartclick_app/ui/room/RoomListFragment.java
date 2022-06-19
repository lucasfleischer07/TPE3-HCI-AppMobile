//package com.example.smartclick_app.ui.room;
//
//import static com.example.smartclick_app.data.Status.LOADING;
//import static com.example.smartclick_app.data.Status.SUCCESS;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.smartclick_app.MyApplication;
//import com.example.smartclick_app.data.RoomRepository;
//import com.example.smartclick_app.model.Room;
//import com.example.smartclick_app.ui.MainActivity;
//import com.example.smartclick_app.ui.RepositoryViewModelFactory;
//
//import com.example.smartclick_app
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class RoomListFragment extends Fragment {
//
//    FragmentListRoomBinding binding;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        MainActivity activity = (MainActivity)requireActivity();
//        MyApplication application = (MyApplication)activity.getApplication();
//        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
//        com.example.smartclick_app.ui.room.RoomViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(com.example.smartclick_app.ui.room.RoomViewModel.class);
//
//        binding = FragmentListRoomBinding.inflate(getLayoutInflater());
//
//        List<Room> rooms = new ArrayList<>();
//        com.example.smartclick_app.ui.room.RoomAdapter adapter = new com.example.smartclick_app.ui.room.RoomAdapter(rooms);
//        viewModel.getRooms().observe(getViewLifecycleOwner(), resource -> {
//            switch (resource.status) {
//                case LOADING:
//                    activity.showProgressBar();
//                    break;
//                case SUCCESS:
//                    activity.hideProgressBar();
//                    rooms.clear();
//                    if (resource.data != null &&
//                            resource.data.size() > 0) {
//                        rooms.addAll(resource.data);
//                        adapter.notifyDataSetChanged();
//                        binding.list.setVisibility(View.VISIBLE);
//                        binding.empty.setVisibility(View.GONE);
//                    } else {
//                        binding.list.setVisibility(View.GONE);
//                        binding.empty.setVisibility(View.VISIBLE);
//                    }
//                    break;
//            }
//        });
//
//        binding.list.setHasFixedSize(true);
//        binding.list.setLayoutManager(new LinearLayoutManager(activity));
//        binding.list.setAdapter(adapter);
//
//        return binding.getRoot();
//    }
//}
