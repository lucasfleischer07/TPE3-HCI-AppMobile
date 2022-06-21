package com.example.smartclick_app.ui.room;

import static com.example.smartclick_app.data.Status.LOADING;
import static com.example.smartclick_app.data.Status.SUCCESS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.AppExecutors;
import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.local.MyDatabase;
import com.example.smartclick_app.data.remote.ApiClient;
import com.example.smartclick_app.data.remote.room.ApiRoomService;
import com.example.smartclick_app.ui.MainActivity;
import com.example.smartclick_app.data.Status.*;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DevicesActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment implements Serializable {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final int [] testPassingInfoArray = new int[] {1, 2, 3, 4};


    public static String DATABASE_NAME = "my-db";
    AppExecutors appExecutors;
    RoomRepository roomRepository;

    private MainActivity activity;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DevicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    public RoomRepository getRoomRepository() {
//        return roomRepository;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

//        appExecutors = new AppExecutors();
//        ApiRoomService roomService = ApiClient.create(ApiRoomService.class);
//        MyDatabase database = Room.databaseBuilder(requireContext(), MyDatabase.class, DATABASE_NAME).build();
//        roomRepository = new RoomRepository(appExecutors, roomService, database);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MainActivity activity = (MainActivity)requireActivity();
        MyApplication application = (MyApplication)activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
        RoomViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);
        ViewGroup devicesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_room, container, false);
        LinearLayout generalLinearLayout = devicesViewGroup.findViewById(R.id.roomLinearLayout);

        Log.d("Carga", "Antes del getRooms");
        List<Room> rooms = new ArrayList<>();
//        RoomAdapter adapter = new RoomAdapter(rooms);
        viewModel.getRooms().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
//                    activity.showProgressBar();
                    break;
                case SUCCESS:
//                    activity.hideProgressBar();
                    rooms.clear();
                    if (resource.data != null && resource.data.size() > 0) {
                        rooms.addAll(resource.data);
                        forRooms(rooms, generalLinearLayout);
                    }
                    break;
            }
        });

        Log.d("Carga", "Despues del getRooms");



        return devicesViewGroup;
    }

    private void forRooms(List<Room> rooms, LinearLayout generalLinearLayout){
        for(int i = 0; i < rooms.size() ; i++) {
            Log.d("Carga", "En el for");
            LinearLayout row = new LinearLayout(getContext());

            Button roomButton= new Button(getContext());
            roomButton.setTransformationMethod(null);
            roomButton.setText(rooms.get(i).getName());
            roomButton.setId(i);
            roomButton.setTextSize(25);
            roomButton.setBackgroundColor(roomButton.getContext().getResources().getColor(R.color.main_act_background));
            roomButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            int finalI = i;
            roomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("roomDevices", (Serializable) rooms);
                    Intent intent = new Intent(getContext(), DevicesActivity.class);
//                    intent.putExtra("roomDevices", (Serializable) rooms);

                    startActivity(intent);
//                    TODO: PAsar el mensaje de ha ingrsado a string para traduccion
                    Toast.makeText(getContext(), "Ha ingresado en la habitacion: " + rooms.get(finalI).getName(), Toast.LENGTH_SHORT).show();
                }
            });

            row.setGravity(Gravity.CENTER);
            row.setPadding(50, 30, 50, 1);
            row.addView(roomButton);
            generalLinearLayout.addView(row);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // * Si no se selecciono casa arranca en null
    }
}