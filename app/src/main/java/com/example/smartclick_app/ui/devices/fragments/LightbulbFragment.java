package com.example.smartclick_app.ui.devices.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.R;
import com.example.smartclick_app.model.Devices.Lightbulb;

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

    public LightbulbFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup lampFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_lamp, container, false);

        TextView textViewDeviceName = lampFragmentLayout.findViewById(R.id.lampName);
        textViewDeviceName.setText(deviceName);

        
        Button lampColorPicker = lampFragmentLayout.findViewById(R.id.lampColorPicker);
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
//                        TODO: Aca habria que meter el llamado a la api para que lo cambie
                        lampColorPicker.setBackgroundColor(color);
                        Toast.makeText(getContext(), getString(R.string.lamp_color_confirm), Toast.LENGTH_SHORT).show();
                    }
                });
                colorPicker.show();
            }
        });


        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch lampSwitchOnOff =  lampFragmentLayout.findViewById(R.id.lampSwitchOnOff);
        lampSwitchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    TODO: llamado a la api
                    // The toggle is enabled
                    Toast.makeText(getContext(), getString(R.string.lamp_on), Toast.LENGTH_SHORT).show();
                } else {
//                    TODO: llamado a la api
                    // The toggle is disabled
                    Toast.makeText(getContext(), getString(R.string.lamp_off), Toast.LENGTH_SHORT).show();
                }
            }
        });


        TextView lampTextViewPercentage = lampFragmentLayout.findViewById(R.id.lampTextViewPercentage);
        SeekBar lampSeekBar = lampFragmentLayout.findViewById(R.id.lampSeekBar);
//        TODO: Ver si nos podemos traer el brillo para poder setearlo desde un principio y no solo cuando lo setea el usuario
//        lampTextViewPercentage.setText();
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
                // This method will automatically
                // called when the user
                // stops touching the SeekBar
//                TODO: Aca habira que llamar a la api para pasarle el resultado final
            }
        });

        return lampFragmentLayout;
    }
}
