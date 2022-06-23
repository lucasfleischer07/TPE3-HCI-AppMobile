package com.example.smartclick_app.ui.routines.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.model.Actions;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Devices.Lightbulb;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;
import com.example.smartclick_app.ui.routines.RoutineViewModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutineGenericFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutineGenericFragment extends Fragment {

//    private String routineDeviceName;
//    private String routineDeviceId;
//    private String routineActionsName;
//    private String routineActionsParams;
    private Routine routineActual;
    private String routineColor;

    private RoutineViewModel viewModel;


    public RoutineGenericFragment() {
        // Required empty public constructor
    }


    public static RoutineGenericFragment newInstance(Routine routineActual) {
        RoutineGenericFragment fragment = new RoutineGenericFragment();
        Bundle args = new Bundle();
        args.putParcelable("routineActual", routineActual);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            routineActual = getArguments().getParcelable("routineActual");
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        routineColor = preferences.getString(routineActual.getId(),null);
        if(routineColor==null){
            routineColor=String.valueOf(R.color.rooms_and_routine_buttons);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity activity = getActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutineViewModel.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ViewGroup routineFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_routine_generic, container, false);
        routineFragmentLayout.setBackgroundColor((int) Long.parseLong(routineColor.replace("#", ""), 16));
        Button routineExecuteButton = routineFragmentLayout.findViewById(R.id.routineExecuteButton);
        Button routineInformationButton = routineFragmentLayout.findViewById(R.id.routineInformationButton);
        Button colorPickerButton = routineFragmentLayout.findViewById(R.id.colorPickerButton);

        TextView routineNameTextView = routineFragmentLayout.findViewById(R.id.routineNameTextView);
        routineNameTextView.setText(routineActual.getName());

        routineExecuteButton.setText(R.string.execute_routine);
        routineExecuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeRoutine(routineActual.getId()).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            Toast.makeText(getContext(), getString(R.string.routine_execute) + " " + routineActual.getName(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        routineInformationButton.setText(R.string.information_routine);
        routineInformationButton.setBackgroundColor(getResources().getColor(R.color.teal_700));
        routineInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder routineInfo = new StringBuilder();

                for(Map.Entry<Device,Actions> deviceActionsEntry : routineActual.getDeviceAndActionsMap().entrySet()) {
                    routineInfo.append(deviceActionsEntry.getKey().getName()).append(": ").append(deviceActionsEntry.getValue().getActionName()).append("\n");
                }

                routineDetailDialog(routineInfo.toString(), routineActual.getName());

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
                        editor.putString(routineActual.getId(),Integer.toHexString(color));
                        routineColor=Integer.toHexString(color);
                        routineFragmentLayout.setBackgroundColor((int) Long.parseLong(routineColor.replace("#", ""), 16));
                        editor.apply();
                        Toast.makeText(getContext(), getString(R.string.lamp_color_confirm), Toast.LENGTH_SHORT).show();
                    }
                });
                colorPicker.show();
            }
        });

        return routineFragmentLayout;
    }

    private void routineDetailDialog(String routineInfo, String routineName) {
        RoutineDetailDialog routineDetails = new RoutineDetailDialog(routineInfo, routineName);
        routineDetails.show(getChildFragmentManager(), "Routine");
    }
}