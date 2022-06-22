package com.example.smartclick_app.ui.devices.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoorFragment extends Fragment {

    private String deviceName;
    private String deviceId;

    private String [] deviceStatus;
    private String deviceLockInfo;
    private String deviceDoorStatus;

    private DeviceViewModel viewModel;

    Button doorLockButton;
    Button doorUnlockButton;
    Button doorOpenButton;
    Button doorCloseButton;

    public DoorFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
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
            deviceStatus = new String[]{deviceDoorStatus, deviceLockInfo};
        }

        Observer observer = new Observer<String[]>() {
            @Override
            public void onChanged(String [] deviceStatus) {
                setVisualButtons(deviceStatus[0], deviceStatus[1], doorLockButton, doorUnlockButton, doorOpenButton, doorCloseButton);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity activity = getActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DeviceViewModel.class);

        ViewGroup doorFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_door, container, false);

        TextView textViewDeviceName = doorFragmentLayout.findViewById(R.id.doorName);
        textViewDeviceName.setText(deviceName);

        doorLockButton = doorFragmentLayout.findViewById(R.id.doorLockButton);
        doorLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_LOCK).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus[1] = "locked";
                            Toast.makeText(getContext(), getString(R.string.door_lock), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        doorUnlockButton = doorFragmentLayout.findViewById(R.id.doorUnlockButton);
        doorUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_UNLOCK).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus[1] = "unlocked";
                            Toast.makeText(getContext(), getString(R.string.door_unlock), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });

            }
        });

        doorOpenButton = doorFragmentLayout.findViewById(R.id.doorOpenButton);
        doorOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_OPEN).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus[0] = "opened";
                            Toast.makeText(getContext(), getString(R.string.door_open), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        doorCloseButton = doorFragmentLayout.findViewById(R.id.doorCloseButton);
        doorCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Door.ACTION_CLOSE).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus[0] = "closed";
                            Toast.makeText(getContext(), getString(R.string.door_close), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        return doorFragmentLayout;
    }

    private void setVisualButtons (String deviceDoorStatus, String deviceLockInfo, Button doorLockButton, Button doorUnlockButton, Button doorOpenButton, Button doorCloseButton) {
        if(Objects.equals(deviceLockInfo, "locked")) {
            doorLockButton.setVisibility(View.GONE);
            doorUnlockButton.setVisibility(View.VISIBLE);
            doorOpenButton.setVisibility(View.VISIBLE);
            doorCloseButton.setVisibility(View.GONE);
            doorOpenButton.setEnabled(false);
        } else if (Objects.equals(deviceLockInfo, "unlocked") && Objects.equals(deviceDoorStatus, "opened")) {
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorLockButton.setEnabled(false);
            doorOpenButton.setVisibility(View.GONE);
            doorCloseButton.setVisibility(View.VISIBLE);
        } else if (Objects.equals(deviceLockInfo, "unlocked") && Objects.equals(deviceDoorStatus, "closed")) {
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorCloseButton.setVisibility(View.GONE);
            doorOpenButton.setVisibility(View.VISIBLE);
        } else if(Objects.equals(deviceDoorStatus, "opened")) {
            doorOpenButton.setVisibility(View.GONE);
            doorCloseButton.setVisibility(View.VISIBLE);
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorLockButton.setEnabled(false);
        } else if (Objects.equals(deviceDoorStatus, "closed")) {
            doorUnlockButton.setVisibility(View.GONE);
            doorLockButton.setVisibility(View.VISIBLE);
            doorCloseButton.setVisibility(View.GONE);
            doorOpenButton.setVisibility(View.VISIBLE);
        }
    }
}