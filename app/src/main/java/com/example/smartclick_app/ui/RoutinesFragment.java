package com.example.smartclick_app.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.smartclick_app.R;

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


        ViewGroup routinesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_routines, container, false);
        LinearLayout generalLinearLayout = routinesViewGroup.findViewById(R.id.routineLinearLayout);

        for(int i = 0; i < 20; i++) {
//            TODO: el id deberia ser el nombre de la rutina o algo asi
            //Routine newRoutine=new Routine(i,"Rutina",3,new HashMap<>());
            //routineList.add(newRoutine);
            int id = i;
            LinearLayout row = new LinearLayout(getContext());

            Button routineButton = new Button(getContext());
            routineButton.setText("Button " + id);
            routineButton.setId(id);
            routineButton.setBackgroundColor(routineButton.getContext().getResources().getColor(R.color.main_act_background));


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

        return routinesViewGroup;
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // * Si no se selecciono casa arranca en null
    }
}