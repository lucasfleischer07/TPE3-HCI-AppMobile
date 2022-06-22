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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OvenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OvenFragment extends Fragment {

    private String deviceName;


    public OvenFragment() {
        // Required empty public constructor
    }


    public static OvenFragment newInstance(String deviceName) {
        OvenFragment fragment = new OvenFragment();
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
        ViewGroup ovenFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_oven, container, false);

        TextView textViewDeviceName = ovenFragmentLayout.findViewById(R.id.ovenName);
        textViewDeviceName.setText(deviceName);


        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch ovenSwitchOnOff =  ovenFragmentLayout.findViewById(R.id.ovenSwitchOnOff);
        ovenSwitchOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    TODO: llamado a la api
                    // The toggle is enabled
                    Toast.makeText(getContext(), getString(R.string.oven_on), Toast.LENGTH_SHORT).show();
                } else {
//                    TODO: llamado a la api
                    // The toggle is disabled
                    Toast.makeText(getContext(), getString(R.string.oven_off), Toast.LENGTH_SHORT).show();
                }
            }
        });


        TextView ovenTextViewPercentage = ovenFragmentLayout.findViewById(R.id.ovenTextViewPercentage);
        SeekBar ovenSeekBar = ovenFragmentLayout.findViewById(R.id.ovenSeekBar);
//        TODO: Ver si nos podemos traer el brillo para poder setearlo desde un principio y no solo cuando lo setea el usuario
//        ovenTextViewPercentage.setText();
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
                // This method will automatically
                // called when the user
                // stops touching the SeekBar
//                TODO: Aca habira que llamar a la api para pasarle el resultado final
            }
        });


        TextView ovenActualHeatSource = ovenFragmentLayout.findViewById(R.id.ovenActualHeatSource);

        Button heatSourceStatusDown = ovenFragmentLayout.findViewById(R.id.heatSourceStatusDown);
//        TODO: Ver de meter la del estado acrual de la api
//        ovenActualHeatSource.setText();
        heatSourceStatusDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualHeatSource.setText(R.string.oven_heat_mode_down);
                Toast.makeText(getContext(), getString(R.string.oven_heat_mode_down_activated), Toast.LENGTH_SHORT).show();
            }
        });


        Button heatSourceStatusNormal = ovenFragmentLayout.findViewById(R.id.heatSourceStatusNormal);
        heatSourceStatusNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualHeatSource.setText(R.string.oven_heat_mode_normal);
                Toast.makeText(getContext(), getString(R.string.oven_heat_mode_normal_activated), Toast.LENGTH_SHORT).show();
            }
        });


        Button heatSourceStatusUp = ovenFragmentLayout.findViewById(R.id.heatSourceStatusUp);
        heatSourceStatusUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualHeatSource.setText(R.string.oven_heat_mode_up);
                Toast.makeText(getContext(), getString(R.string.oven_heat_mode_up_activated), Toast.LENGTH_SHORT).show();
            }
        });


        TextView ovenActualGrill = ovenFragmentLayout.findViewById(R.id.ovenActualGrill);
        Button grillStatusOff = ovenFragmentLayout.findViewById(R.id.grillStatusOff);
//        TODO: Ver de meter la del estado acrual de la api
//        ovenActualGrill.setText();
        grillStatusOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualGrill.setText(R.string.oven_grill_convection_off);
                Toast.makeText(getContext(), getString(R.string.oven_grill_mode_off), Toast.LENGTH_SHORT).show();
            }
        });


        Button grillStatusNormal = ovenFragmentLayout.findViewById(R.id.grillStatusNormal);
        grillStatusNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualGrill.setText(R.string.oven_grill_convection_eco);
                Toast.makeText(getContext(), getString(R.string.oven_grill_mode_eco), Toast.LENGTH_SHORT).show();
            }
        });


        Button grillStatusUp = ovenFragmentLayout.findViewById(R.id.grillStatusUp);
        grillStatusUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualGrill.setText(R.string.oven_grill_convection_large);
                Toast.makeText(getContext(), getString(R.string.oven_grill_mode_large), Toast.LENGTH_SHORT).show();
            }
        });


        TextView ovenActualConvection = ovenFragmentLayout.findViewById(R.id.ovenActualConvection);
        Button convectionStatusOff = ovenFragmentLayout.findViewById(R.id.convectionStatusOff);
//        TODO: Ver de meter la del estado acrual de la api
//        ovenActualConvection.setText();
        convectionStatusOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualConvection.setText(R.string.oven_grill_convection_off);
                Toast.makeText(getContext(), getString(R.string.oven_convection_mode_off), Toast.LENGTH_SHORT).show();
            }
        });


        Button convectionStatusNormal = ovenFragmentLayout.findViewById(R.id.convectionStatusNormal);
        convectionStatusNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualConvection.setText(R.string.oven_grill_convection_eco);
                Toast.makeText(getContext(), getString(R.string.oven_convection_mode_eco), Toast.LENGTH_SHORT).show();
            }
        });


        Button convectionStatusUp = ovenFragmentLayout.findViewById(R.id.convectionStatusUp);
        convectionStatusUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                ovenActualConvection.setText(R.string.oven_grill_convection_large);
                Toast.makeText(getContext(), getString(R.string.oven_convection_mode_large), Toast.LENGTH_SHORT).show();
            }
        });


        return ovenFragmentLayout;
    }
}