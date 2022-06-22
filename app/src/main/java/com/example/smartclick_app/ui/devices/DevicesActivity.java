package com.example.smartclick_app.ui.devices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.databinding.ActivityDevicesBinding;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Devices.Oven;
import com.example.smartclick_app.model.Devices.Refrigerator;
import com.example.smartclick_app.model.Devices.Speaker;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.fragments.DoorFragment;
import com.example.smartclick_app.ui.devices.fragments.LightbulbFragment;
import com.example.smartclick_app.ui.devices.fragments.OvenFragment;
import com.example.smartclick_app.ui.devices.fragments.RefrigeratorFragment;
import com.example.smartclick_app.ui.devices.fragments.SpeakerFragment;
import com.example.smartclick_app.ui.room.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class DevicesActivity extends AppCompatActivity {

    private ActivityDevicesBinding binding;
    private RoomViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String roomId = intent.getStringExtra("ROOM_ID");
        String roomName = intent.getStringExtra("ROOM_NAME");

        LinearLayout generalLinearLayout = findViewById(R.id.devicesRoomLinearLayout);
//        View generalLinearLayout = findViewById(R.id.activityDevices);


        MyApplication application = (MyApplication) this.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);

        List<Device> roomDevices = new ArrayList<>();
        viewModel.getRoomDevices(roomId).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
//                    activity.showProgressBar();
                    break;
                case SUCCESS:
//                    activity.hideProgressBar();
                    if (resource.data != null && resource.data.size() > 0) {
                        Log.d("devices", resource.data.toString());
                        roomDevices.addAll(resource.data);
                        forDevices(roomDevices, generalLinearLayout);
                    }
                    break;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(roomName);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


//  TODO: Cambiar la lista de rooms por la lista de devices

    private void forDevices( List<Device> roomDevices, View generalLinearLayout) {
        for(int i = 0; i < roomDevices.size() ; i++) {
//            LinearLayout row = new LinearLayout(this);
            switch (roomDevices.get(i).getTypeId()) {
                case Door.TYPE_ID:
                    if(roomDevices.get(i) instanceof Door){
                        Door doorDevice = (Door) roomDevices.get(i);
                        getSupportFragmentManager().beginTransaction().add(generalLinearLayout.getId(), DoorFragment.newInstance(doorDevice)).commit();
                    }
                    break;
                case Oven.TYPE_ID:
                    if(roomDevices.get(i) instanceof Oven){
                        Oven ovenDevice = (Oven) roomDevices.get(i);
                        getSupportFragmentManager().beginTransaction().add(generalLinearLayout.getId(), OvenFragment.newInstance(ovenDevice)).commit();
                    }
                    break;
                case Refrigerator.TYPE_ID:
                    if(roomDevices.get(i) instanceof Refrigerator){
                        Refrigerator refrigeratorDevice = (Refrigerator) roomDevices.get(i);
                        getSupportFragmentManager().beginTransaction().add(generalLinearLayout.getId(), RefrigeratorFragment.newInstance(refrigeratorDevice)).commit();
                    }
                    break;
                case Speaker.TYPE_ID:
                    Log.d("nombre", roomDevices.get(i).getName());
                    if(roomDevices.get(i) instanceof Speaker){
                        Speaker speakerDevice = (Speaker) roomDevices.get(i);
                        getSupportFragmentManager().beginTransaction().add(generalLinearLayout.getId(), SpeakerFragment.newInstance(speakerDevice)).commit();
                    }
                    break;
                case Lightbulb.TYPE_ID:
                    if(roomDevices.get(i) instanceof Lightbulb){
                        Lightbulb lightbulbDevice = (Lightbulb) roomDevices.get(i);
                        getSupportFragmentManager().beginTransaction().add(generalLinearLayout.getId(), LightbulbFragment.newInstance(lightbulbDevice)).commit();
                    }
                    break;

            }

//
//            MaterialButton routineButton = new MaterialButton(getContext());
//            routineButton.setText(routines.get(i).getName());
//            routineButton.setId(i);
//            routineButton.setBackgroundColor(routineButton.getContext().getResources().getColor(R.color.rooms_and_routine_buttons));
//            int finalI = i;
//            routineButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    viewModel.executeRoutine(routines.get(finalI).getId()).observe(getViewLifecycleOwner(), resource -> {
//                        switch (resource.status) {
//                            case LOADING:
////                    activity.showProgressBar();
//                                break;
//                            case SUCCESS:
////                    activity.hideProgressBar();
//                                Toast.makeText(getContext(), getString(R.string.routine_execute) + " " + routines.get(finalI).getName(), Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                    });
////                    viewModel.executeRoutine(routines.get(finalI).getId());
//                }
//            });


//            View horizontalLine = new View(getContext());
//            horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
//            horizontalLine.setBackgroundColor(getResources().getColor(R.color.black));

//            row.setGravity(Gravity.CENTER);
//            row.setPadding(3, 1, 50, 1);
//            row.addView(routineButton);
//            generalLinearLayout.addView(row);

//            row = new LinearLayout(getContext());
//            row.setPadding(0, 20, 0, 20);
//            row.addView(horizontalLine);
//            generalLinearLayout.addView(row);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }// If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

}