package com.example.smartclick_app.ui.routines.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.model.Actions;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;
import com.example.smartclick_app.ui.routines.RoutineViewModel;

import java.util.List;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity activity = getActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutineViewModel.class);

        ViewGroup routineFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_routine_generic, container, false);

        Button routineExecuteButton = routineFragmentLayout.findViewById(R.id.routineExecuteButton);
        Button routineInformationButton = routineFragmentLayout.findViewById(R.id.routineInformationButton);

//        TODO: Meter los strings de ejecutar para el boton aunque lo puede poner directo en el xml
        routineExecuteButton.setText(routineActual.getName());
        routineExecuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeRoutine().observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
//                    activity.showProgressBar();
                            break;
                        case SUCCESS:
//                    activity.hideProgressBar();
                            Toast.makeText(getContext(), getString(R.string.routine_execute) + " " + routines.get(finalI).getName(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
            }
        });

        routineInformationButton.setText(R.string.execute_routine);
        routineInformationButton.setBackgroundColor(getResources().getColor(R.color.teal_700));


        return routineFragmentLayout;
    }
}