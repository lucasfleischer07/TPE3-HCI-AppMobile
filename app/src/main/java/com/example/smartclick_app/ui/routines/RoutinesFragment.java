package com.example.smartclick_app.ui.routines;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.model.Actions;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.MainActivity;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.routines.fragment.RoutineGenericFragment;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ViewGroup routinesViewGroup;
    private LinearLayout generalLinearLayout;

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

        routinesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_routines, container, false);
        generalLinearLayout = routinesViewGroup.findViewById(R.id.routineLinearLayout);
        refreshData();
        return routinesViewGroup;
    }


    private void forRoutines(List<Routine> routines, LinearLayout generalLinearLayout) {
        generalLinearLayout.removeAllViews();
        generalLinearLayout.removeAllViewsInLayout();

        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String actualId=preferences.getString("actualHouse",null);

        if(actualId==null && routines.size()>0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("actualHouse",routines.get(0).getHouseId());
            editor.apply();
            actualId=preferences.getString("actualHouse",null);
        }

        Log.d("Size", String.valueOf(routines.size()));
        for (int i = 0; i < routines.size(); i++) {
            Log.d("Size", "En el for: " + i);
            if (routines.get(i).getHouseId().equals(actualId)) {
                Log.d("Rutina con nombre",routines.get(i).getName());

                LinearLayout row = new LinearLayout(getContext());
                MaterialButton routineButton = new MaterialButton(getContext());
                routineButton.setText(routines.get(i).getName());
                routineButton.setId(i);
                routineButton.setBackgroundColor(routineButton.getContext().getResources().getColor(R.color.rooms_and_routine_buttons));
                getChildFragmentManager().beginTransaction().add(generalLinearLayout.getId(), RoutineGenericFragment.newInstance(routines.get(i))).commit();

            }
        }
    }

     void refreshData(){
         MainActivity activity = (MainActivity) requireActivity();
         MyApplication application = (MyApplication) activity.getApplication();
         ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
         viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutineViewModel.class);

         List<Routine> routines = new ArrayList<>();

         viewModel.getRoutines().observe(getViewLifecycleOwner(), resource -> {
             switch (resource.status) {
                 case LOADING:
                     break;
                 case SUCCESS:
                     routines.clear();
                     if (resource.data != null && resource.data.size() > 0) {
                         routines.addAll(resource.data);
                         forRoutines(routines, generalLinearLayout);
                     }
                     break;
             }
         });
     }


     @Override
        public void onResume() {
         refreshData();
         super.onResume();
         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
     }

}

//                routineButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        viewModel.executeRoutine(routines.get(finalI).getId()).observe(getViewLifecycleOwner(), resource -> {
//                            switch (resource.status) {
//                                case LOADING:
////                    activity.showProgressBar();
//                                    break;
//                                case SUCCESS:
////                    activity.hideProgressBar();
//                                    Toast.makeText(getContext(), getString(R.string.routine_execute) + " " + routines.get(finalI).getName(), Toast.LENGTH_SHORT).show();
//                                    break;
//                            }
//                        });
////                    viewModel.executeRoutine(routines.get(finalI).getId());
//                    }
//                });


//            View horizontalLine = new View(getContext());
//            horizontalLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
//            horizontalLine.setBackgroundColor(getResources().getColor(R.color.black));

//                row.setGravity(Gravity.CENTER);
//                row.setPadding(3, 1, 50, 1);
//                row.addView(routineButton);
//                generalLinearLayout.addView(row);

//            row = new LinearLayout(getContext());
//            row.setPadding(0, 20, 0, 20);
//            row.addView(horizontalLine);
//            generalLinearLayout.addView(row);