package com.example.smartclick_app.ui.devices.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpeakerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeakerFragment extends Fragment {

    private String deviceName;

//    String[] itemsDropMenu = {"Classical", "Country", "Dance", "Latina", "Pop","Rock"};
//    AutoCompleteTextView autoCompleteTextView;
//    ArrayAdapter<String> adapterItems;


    public SpeakerFragment() {
        // Required empty public constructor
    }


    public static SpeakerFragment newInstance(String deviceName) {
        SpeakerFragment fragment = new SpeakerFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", deviceName);
        Log.d("nombre", deviceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");
        }
        Log.d("nombreCreate", deviceName);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup speakerFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_speaker, container, false);

        TextView textViewDeviceName = speakerFragmentLayout.findViewById(R.id.speakerName);
        textViewDeviceName.setText(deviceName);

        Button speakerPlay = speakerFragmentLayout.findViewById(R.id.speakerPlay);
//        TODO: Ver de meter la del estado acrual de la api
//        ovenActualHeatSource.setText();
        speakerPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                Toast.makeText(getContext(), getString(R.string.speaker_play), Toast.LENGTH_SHORT).show();
            }
        });

        TextView speakerVolumeNumber = speakerFragmentLayout.findViewById(R.id.speakerVolumeNumber);
        SeekBar speakerSeekBar = speakerFragmentLayout.findViewById(R.id.speakerSeekBar);
//        TODO: Ver si nos podemos traer el brillo para poder setearlo desde un principio y no solo cuando lo setea el usuario
//        lampTextViewPercentage.setText();
        speakerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // increment 1 in progress and
                // increase the textsize
                // with the value of progress
                speakerVolumeNumber.setText(progress + "%");
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

        String[] itemsDropMenu = {"Classical", "Country", "Dance", "Latina", "Pop","Rock"};
        AutoCompleteTextView autoCompleteTextView;
        ArrayAdapter<String> adapterItems;

        autoCompleteTextView = speakerFragmentLayout.findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_items,itemsDropMenu);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), "Item"+ item, Toast.LENGTH_SHORT).show();
            }
        });


        return speakerFragmentLayout;
    }
}