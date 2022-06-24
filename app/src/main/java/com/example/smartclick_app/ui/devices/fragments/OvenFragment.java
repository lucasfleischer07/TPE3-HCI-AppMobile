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
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Devices.Oven;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;

import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OvenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OvenFragment extends Fragment {

    private String deviceName;
    private String deviceId;
    private String deviceConvection;
    private String deviceStatus;
    private String deviceGrill;
    private String deviceHeatZone;
    private int deviceTemperature;
    private String deviceColor;
    private DeviceViewModel viewModel;


    public OvenFragment() {
        // Required empty public constructor
    }


    public static OvenFragment newInstance(Oven device) {
        OvenFragment fragment = new OvenFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", device.getName());
        args.putString("deviceId", device.getId());
        args.putString("deviceConvection", device.getConvection());
        args.putString("deviceGrill", device.getGrillMode());
        args.putString("deviceStatus", device.getStatus());
        args.putString("deviceHeatZone", device.getHeatZone());
        args.putInt("deviceTemp", device.getTemperature());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");
            deviceId = getArguments().getString("deviceId");
            deviceConvection = getArguments().getString("deviceConvection");
            deviceStatus = getArguments().getString("deviceStatus");
            deviceGrill = getArguments().getString("deviceGrill");
            deviceHeatZone = getArguments().getString("deviceHeatZone");
            deviceTemperature = getArguments().getInt("deviceTemp");
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

        ViewGroup ovenFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_oven, container, false);
        ovenFragmentLayout.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
        TextView textViewDeviceName = ovenFragmentLayout.findViewById(R.id.ovenName);
        textViewDeviceName.setText(deviceName);

        Button colorPickerButton = ovenFragmentLayout.findViewById(R.id.colorPickerButton);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        boolean turnOn = Objects.equals(deviceStatus, "on");
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch ovenSwitchOnOff = ovenFragmentLayout.findViewById(R.id.ovenSwitchOnOff);
        ovenSwitchOnOff.setChecked(turnOn);
        ovenSwitchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewModel.executeDeviceAction(deviceId, Oven.ACTION_TURN_ON).observe(getViewLifecycleOwner(), resource -> {
                        switch (resource.status) {
                            case LOADING:
                                break;
                            case SUCCESS:
                                deviceStatus = "on";
                                Toast.makeText(getContext(), getString(R.string.oven_on), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });
                } else {
                    viewModel.executeDeviceAction(deviceId, Oven.ACTION_TURN_OFF).observe(getViewLifecycleOwner(), resource -> {
                        switch (resource.status) {
                            case LOADING:
                                break;
                            case SUCCESS:
                                deviceStatus = "off";
                                Toast.makeText(getContext(), getString(R.string.oven_off), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    });
                }
            }
        });


        TextView ovenTextViewPercentage = ovenFragmentLayout.findViewById(R.id.ovenTextViewPercentage);
        SeekBar ovenSeekBar = ovenFragmentLayout.findViewById(R.id.ovenSeekBar);
        ovenTextViewPercentage.setText(String.valueOf(deviceTemperature) + "°");
        ovenSeekBar.setProgress(deviceTemperature);
        ovenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // increment 1 in progress and
                // increase the textsize
                // with the value of progress
                ovenTextViewPercentage.setText(progress + "°");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This method will automatically
                // called when the user touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.executeDeviceActionWithInt(deviceId, Oven.ACTION_SET_TEMPERATURE, seekBar.getProgress()).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceTemperature = seekBar.getProgress();
                            break;
                    }
                });
            }
        });


        Button heatSourceStatusDown = ovenFragmentLayout.findViewById(R.id.heatSourceStatusDown);
        Button heatSourceStatusNormal = ovenFragmentLayout.findViewById(R.id.heatSourceStatusNormal);
        Button heatSourceStatusUp = ovenFragmentLayout.findViewById(R.id.heatSourceStatusUp);

        if(Objects.equals(deviceHeatZone, Oven.HEAT_TYPE_BOTTOM)) {
            heatSourceStatusDown.setEnabled(false);
            heatSourceStatusNormal.setEnabled(true);
            heatSourceStatusUp.setEnabled(true);
        } else if(Objects.equals(deviceHeatZone, Oven.HEAT_TYPE_CONVENTIONAL)) {
            heatSourceStatusDown.setEnabled(true);
            heatSourceStatusNormal.setEnabled(false);
            heatSourceStatusUp.setEnabled(true);
        } else if(Objects.equals(deviceHeatZone, Oven.HEAT_TYPE_TOP)) {
            heatSourceStatusDown.setEnabled(true);
            heatSourceStatusNormal.setEnabled(true);
            heatSourceStatusUp.setEnabled(false);
        }

        TextView ovenActualHeatSource = ovenFragmentLayout.findViewById(R.id.ovenActualHeatSource);
        ovenActualHeatSource.setText(deviceHeatZone);
        heatSourceStatusDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_HEAT, Oven.HEAT_TYPE_BOTTOM).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualHeatSource.setText(R.string.oven_heat_mode_down);
                            deviceHeatZone = Oven.HEAT_TYPE_BOTTOM;
                            heatSourceStatusDown.setEnabled(false);
                            heatSourceStatusNormal.setEnabled(true);
                            heatSourceStatusUp.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.oven_heat_mode_down_activated), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        heatSourceStatusNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_HEAT, Oven.HEAT_TYPE_CONVENTIONAL).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualHeatSource.setText(R.string.oven_heat_mode_normal);
                            deviceHeatZone = Oven.HEAT_TYPE_CONVENTIONAL;
                            heatSourceStatusDown.setEnabled(true);
                            heatSourceStatusNormal.setEnabled(false);
                            heatSourceStatusUp.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.oven_heat_mode_normal_activated), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        heatSourceStatusUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_HEAT, Oven.HEAT_TYPE_TOP).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualHeatSource.setText(R.string.oven_heat_mode_up);
                            deviceHeatZone = Oven.HEAT_TYPE_TOP;
                            heatSourceStatusDown.setEnabled(true);
                            heatSourceStatusNormal.setEnabled(true);
                            heatSourceStatusUp.setEnabled(false);
                            Toast.makeText(getContext(), getString(R.string.oven_heat_mode_up_activated), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });


        Button grillStatusOff = ovenFragmentLayout.findViewById(R.id.grillStatusOff);
        Button grillStatusNormal = ovenFragmentLayout.findViewById(R.id.grillStatusNormal);
        Button grillStatusUp = ovenFragmentLayout.findViewById(R.id.grillStatusUp);

        if(Objects.equals(deviceGrill, Oven.GRILL_TYPE_OFF)) {
            grillStatusOff.setEnabled(false);
            grillStatusNormal.setEnabled(true);
            grillStatusUp.setEnabled(true);
        } else if(Objects.equals(deviceGrill, Oven.GRILL_TYPE_ECO)) {
            grillStatusOff.setEnabled(true);
            grillStatusNormal.setEnabled(false);
            grillStatusUp.setEnabled(true);
        } else if(Objects.equals(deviceGrill, Oven.GRILL_TYPE_LARGE)) {
            grillStatusOff.setEnabled(true);
            grillStatusNormal.setEnabled(true);
            grillStatusUp.setEnabled(false);
        }

        TextView ovenActualGrill = ovenFragmentLayout.findViewById(R.id.ovenActualGrill);
        ovenActualGrill.setText(deviceGrill);
        grillStatusOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_GRILL, Oven.GRILL_TYPE_OFF).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualGrill.setText(R.string.oven_grill_convection_off);
                            deviceGrill = Oven.GRILL_TYPE_OFF;
                            grillStatusOff.setEnabled(false);
                            grillStatusNormal.setEnabled(true);
                            grillStatusUp.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.oven_grill_mode_off), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        grillStatusNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_GRILL, Oven.GRILL_TYPE_ECO).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualGrill.setText(R.string.oven_grill_convection_eco);
                            deviceGrill = Oven.GRILL_TYPE_OFF;
                            grillStatusOff.setEnabled(true);
                            grillStatusNormal.setEnabled(false);
                            grillStatusUp.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.oven_grill_mode_eco), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        grillStatusUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_GRILL, Oven.GRILL_TYPE_LARGE).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualGrill.setText(R.string.oven_grill_convection_large);
                            deviceGrill = Oven.GRILL_TYPE_LARGE;
                            grillStatusOff.setEnabled(true);
                            grillStatusNormal.setEnabled(true);
                            grillStatusUp.setEnabled(false);
                            Toast.makeText(getContext(), getString(R.string.oven_grill_mode_large), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });


        Button convectionStatusOff = ovenFragmentLayout.findViewById(R.id.convectionStatusOff);
        Button convectionStatusNormal = ovenFragmentLayout.findViewById(R.id.convectionStatusNormal);
        Button convectionStatusUp = ovenFragmentLayout.findViewById(R.id.convectionStatusUp);

        if(Objects.equals(deviceConvection, Oven.CONVECTION_TYPE_OFF)) {
            convectionStatusOff.setEnabled(false);
            convectionStatusNormal.setEnabled(true);
            convectionStatusUp.setEnabled(true);
        } else if(Objects.equals(deviceConvection, Oven.CONVECTION_TYPE_ECO)) {
            convectionStatusOff.setEnabled(true);
            convectionStatusNormal.setEnabled(false);
            convectionStatusUp.setEnabled(true);
        } else if(Objects.equals(deviceConvection, Oven.CONVECTION_TYPE_NORMAL)) {
            convectionStatusOff.setEnabled(true);
            convectionStatusNormal.setEnabled(true);
            convectionStatusUp.setEnabled(false);
        }

        TextView ovenActualConvection = ovenFragmentLayout.findViewById(R.id.ovenActualConvection);
        ovenActualConvection.setText(deviceConvection);
        convectionStatusOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_CONVECTION, Oven.CONVECTION_TYPE_OFF).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualConvection.setText(R.string.oven_grill_convection_off);
                            deviceGrill = Oven.CONVECTION_TYPE_OFF;
                            convectionStatusOff.setEnabled(false);
                            convectionStatusNormal.setEnabled(true);
                            convectionStatusUp.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.oven_convection_mode_off), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        convectionStatusNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_CONVECTION, Oven.CONVECTION_TYPE_ECO).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualConvection.setText(R.string.oven_grill_convection_eco);
                            deviceGrill = Oven.CONVECTION_TYPE_ECO;
                            convectionStatusOff.setEnabled(true);
                            convectionStatusNormal.setEnabled(false);
                            convectionStatusUp.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.oven_convection_mode_eco), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        convectionStatusUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Oven.ACTION_SET_CONVECTION, Oven.CONVECTION_TYPE_NORMAL).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            ovenActualConvection.setText(R.string.oven_grill_convection_large);
                            deviceGrill = Oven.CONVECTION_TYPE_NORMAL;
                            convectionStatusOff.setEnabled(true);
                            convectionStatusNormal.setEnabled(true);
                            convectionStatusUp.setEnabled(false);
                            Toast.makeText(getContext(), getString(R.string.oven_convection_mode_large), Toast.LENGTH_SHORT).show();
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
                        ovenFragmentLayout.setBackgroundColor((int) Long.parseLong(deviceColor.replace("#", ""), 16));
                        editor.apply();
                        Toast.makeText(getContext(), getString(R.string.lamp_color_confirm), Toast.LENGTH_SHORT).show();
                    }
                });
                colorPicker.show();
            }
        });

        return ovenFragmentLayout;
    }
}