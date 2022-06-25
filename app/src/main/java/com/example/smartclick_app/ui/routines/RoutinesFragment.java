package com.example.smartclick_app.ui.routines;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.data.RoutineRepository;
import com.example.smartclick_app.model.Actions;
import com.example.smartclick_app.model.Device;
import com.example.smartclick_app.model.House;
import com.example.smartclick_app.model.Routine;
import com.example.smartclick_app.ui.MainActivity;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.home.HouseViewModel;
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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RoutineViewModel viewModel;

    private String mParam1;
    private String mParam2;
    private ViewGroup routinesViewGroup;
    private LinearLayout generalLinearLayout;
    private List<House> houses;

    public RoutinesFragment() {
        // Required empty public constructor
    }

    public static RoutinesFragment newInstance(String param1, String param2) {
        RoutinesFragment fragment = new RoutinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private String selectedHouse;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        houses=new ArrayList<>();
        MainActivity activity = (MainActivity) requireActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(RoutineRepository.class, application.getRoutineRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RoutineViewModel.class);

        routinesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_routines, container, false);
        generalLinearLayout = routinesViewGroup.findViewById(R.id.routineLinearLayout);

        return routinesViewGroup;
    }


    private void forRoutines(List<Routine> routines, LinearLayout generalLinearLayout) {
        generalLinearLayout.removeAllViews();
        generalLinearLayout.removeAllViewsInLayout();
        for (Fragment fragmentChild : getChildFragmentManager().getFragments()) {
            getChildFragmentManager().beginTransaction().remove(fragmentChild).commit();
        }
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String actualId=preferences.getString("actualHouse",null);
        String actualHouseName=preferences.getString("actualHouseName",null);

        if(actualId==null && houses.size()>0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("actualHouse",houses.get(0).getId());
            editor.putString("actualHouseName",houses.get(0).getName());
            editor.apply();
            actualId=preferences.getString("actualHouse",null);
            actualHouseName=preferences.getString("actualHouseName",null);

        }else if(houses.size() == 0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("actualHouse",null);
            editor.putString("actualHouseName",null);
            editor.apply();
            actualId=preferences.getString("actualHouse",null);
            actualHouseName=preferences.getString("actualHouseName",null);

        }
        int added=0;

        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) && (getResources().getBoolean(R.bool.isTablet))) {
            LinearLayout rowLinearLayout;
            for (int i = 0; i < routines.size()/2 + routines.size() % 2; i++) {
                rowLinearLayout = new LinearLayout(getContext());
                rowLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLinearLayout.setId(1000000 + i);
                for(int j = 0; j < 2; j++) {
                    if(j + i*2 >= routines.size()) {
                        break;
                    }
                    if (routines.get(j + i*2).getHouseId().equals(actualId)) {
                        added++;

                        MaterialButton routineButton = new MaterialButton(getContext());
                        routineButton.setText(routines.get(j + i*2).getName());
                        routineButton.setId(j + i*2);
                        routineButton.setBackgroundColor(routineButton.getContext().getResources().getColor(R.color.rooms_and_routine_buttons));
                        getChildFragmentManager().beginTransaction().add(rowLinearLayout.getId(), RoutineGenericFragment.newInstance(routines.get(j + i*2))).commit();
                        Log.d("child if", String.valueOf(getChildFragmentManager().getFragments().size()));
                    }
                }
                generalLinearLayout.addView(rowLinearLayout);
            }
        } else {
            for (int i = 0; i < routines.size(); i++) {
                if (routines.get(i).getHouseId().equals(actualId)) {
                    added++;

                    MaterialButton routineButton = new MaterialButton(getContext());
                    routineButton.setText(routines.get(i).getName());
                    routineButton.setId(11000 + i);
                    routineButton.setBackgroundColor(routineButton.getContext().getResources().getColor(R.color.rooms_and_routine_buttons));
                    getChildFragmentManager().beginTransaction().add(generalLinearLayout.getId(), RoutineGenericFragment.newInstance(routines.get(i))).commit();
                    Log.d("child else", String.valueOf(getChildFragmentManager().getFragments().size()));
                }
            }
        }

        if(added==0 ||routines.size()==0) {
            TextView text = new TextView(this.getContext());
            if (actualId != null && actualHouseName!=null) {
                text.setText(getString(R.string.routine_null) + " " + actualHouseName);
            } else {
                text.setText(R.string.routine_house_null);
            }
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                text.setTextSize(generalLinearLayout.getWidth()/100);
            } else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                text.setTextSize(generalLinearLayout.getWidth()/50);
            }
            if(getResources().getBoolean(R.bool.isTablet) && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)) {
                text.setTextSize(generalLinearLayout.getWidth()/30);
            } else if(getResources().getBoolean(R.bool.isTablet) && (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)) {
                text.setTextSize(generalLinearLayout.getWidth()/55);

            }
            text.setPadding(20,100, 20, 0);
            text.setGravity(Gravity.CENTER);
            generalLinearLayout.addView(text);
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
                     if (resource.data != null) {
                         routines.addAll(resource.data);
                         forRoutines(routines, generalLinearLayout);
                     }
                     break;
             }
         });
     }


     @Override
        public void onResume() {
         SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
         MyApplication application = (MyApplication)this.getActivity().getApplication();
         ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(HouseRepository.class, application.getHouseRepository());
         HouseViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(HouseViewModel.class);
         viewModel.gethouses().observe(this, resource -> {
             switch (resource.status) {
                 case LOADING:
                     break;
                 case SUCCESS:
                     houses.clear();
                     if (resource.data != null) {
                         houses.addAll(resource.data);
                     }
                     refreshData();
             }
         });
         super.onResume();
     }


}
