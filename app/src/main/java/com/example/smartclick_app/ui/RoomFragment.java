package com.example.smartclick_app.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.smartclick_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final int [] testPassingInfoArray = new int[] {1, 2, 3, 4};

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DevicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoomFragment newInstance(String param1, String param2) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        ViewGroup devicesViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_room, container, false);
        LinearLayout generalLinearLayout = devicesViewGroup.findViewById(R.id.roomLinearLayout);

        for(int i = 0; i < 20; i++) {
//            TODO: el id deberia ser el nombre de la rutina o algo asi
            int id = i;
            LinearLayout row = new LinearLayout(getContext());

            Button roomButton= new Button(getContext());
            roomButton.setTransformationMethod(null);
            roomButton.setText("Room " + id);
            roomButton.setId(id);
            roomButton.setTextSize(25);
            roomButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            roomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DevicesActivity.class);
                    intent.putExtra("roomDevices", "testPassingInfoArray");
                    startActivity(intent);
                    Toast.makeText(getContext(), "Ha ingresado en la habitacion " + id, Toast.LENGTH_SHORT).show();
                }
            });

            row.setGravity(Gravity.CENTER);
            row.setPadding(50, 30, 50, 1);
            row.addView(roomButton);
            generalLinearLayout.addView(row);
        }

        return devicesViewGroup;
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        // * Si no se selecciono casa arranca en null
    }
}