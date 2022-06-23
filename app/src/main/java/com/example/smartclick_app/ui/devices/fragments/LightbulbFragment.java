package com.example.smartclick_app.ui.devices.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.ui.MainActivity;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;

import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LightbulbFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LightbulbFragment extends Fragment {

    private String deviceName;
    private String deviceId;
    private String deviceColor;
    private String deviceStatus;
    private int deviceBrightness;
    private DeviceViewModel viewModel;


    public LightbulbFragment() {
        // Required empty public constructor
    }


    public static LightbulbFragment newInstance(Lightbulb device) {
        LightbulbFragment fragment = new LightbulbFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", device.getName());
        args.putString("deviceId", device.getId());
        args.putString("deviceColor", device.getColor());
        args.putString("deviceStatus", device.getStatus());
        args.putInt("deviceBrightness", device.getBrightness());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");
            deviceId = getArguments().getString("deviceId");
            deviceColor = getArguments().getString("deviceColor");
            deviceStatus = getArguments().getString("deviceStatus");
            deviceBrightness = getArguments().getInt("deviceBrightness");
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

        ViewGroup lampFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_lamp, container, false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        TextView textViewDeviceName = lampFragmentLayout.findViewById(R.id.lampName);
        textViewDeviceName.setText(deviceName);
        lampFragmentLayout.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
        Button colorPickerButton = lampFragmentLayout.findViewById(R.id.colorPickerButton);


        Button lampColorPicker = lampFragmentLayout.findViewById(R.id.lampColorPicker);
        Log.d("Color", deviceColor);
        lampColorPicker.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
        lampColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getContext(), R.color.blue_main, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        Toast.makeText(getContext(), getString(R.string.lamp_color_cancel), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        Log.d("color", String.valueOf(color));
                        viewModel.executeDeviceActionWithString(deviceId, Lightbulb.ACTION_SET_COLOR, String.valueOf(color)).observe(getViewLifecycleOwner(), resource -> {
                            switch (resource.status) {
                                case LOADING:
                                    break;
                                case SUCCESS:
                                    lampColorPicker.setBackgroundColor(color);
                                    deviceColor = Integer.toHexString(color);
                                    Toast.makeText(getContext(), getString(R.string.lamp_color_confirm), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        });
                    }
                });
                colorPicker.show();
            }
        });

        boolean turnOn = Objects.equals(deviceStatus, "on");
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch lampSwitchOnOff =  lampFragmentLayout.findViewById(R.id.lampSwitchOnOff);
        lampSwitchOnOff.setChecked(turnOn);
        lampSwitchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewModel.executeDeviceAction(deviceId, Lightbulb.ACTION_TURN_ON).observe(getViewLifecycleOwner(), resource -> {
                        switch (resource.status) {
                            case LOADING:
                                break;
                            case SUCCESS:
                                deviceStatus = "on";
                                Toast.makeText(getContext(), getString(R.string.lamp_on), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });
                } else {
                    viewModel.executeDeviceAction(deviceId, Lightbulb.ACTION_TURN_OFF).observe(getViewLifecycleOwner(), resource -> {
                        switch (resource.status) {
                            case LOADING:
                                break;
                            case SUCCESS:
                                deviceStatus = "off";
                                Toast.makeText(getContext(), getString(R.string.lamp_off), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });
                }
            }
        });


        TextView lampTextViewPercentage = lampFragmentLayout.findViewById(R.id.lampTextViewPercentage);
        SeekBar lampSeekBar = lampFragmentLayout.findViewById(R.id.lampSeekBar);
        lampTextViewPercentage.setText(String.valueOf(deviceBrightness) + "%");
        lampSeekBar.setProgress(deviceBrightness);
        lampSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // increment 1 in progress and
                // increase the textsize
                // with the value of progress
                lampTextViewPercentage.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This method will automatically
                // called when the user touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.executeDeviceActionWithInt(deviceId, Lightbulb.ACTION_SET_BRIGHTNESS, seekBar.getProgress()).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceBrightness = seekBar.getProgress();
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
                        lampFragmentLayout.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
                        editor.apply();
                        Toast.makeText(getContext(), getString(R.string.lamp_color_confirm), Toast.LENGTH_SHORT).show();
                    }
                });
                colorPicker.show();
            }
        });


        return lampFragmentLayout;
    }
}
