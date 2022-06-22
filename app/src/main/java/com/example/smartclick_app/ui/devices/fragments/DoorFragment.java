package com.example.smartclick_app.ui.devices.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.R;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.ui.devices.DevicesActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoorFragment extends Fragment {

    private String deviceName;

    public DoorFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DoorFragment newInstance(String deviceName) {
        DoorFragment fragment = new DoorFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", deviceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup doorFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_door, container, false);

        TextView textViewDeviceName = doorFragmentLayout.findViewById(R.id.doorName);
        textViewDeviceName.setText(deviceName);

        Button doorLockButton = doorFragmentLayout.findViewById(R.id.doorLockButton);
        doorLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de abrir la puerta aca
                Toast.makeText(getContext(), getString(R.string.door_lock), Toast.LENGTH_SHORT).show();
            }
        });

        Button doorUnlockButton = doorFragmentLayout.findViewById(R.id.doorUnlockButton);
        doorUnlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de abrir la puerta aca
                Toast.makeText(getContext(), getString(R.string.door_unlock), Toast.LENGTH_SHORT).show();
            }
        });

        Button doorOpenButton = doorFragmentLayout.findViewById(R.id.doorOpenButton);
        doorOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de abrir la puerta aca
                Toast.makeText(getContext(), getString(R.string.door_open), Toast.LENGTH_SHORT).show();
            }
        });

        Button doorCloseButton = doorFragmentLayout.findViewById(R.id.doorCloseButton);
        doorCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de abrir la puerta aca
                Toast.makeText(getContext(), getString(R.string.door_close), Toast.LENGTH_SHORT).show();
            }
        });

        return doorFragmentLayout;
    }
}