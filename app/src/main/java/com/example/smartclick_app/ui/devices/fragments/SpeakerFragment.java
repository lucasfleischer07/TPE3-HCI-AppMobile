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
import com.example.smartclick_app.model.Devices.Speaker;
import com.example.smartclick_app.ui.devices.playlistDialog;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpeakerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeakerFragment extends Fragment {

    private String deviceName;
    private String deviceId;
    private int deviceVolume;
    private String deviceGenre;
    private String deviceStatus;


    String[] itemsDropMenu = {"classical", "country", "dance", "latina", "pop","rock"};
    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;


    public SpeakerFragment() {
        // Required empty public constructor
    }


    public static SpeakerFragment newInstance(Speaker device) {
        SpeakerFragment fragment = new SpeakerFragment();
        Bundle args = new Bundle();
        args.putString("deviceName", device.getName());
        args.putString("deviceId", device.getId());
        args.putInt("deviceVolume", device.getVolume());
        args.putString("deviceGenre", device.getGenre());
        args.putString("deviceStatus", device.getStatus());
        Log.d("nombre", device.getName());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            deviceName = getArguments().getString("deviceName");
            deviceId = getArguments().getString("deviceId");
            deviceVolume = getArguments().getInt("deviceVolume");
            deviceGenre = getArguments().getString("deviceGenre");
            deviceStatus = getArguments().getString("deviceStatus");
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

//        String[] itemsDropMenu = {"Classical", "Country", "Dance", "Latina", "Pop","Rock"};
//        AutoCompleteTextView autoCompleteTextView;
//        ArrayAdapter<String> adapterItems;

        autoCompleteText = speakerFragmentLayout.findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item,itemsDropMenu);
        autoCompleteText.setAdapter(adapterItems);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deviceGenre = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), getString(R.string.speaker_gender_toast) +" "+ deviceGenre, Toast.LENGTH_SHORT).show();
            }
        });



        Button speakerPlaylist = speakerFragmentLayout.findViewById(R.id.speakerPlaylist);
//        TODO: Ver de meter la del estado acrual de la api
//        ovenActualHeatSource.setText();
        speakerPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: Meter la accion de llamar a la api
                String songs;
                switch (deviceGenre){
                    case Speaker.GENDER_CLASSICAL:
                         songs = "Nombre: Speaking Unto Nations (Beethoven Symphony no 7 - II), duración: 5:02\n" +
                                "Nombre: The Well-Tempered Clavier: Book 1, Prelude in C Major, duración: 2:19\n" +
                                "Nombre: Handel / Orch. Hale: Keyboard Suite in D Minor, duración: 3:27\n" +
                                "Nombre: Piano Sonata No. 14 in C-Sharp Minor, Op. 27 No. 2, duración: 7:22";
                    break;

                    case Speaker.GENDER_COUNTRY:
                         songs = "Nombre: Singles You Up, duración: 3:02\n" +
                                "Nombre: Tequila, duración: 3:15\n" +
                                "Nombre: Get Along, duración: 3:19\n" +
                                "Nombre: What Ifs, duración: 3:08\n" +
                                "Nombre: Simple, duración: 3:05";
                    break;

                    case Speaker.GENDER_DANCE:
                        songs = "pedido a api";
                        break;

                    case Speaker.GENDER_LATINA:
                    songs = "pedido a api 2";
                    break;

                    case Speaker.GENDER_POP:
                    songs = "pedido a api 3";
                    break;

                    case Speaker.GENDER_ROCK:
                    songs = "pedido a api 4";
                    break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + deviceGenre);
                }
                playlistDialog(songs);
                Toast.makeText(getContext(), getString(R.string.speaker_playlist_toast), Toast.LENGTH_SHORT).show();

            }
        });

                return speakerFragmentLayout;
    }

    public void playlistDialog(String songs) {
        playlistDialog playlist = new playlistDialog(songs);
        playlist.show(getChildFragmentManager(), "h");
    }
}
















