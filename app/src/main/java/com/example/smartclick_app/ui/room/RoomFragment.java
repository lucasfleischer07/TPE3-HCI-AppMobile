package com.example.smartclick_app.ui.room;

import static com.example.smartclick_app.data.Status.LOADING;
import static com.example.smartclick_app.data.Status.SUCCESS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.model.House;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.model.Device;
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
import com.example.smartclick_app.ui.home.HouseViewModel;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

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


    public static String DATABASE_NAME = "my-db";

    private RoomViewModel viewModel;
    private ViewGroup devicesViewGroup;
    private LinearLayout generalLinearLayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<House> houses;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        houses=new ArrayList<>();
        MainActivity activity = (MainActivity) getActivity();
        MyApplication application = (MyApplication)activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);

        devicesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_room, container, false);
        generalLinearLayout = devicesViewGroup.findViewById(R.id.roomLinearLayout);
        refreshData();
        return devicesViewGroup;
    }


    private void forRooms(List<Room> rooms, LinearLayout generalLinearLayout){

        generalLinearLayout.removeAllViews();
        generalLinearLayout.removeAllViewsInLayout();
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String actualId=preferences.getString("actualHouse",null);
        String actualHouseName=preferences.getString("actualHouseName",null);

        if(actualId==null && houses.size()>0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("actualHouse",houses.get(0).getId());
            editor.putString("actualHouseName",houses.get(0).getName());
            editor.apply();
            actualId=preferences.getString("actualHouse",null);
            actualHouseName=preferences.getString("actualHouseName",null);
        }

            else if(houses.size()==0){
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("actualHouse",null);
                editor.putString("actualHouseName",null);
                editor.apply();
                actualId=preferences.getString("actualHouse",null);
                actualHouseName=preferences.getString("actualHouseName",null);

            }

        int added=0;
        for(int i = 0; i < rooms.size() ; i++) {
            if(rooms.get(i).getHomeId().equals(actualId)){
                added++;
                LinearLayout row = new LinearLayout(getContext());
                MaterialButton roomButton= new MaterialButton(getContext());
                roomButton.setTransformationMethod(null);
                roomButton.setText(rooms.get(i).getName());
                roomButton.setId(i);
                roomButton.setTextSize(25);
                roomButton.setBackgroundColor(roomButton.getContext().getResources().getColor(R.color.rooms_and_routine_buttons));
                roomButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                int finalI = i;
                roomButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomId = rooms.get(finalI).getId();
                        String roomName = rooms.get(finalI).getName();
                        Intent intent = new Intent(getContext(), DevicesActivity.class);
                        intent.putExtra("ROOM_ID", roomId);
                        intent.putExtra("ROOM_NAME", roomName);
                        startActivity(intent);
                        Toast.makeText(getContext(), getString(R.string.room_selected) + " " + rooms.get(finalI).getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                row.setGravity(Gravity.CENTER);
                row.setPadding(50, 30, 50, 1);
                row.addView(roomButton);
                generalLinearLayout.addView(row);
            }
        }

        if(added == 0 || rooms.size()==0) {
            TextView text=new TextView(this.getContext());
            if(actualId != null && actualHouseName!=null)
                text.setText(getString(R.string.house_rooms_null) + " " + actualHouseName);
            else
                text.setText(R.string.house_not_selected);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                text.setTextSize(generalLinearLayout.getWidth()/100);
            } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                text.setTextSize(generalLinearLayout.getWidth()/50);
            }
            if(getResources().getBoolean(R.bool.isTablet)) {
                text.setTextSize(generalLinearLayout.getWidth()/35);
            }
            text.setPadding(20,100, 20, 0);
            text.setGravity(Gravity.CENTER);
            generalLinearLayout.addView(text);
        }
    }


    @Override
    public void onResume() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        MyApplication application = (MyApplication)this.getActivity().getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(HouseRepository.class, application.getHouseRepository());
        HouseViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(HouseViewModel.class);
        viewModel.gethouses().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;

                case SUCCESS:
                    houses.clear();
                    if (resource.data != null) {
                        houses.addAll(resource.data);
                    }
                    refreshData();
            }
        });
        super.onResume();
    }

    public void refreshData(){
        MainActivity activity = (MainActivity)requireActivity();
        MyApplication application = (MyApplication)activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
        RoomViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);
        List<Room> rooms = new ArrayList<>();
        viewModel.getRooms().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    break;

                case SUCCESS:
                    rooms.clear();
                    if (resource.data != null) {
                        rooms.addAll(resource.data);
                        forRooms(rooms, generalLinearLayout);
                    }
                    break;
            }
        });
    }
}