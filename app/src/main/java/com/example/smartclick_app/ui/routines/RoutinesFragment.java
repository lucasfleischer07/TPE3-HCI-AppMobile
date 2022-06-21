package com.example.smartclick_app.ui.routines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.data.Status;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.MainActivity;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DevicesActivity;
import com.example.smartclick_app.ui.room.RoomViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoutinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoutinesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RoutineViewModel viewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoutinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoutinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoutinesFragment newInstance(String param1, String param2) {
        RoutinesFragment fragment = new RoutinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    //Esto mas tarde cambiarlo por tipo House
    private String selectedHouse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    //List<Routine> routineList= new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) requireActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutineViewModel.class);

        ViewGroup routinesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_routines, container, false);
        LinearLayout generalLinearLayout = routinesViewGroup.findViewById(R.id.routineLinearLayout);

        List<Routine> routines = new ArrayList<>();
        viewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
//                    activity.showProgressBar();
                    break;
                case SUCCESS:
//                    activity.hideProgressBar();
                    routines.clear();
                    if (resource.data != null && resource.data.size() > 0) {
                        routines.addAll(resource.data);
                        forRoutines(routines, generalLinearLayout);
                    }
                    break;
            }
        });

        return routinesViewGroup;
    }


    private void forRoutines(List<Routine> routines, LinearLayout generalLinearLayout) {
        for(int i = 0; i < routines.size(); i++) {
            LinearLayout row = new LinearLayout(getContext());
            MaterialButton routineButton = new MaterialButton(getContext());
            routineButton.setText(routines.get(i).getName());
            routineButton.setId(i);
            routineButton.setBackgroundColor(routineButton.getContext().getResources().getColor(R.color.rooms_and_routine_buttons));
            int finalI = i;
            routineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.executeRoutine(routines.get(finalI).getId()).observe(getViewLifecycleOwner(), resource -> {
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
//                    viewModel.executeRoutine(routines.get(finalI).getId());
                }
            });


            View horizontalLine = new View(getContext());
            horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
            horizontalLine.setBackgroundColor(getResources().getColor(R.color.black));

            row.setGravity(Gravity.END);
            row.setPadding(3, 1, 50, 1);
            row.addView(routineButton);
            generalLinearLayout.addView(row);

            row = new LinearLayout(getContext());
            row.setPadding(0, 20, 0, 20);
            row.addView(horizontalLine);
            generalLinearLayout.addView(row);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // * Si no se selecciono casa arranca en null
    }
}