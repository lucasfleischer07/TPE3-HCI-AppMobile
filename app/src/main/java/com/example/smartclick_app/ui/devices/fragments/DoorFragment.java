package com.example.smartclick_app.ui.devices.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;

import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoorFragment extends Fragment {

    private String deviceName;
    private String deviceId;
    private String deviceColor;
    private String [] deviceStatus;
    private String deviceLockInfo;
    private String deviceDoorStatus;

    private DeviceViewModel viewModel;


    public DoorFragment() {
        // Required empty public constructor
    }


    public static DoorFragment newInstance(Door device) {
        DoorFragment fragment = new DoorFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", device.getName());
        args.putString("deviceId", device.getId());
        args.putString("deviceLockInfo",device.getDoorLock());
        args.putString("deviceDoorStatus",device.getDoorStatus());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");
            deviceId = getArguments().getString("deviceId");
            deviceLockInfo = getArguments().getString("deviceLockInfo");
            deviceDoorStatus = getArguments().getString("deviceDoorStatus");
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        deviceColor = preferences.getString(deviceId,null);
        if(deviceColor==null){
            deviceColor=String.valueOf(R.color.rooms_and_routine_buttons);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DeviceViewModel.class);

        ViewGroup doorFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_door, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        TextView textViewDeviceName = doorFragmentLayout.findViewById(R.id.doorName);
        textViewDeviceName.setText(deviceName);
        doorFragmentLayout.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
        Button doorLockButton = doorFragmentLayout.findViewById(R.id.doorLockButton);
        Button doorUnlockButton = doorFragmentLayout.findViewById(R.id.doorUnlockButton);
        Button doorOpenButton = doorFragmentLayout.findViewById(R.id.doorOpenButton);
        Button doorCloseButton = doorFragmentLayout.findViewById(R.id.doorCloseButton);
        Button colorPickerButton = doorFragmentLayout.findViewById(R.id.colorPickerButton);

        if(Objects.equals(deviceLockInfo, Door.LOCK)) {
            doorLockButton.setVisibility(View.GONE);
            doorUnlockButton.setVisibility(View.VISIBLE);
            doorOpenButton.setVisibility(View.VISIBLE);
            doorCloseButton.setVisibility(View.GONE);
            doorOpenButton.setEnabled(false);
        } else if(Objects.equals(deviceLockInfo, Door.UNLOCK) && Objects.equals(deviceDoorStatus, Door.OPEN)) {
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorLockButton.setEnabled(false);
            doorOpenButton.setVisibility(View.GONE);
            doorCloseButton.setVisibility(View.VISIBLE);
        } else if(Objects.equals(deviceLockInfo, Door.UNLOCK) && Objects.equals(deviceDoorStatus, Door.CLOSE)) {
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorCloseButton.setVisibility(View.GONE);
            doorOpenButton.setVisibility(View.VISIBLE);
            doorOpenButton.setEnabled(true);
        } else if(Objects.equals(deviceDoorStatus, Door.OPEN)) {
            doorOpenButton.setVisibility(View.GONE);
            doorCloseButton.setVisibility(View.VISIBLE);
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorLockButton.setEnabled(false);
        } else if(Objects.equals(deviceDoorStatus, Door.CLOSE)) {
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorCloseButton.setVisibility(View.GONE);
            doorOpenButton.setVisibility(View.VISIBLE);
            doorLockButton.setEnabled(true);
        }


        doorLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_LOCK).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceLockInfo = Door.LOCK;
                            doorLockButton.setVisibility(View.GONE);
                            doorUnlockButton.setVisibility(View.VISIBLE);
                            doorOpenButton.setVisibility(View.VISIBLE);
                            doorCloseButton.setVisibility(View.GONE);
                            doorOpenButton.setEnabled(false);
                            Toast.makeText(getContext(), getString(R.string.door_lock), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        doorUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_UNLOCK).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceLockInfo = Door.UNLOCK;
                            if(Objects.equals(deviceDoorStatus, Door.OPEN)) {
                                doorUnlockButton.setVisibility(View.GONE);
                                doorLockButton.setVisibility(View.VISIBLE);
                                doorLockButton.setEnabled(false);
                                doorOpenButton.setVisibility(View.GONE);
                                doorCloseButton.setVisibility(View.VISIBLE);
                            } else if(Objects.equals(deviceDoorStatus, Door.CLOSE)) {
                                doorUnlockButton.setVisibility(View.GONE);
                                doorLockButton.setVisibility(View.VISIBLE);
                                doorCloseButton.setVisibility(View.GONE);
                                doorOpenButton.setVisibility(View.VISIBLE);
                                doorOpenButton.setEnabled(true);
                            }
                            Toast.makeText(getContext(), getString(R.string.door_unlock), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        doorOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_OPEN).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceDoorStatus = Door.OPEN;
                            doorOpenButton.setVisibility(View.GONE);
                            doorCloseButton.setVisibility(View.VISIBLE);
                            doorUnlockButton.setVisibility(View.GONE);
                            doorLockButton.setVisibility(View.VISIBLE);
                            doorLockButton.setEnabled(false);
                            Toast.makeText(getContext(), getString(R.string.door_open), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        doorCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_CLOSE).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceDoorStatus = Door.CLOSE;
                            doorUnlockButton.setVisibility(View.GONE);
                            doorLockButton.setVisibility(View.VISIBLE);
                            doorCloseButton.setVisibility(View.GONE);
                            doorOpenButton.setVisibility(View.VISIBLE);
                            doorLockButton.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.door_close), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getContext(), R.color.blue_main, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        Toast.makeText(getContext(), getString(R.string.lamp_color_cancel), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(deviceId,Integer.toHexString(color));
                        deviceColor=Integer.toHexString(color);
                        doorFragmentLayout.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
                        editor.apply();
                        Toast.makeText(getContext(), getString(R.string.lamp_color_confirm), Toast.LENGTH_SHORT).show();
                    }
                });
                colorPicker.show();
            }
        });

        return doorFragmentLayout;
    }
}