package com.example.smartclick_app.ui.devices;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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

import yuku.ambilwarna.AmbilWarnaDialog;

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

        LinearLayout generalLinearLayout = findViewById(R.id.devicesRoomLinearLayout2);

        MyApplication application = (MyApplication) this.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoomRepository.class, application.getRoomRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoomViewModel.class);

        List<Device> roomDevices = new ArrayList<>();
        viewModel.getRoomDevices(roomId).observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (resource.data != null) {
                        Log.d("devices", resource.data.toString());
                        roomDevices.addAll(resource.data);
                        forDevices(roomDevices, generalLinearLayout, roomName);
                    }
                    break;
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(roomName);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    @SuppressLint("SetTextI18n")
    private void forDevices(List<Device> roomDevices, LinearLayout generalLinearLayout, String roomName) {
        generalLinearLayout.removeAllViewsInLayout();
        generalLinearLayout.removeAllViews();
        if((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) && !(getResources().getBoolean(R.bool.isTablet))) {
            for(int i = 0; i < roomDevices.size() ; i++) {
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
            }
        } else if((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) && !(getResources().getBoolean(R.bool.isTablet))) {
            LinearLayout rowLinearLayout;
            for(int i = 0; i < ((roomDevices.size())/2 + roomDevices.size() % 2); i++) {
                rowLinearLayout = new LinearLayout(getApplicationContext());
                rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLinearLayout.setId(16000000 + i);
                for(int j = 0; j < 2; j++) {
                    if(j + i*2 >= roomDevices.size()) {
                        break;
                    }
                    switch (roomDevices.get(j + i*2).getTypeId()) {
                        case Door.TYPE_ID:
                            if(roomDevices.get(j + i*2) instanceof Door){
                                Door doorDevice = (Door) roomDevices.get(j + i*2);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), DoorFragment.newInstance(doorDevice)).commit();
                            }
                            break;

                        case Oven.TYPE_ID:
                            if(roomDevices.get(j + i*2) instanceof Oven){
                                Oven ovenDevice = (Oven) roomDevices.get(j + i*2);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), OvenFragment.newInstance(ovenDevice)).commit();
                            }
                            break;

                        case Refrigerator.TYPE_ID:
                            if(roomDevices.get(j + i*2) instanceof Refrigerator){
                                Refrigerator refrigeratorDevice = (Refrigerator) roomDevices.get(j + i*2);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), RefrigeratorFragment.newInstance(refrigeratorDevice)).commit();
                            }
                            break;

                        case Speaker.TYPE_ID:
                            if(roomDevices.get(j + i*2) instanceof Speaker){
                                Speaker speakerDevice = (Speaker) roomDevices.get(j + i*2);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), SpeakerFragment.newInstance(speakerDevice)).commit();
                            }
                            break;

                        case Lightbulb.TYPE_ID:
                            if(roomDevices.get(j + i*2) instanceof Lightbulb){
                                Lightbulb lightbulbDevice = (Lightbulb) roomDevices.get(j + i*2);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), LightbulbFragment.newInstance(lightbulbDevice)).commit();
                            }
                            break;
                    }
                }
                generalLinearLayout.addView(rowLinearLayout);
            }
        } else if((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) && (getResources().getBoolean(R.bool.isTablet))) {
            LinearLayout rowLinearLayout;
            for(int i = 0; i < ((roomDevices.size())/4 + (roomDevices.size() % 4 == 0? 0 : 1)); i++) {
                rowLinearLayout = new LinearLayout(getApplicationContext());
                rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLinearLayout.setId(16000000 + i);
                for (int j = 0; j < 4 && i*4+j<roomDevices.size(); j++) {
                    if (j + i * 2 >= roomDevices.size()) {
                        break;
                    }
                    switch (roomDevices.get(j + i * 4).getTypeId()) {
                        case Door.TYPE_ID:
                            if (roomDevices.get(j + i * 4) instanceof Door) {
                                Door doorDevice = (Door) roomDevices.get(j + i * 4);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), DoorFragment.newInstance(doorDevice)).commit();
                            }
                            break;

                        case Oven.TYPE_ID:
                            if (roomDevices.get(j + i * 4) instanceof Oven) {
                                Oven ovenDevice = (Oven) roomDevices.get(j + i * 4);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), OvenFragment.newInstance(ovenDevice)).commit();
                            }
                            break;

                        case Refrigerator.TYPE_ID:
                            if (roomDevices.get(j + i * 4) instanceof Refrigerator) {
                                Refrigerator refrigeratorDevice = (Refrigerator) roomDevices.get(j + i * 4);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), RefrigeratorFragment.newInstance(refrigeratorDevice)).commit();
                            }
                            break;

                        case Speaker.TYPE_ID:
                            if (roomDevices.get(j + i * 4) instanceof Speaker) {
                                Speaker speakerDevice = (Speaker) roomDevices.get(j + i * 4);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), SpeakerFragment.newInstance(speakerDevice)).commit();
                            }
                            break;

                        case Lightbulb.TYPE_ID:
                            if (roomDevices.get(j + i * 4) instanceof Lightbulb) {
                                Lightbulb lightbulbDevice = (Lightbulb) roomDevices.get(j + i * 4);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), LightbulbFragment.newInstance(lightbulbDevice)).commit();
                            }
                            break;
                    }
                }
                generalLinearLayout.addView(rowLinearLayout);
            }
        } else if((getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) && (getResources().getBoolean(R.bool.isTablet))) {
            LinearLayout rowLinearLayout;
            for(int i = 0; i < ((roomDevices.size())/3 + (roomDevices.size() % 3 == 0? 0 : 1)); i++) {
                rowLinearLayout = new LinearLayout(getApplicationContext());
                rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLinearLayout.setId(16000000 + i);
                for (int j = 0; j < 3 && j + i*3<roomDevices.size(); j++) {
                    if (j + i * 2 >= roomDevices.size()) {
                        break;
                    }
                    switch (roomDevices.get(j + i * 3).getTypeId()) {
                        case Door.TYPE_ID:
                            if (roomDevices.get(j + i * 3) instanceof Door) {
                                Door doorDevice = (Door) roomDevices.get(j + i * 3);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), DoorFragment.newInstance(doorDevice)).commit();
                            }
                            break;

                        case Oven.TYPE_ID:
                            if (roomDevices.get(j + i * 3) instanceof Oven) {
                                Oven ovenDevice = (Oven) roomDevices.get(j + i * 3);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), OvenFragment.newInstance(ovenDevice)).commit();
                            }
                            break;

                        case Refrigerator.TYPE_ID:
                            if (roomDevices.get(j + i * 3) instanceof Refrigerator) {
                                Refrigerator refrigeratorDevice = (Refrigerator) roomDevices.get(j + i * 3);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), RefrigeratorFragment.newInstance(refrigeratorDevice)).commit();
                            }
                            break;

                        case Speaker.TYPE_ID:
                            if (roomDevices.get(j + i * 3) instanceof Speaker) {
                                Speaker speakerDevice = (Speaker) roomDevices.get(j + i * 3);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), SpeakerFragment.newInstance(speakerDevice)).commit();
                            }
                            break;

                        case Lightbulb.TYPE_ID:
                            if (roomDevices.get(j + i * 3) instanceof Lightbulb) {
                                Lightbulb lightbulbDevice = (Lightbulb) roomDevices.get(j + i * 3);
                                getSupportFragmentManager().beginTransaction().add(rowLinearLayout.getId(), LightbulbFragment.newInstance(lightbulbDevice)).commit();
                            }
                            break;
                    }
                }
                generalLinearLayout.addView(rowLinearLayout);
            }
        }

        if(roomDevices.size()==0){
            TextView text=new TextView(this);
            text.setText(getString(R.string.devices_null) + " " + roomName);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                text.setTextSize(generalLinearLayout.getWidth()/100);
            } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                text.setTextSize(generalLinearLayout.getWidth()/50);
            }
            if(getResources().getBoolean(R.bool.isTablet)) {
                text.setTextSize(generalLinearLayout.getWidth()/70);
            }
            text.setPadding(20,100, 20, 0);
            text.setGravity(View.TEXT_ALIGNMENT_GRAVITY);

            LinearLayout rowLinearLayout = new LinearLayout(getApplicationContext());
            rowLinearLayout.setGravity(Gravity.CENTER);
            rowLinearLayout.addView(text);
            generalLinearLayout.addView(rowLinearLayout);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

}