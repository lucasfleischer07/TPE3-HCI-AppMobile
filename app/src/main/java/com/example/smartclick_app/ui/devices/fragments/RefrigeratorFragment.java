package com.example.smartclick_app.ui.devices.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Devices.Refrigerator;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RefrigeratorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RefrigeratorFragment extends Fragment {

    private String deviceName;
    private String deviceId;
    private int deviceTemperature;
    private int deviceFreezerTemperature;
    private String deviceMode;

    private DeviceViewModel viewModel;


    public RefrigeratorFragment() {
        // Required empty public constructor
    }


    public static RefrigeratorFragment newInstance(Refrigerator device) {
        RefrigeratorFragment fragment = new RefrigeratorFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", device.getName());
        args.putString("deviceId", device.getId());
        args.putInt("deviceTemperature", device.getTemperature());
        args.putInt("deviceFreezerTemperature", device.getFreezerTemperature());
        args.putString("deviceMode", device.getMode());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");
            deviceId = getArguments().getString("deviceId");
            deviceFreezerTemperature = getArguments().getInt("deviceFreezerTemperature");
            deviceMode = getArguments().getString("deviceMode");
            deviceTemperature = getArguments().getInt("deviceTemp");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity activity = getActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DeviceViewModel.class);

        ViewGroup refrigeratorFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_refrigerator, container, false);

        TextView textViewDeviceName = refrigeratorFragmentLayout.findViewById(R.id.refrigeratorName);
        textViewDeviceName.setText(deviceName);


        Button modeRefrigeratorFest = refrigeratorFragmentLayout.findViewById(R.id.modeRefrigeratorFest);
        Button modeRefrigeratorAuto = refrigeratorFragmentLayout.findViewById(R.id.modeRefrigeratorAuto);
        Button modeRefrigeratorVacation = refrigeratorFragmentLayout.findViewById(R.id.modeRefrigeratorVacation);
        TextView refrigeratorActualMode = refrigeratorFragmentLayout.findViewById(R.id.refrigeratorActualMode);


        TextView refrigeratorTemperatureNumber = refrigeratorFragmentLayout.findViewById(R.id.refrigeratorTemperatureNumber);
        SeekBar refigeratorSeekBar = refrigeratorFragmentLayout.findViewById(R.id.refigeratorSeekBar);

        TextView freezerTemperatureNumber = refrigeratorFragmentLayout.findViewById(R.id.freezerTemperatureNumber);
        SeekBar refigeratorFreezerSeekBar = refrigeratorFragmentLayout.findViewById(R.id.refigeratorFreezerSeekBar);

        if(Objects.equals(deviceMode, Refrigerator.MODE_DEFAULT)) {
            refigeratorSeekBar.setProgress(deviceTemperature);
            refrigeratorTemperatureNumber.setText(deviceTemperature + "°");
            deviceFreezerTemperature = Refrigerator.MODE_DEFAULT_TEMPERATURE_FREEZER;
            refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
            freezerTemperatureNumber.setText(deviceFreezerTemperature + "°");
            refrigeratorActualMode.setText(": " + deviceMode);
            modeRefrigeratorAuto.setEnabled(false);
            modeRefrigeratorFest.setEnabled(true);
            modeRefrigeratorVacation.setEnabled(true);
        } else if(Objects.equals(deviceMode, Refrigerator.MODE_PARTY)) {
            deviceTemperature = Refrigerator.MODE_PARTY_TEMPERATURE;
            refigeratorSeekBar.setProgress(deviceTemperature);
            refrigeratorTemperatureNumber.setText(deviceTemperature + "°");
            deviceFreezerTemperature = Refrigerator.MODE_PARTY_TEMPERATURE_FREEZER;
            refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
            freezerTemperatureNumber.setText(deviceFreezerTemperature + "°");
            refrigeratorActualMode.setText(": " + deviceMode);
            modeRefrigeratorAuto.setEnabled(true);
            modeRefrigeratorFest.setEnabled(false);
            modeRefrigeratorVacation.setEnabled(true);
        } else if(Objects.equals(deviceMode, Refrigerator.MODE_VACATION)) {
            deviceTemperature = Refrigerator.MODE_VACATION_TEMPERATURE;
            refigeratorSeekBar.setProgress(deviceTemperature);
            refrigeratorTemperatureNumber.setText(deviceTemperature + "°");
            deviceFreezerTemperature = Refrigerator.MODE_VACATION_TEMPERATURE_FREEZER;
            refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
            freezerTemperatureNumber.setText(deviceFreezerTemperature + "°");
            refrigeratorActualMode.setText(": " + deviceMode);
            modeRefrigeratorAuto.setEnabled(true);
            modeRefrigeratorFest.setEnabled(true);
            modeRefrigeratorVacation.setEnabled(false);
        }


        refrigeratorTemperatureNumber.setText(String.valueOf(deviceTemperature) + "°");
        refigeratorSeekBar.setProgress(deviceTemperature);
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
                viewModel.executeDeviceActionWithInt(deviceId, Refrigerator.ACTION_SET_TEMP, seekBar.getProgress()).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceMode = Refrigerator.MODE_DEFAULT;
                            refrigeratorActualMode.setText(": " + deviceMode);
                            modeRefrigeratorFest.setEnabled(true);
                            modeRefrigeratorAuto.setEnabled(false);
                            modeRefrigeratorVacation.setEnabled(true);
                            deviceTemperature = seekBar.getProgress();
                            break;
                    }
                });
            }
        });

        freezerTemperatureNumber.setText(String.valueOf(deviceFreezerTemperature) + "°");
        refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
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
                viewModel.executeDeviceActionWithInt(deviceId, Refrigerator.ACTION_SET_FREZ_TEMP, seekBar.getProgress()).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceMode = Refrigerator.MODE_DEFAULT;
                            refrigeratorActualMode.setText(": " + deviceMode);
                            modeRefrigeratorFest.setEnabled(true);
                            modeRefrigeratorAuto.setEnabled(false);
                            modeRefrigeratorVacation.setEnabled(true);
                            deviceFreezerTemperature = seekBar.getProgress();
                            break;
                    }
                });
            }
        });

        refrigeratorActualMode.setText(": " + deviceMode);
        modeRefrigeratorFest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceActionWithString(deviceId, Refrigerator.ACTION_SET_MODE, Refrigerator.MODE_PARTY).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceMode = Refrigerator.MODE_PARTY;
                            deviceTemperature = Refrigerator.MODE_PARTY_TEMPERATURE;
                            refigeratorSeekBar.setProgress(deviceTemperature);
                            refrigeratorTemperatureNumber.setText(deviceTemperature + "°");

                            deviceFreezerTemperature = Refrigerator.MODE_PARTY_TEMPERATURE_FREEZER;
                            refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
                            freezerTemperatureNumber.setText(deviceFreezerTemperature + "°");

                            refrigeratorActualMode.setText(": " + deviceMode);

                            modeRefrigeratorFest.setEnabled(false);
                            modeRefrigeratorAuto.setEnabled(true);
                            modeRefrigeratorVacation.setEnabled(true);
                            Toast.makeText(getContext(), getString(R.string.refrigerator_mode_fest), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });
            
       modeRefrigeratorAuto.setOnClickListener(new View.OnClickListener() {
           @SuppressLint("SetTextI18n")
           @Override
           public void onClick(View v) {
               viewModel.executeDeviceActionWithString(deviceId, Refrigerator.ACTION_SET_MODE, Refrigerator.MODE_DEFAULT).observe(getViewLifecycleOwner(), resource -> {
                   switch (resource.status) {
                       case LOADING:
                           break;
                       case SUCCESS:
                           deviceMode = Refrigerator.MODE_DEFAULT;
                           deviceFreezerTemperature = Refrigerator.MODE_DEFAULT_TEMPERATURE_FREEZER;
                           refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
                           freezerTemperatureNumber.setText(deviceFreezerTemperature + "°");

                           refrigeratorActualMode.setText(": " + deviceMode);

                           modeRefrigeratorFest.setEnabled(true);
                           modeRefrigeratorAuto.setEnabled(false);
                           modeRefrigeratorVacation.setEnabled(true);
                           Toast.makeText(getContext(), getString(R.string.refrigerator_mode_auto), Toast.LENGTH_SHORT).show();
                           break;
                   }
               });
           }
       });            
     
       modeRefrigeratorVacation.setOnClickListener(new View.OnClickListener() {
          @SuppressLint("SetTextI18n")
          @Override
          public void onClick(View v) {
              viewModel.executeDeviceActionWithString(deviceId, Refrigerator.ACTION_SET_MODE, Refrigerator.MODE_VACATION).observe(getViewLifecycleOwner(), resource -> {
                  switch (resource.status) {
                      case LOADING:
                          break;
                      case SUCCESS:
                           deviceMode = Refrigerator.MODE_VACATION;
                           deviceTemperature = Refrigerator.MODE_VACATION_TEMPERATURE;
                           refigeratorSeekBar.setProgress(deviceTemperature);
                           refrigeratorTemperatureNumber.setText(deviceTemperature + "°");

                           deviceFreezerTemperature = Refrigerator.MODE_VACATION_TEMPERATURE_FREEZER;
                           refigeratorFreezerSeekBar.setProgress(deviceFreezerTemperature);
                           freezerTemperatureNumber.setText(deviceFreezerTemperature + "°");

                          refrigeratorActualMode.setText(": " + deviceMode);

                          modeRefrigeratorFest.setEnabled(true);
                          modeRefrigeratorAuto.setEnabled(true);
                          modeRefrigeratorVacation.setEnabled(false);
                          Toast.makeText(getContext(), getString(R.string.refrigerator_mode_vacations), Toast.LENGTH_SHORT).show();
                          break;
                  }
              });
          }
      });  
      


        return refrigeratorFragmentLayout;
    }
}



