package com.example.smartclick_app.ui.devices.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RefrigeratorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RefrigeratorFragment extends Fragment {

    private String deviceName;

    public RefrigeratorFragment() {
        // Required empty public constructor
    }


    public static RefrigeratorFragment newInstance(String deviceName) {
        RefrigeratorFragment fragment = new RefrigeratorFragment();
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
        ViewGroup refrigeratorFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_refrigerator, container, false);

        TextView textViewDeviceName = refrigeratorFragmentLayout.findViewById(R.id.refrigeratorName);
        textViewDeviceName.setText(deviceName);

        TextView refrigeratorTemperatureNumber = refrigeratorFragmentLayout.findViewById(R.id.refrigeratorTemperatureNumber);
        SeekBar refigeratorSeekBar = refrigeratorFragmentLayout.findViewById(R.id.refigeratorSeekBar);
//        TODO: Ver si nos podemos traer el brillo para poder setearlo desde un principio y no solo cuando lo setea el usuario
//        lampTextViewPercentage.setText();
        refigeratorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // increment 1 in progress and
                // increase the textsize
                // with the value of progress
                refrigeratorTemperatureNumber.setText(progress + "°");
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

        TextView freezerTemperatureNumber = refrigeratorFragmentLayout.findViewById(R.id.freezerTemperatureNumber);
        SeekBar refigeratorFreezerSeekBar = refrigeratorFragmentLayout.findViewById(R.id.refigeratorFreezerSeekBar);
//        TODO: Ver si nos podemos traer el brillo para poder setearlo desde un principio y no solo cuando lo setea el usuario
//        lampTextViewPercentage.setText();
        refigeratorFreezerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // increment 1 in progress and
                // increase the textsize
                // with the value of progress
                freezerTemperatureNumber.setText(progress + "°");
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

        Button modeRefrigeratorFest = refrigeratorFragmentLayout.findViewById(R.id.modeRefrigeratorFest);
        TextView refrigeratorActualMode = refrigeratorFragmentLayout.findViewById(R.id.refrigeratorActualMode);
//        TODO: Meter el estado actaul del modo
//        refrigeratorActualMode.setText();
        modeRefrigeratorFest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
//          TODO: Meter la accion de abrir la puerta aca
                refrigeratorActualMode.setText(": " + getString(R.string.refrigerator_fest));
                Toast.makeText(getContext(), getString(R.string.refrigerator_mode_fest), Toast.LENGTH_SHORT).show();
            }
        });
            
       Button modeRefrigeratorAuto = refrigeratorFragmentLayout.findViewById(R.id.modeRefrigeratorAuto);
       modeRefrigeratorAuto.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("SetTextI18n")
           @Override
           public void onClick(View v) {
//                TODO: Meter la accion de abrir la puerta aca
               refrigeratorActualMode.setText(": " + getString(R.string.refrigerator_auto));
               Toast.makeText(getContext(), getString(R.string.refrigerator_mode_auto), Toast.LENGTH_SHORT).show();
           }
       });            
     
       Button modeRefrigeratorVacation = refrigeratorFragmentLayout.findViewById(R.id.modeRefrigeratorVacation);
       modeRefrigeratorVacation.setOnClickListener(new View.OnClickListener() {
          @SuppressLint("SetTextI18n")
          @Override
          public void onClick(View v) {
//                TODO: Meter la accion de abrir la puerta aca
              refrigeratorActualMode.setText(": " + getString(R.string.refrigerator_vacations));
              Toast.makeText(getContext(), getString(R.string.refrigerator_mode_vacations), Toast.LENGTH_SHORT).show();
          }
      });  
      


        return refrigeratorFragmentLayout;
    }
}



