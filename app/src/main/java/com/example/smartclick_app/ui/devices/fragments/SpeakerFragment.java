package com.example.smartclick_app.ui.devices.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.DeviceRepository;
import com.example.smartclick_app.model.Devices.Door;
import com.example.smartclick_app.model.Devices.Oven;
import com.example.smartclick_app.model.Devices.Refrigerator;
import com.example.smartclick_app.model.Devices.Speaker;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.devices.DeviceViewModel;
import com.example.smartclick_app.ui.devices.playlistDialog;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

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

    private DeviceViewModel viewModel;



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
//        args.putStringArrayList("deviceSongs", device.get);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Activity activity = getActivity();
        MyApplication application = (MyApplication) activity.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(DeviceRepository.class, application.getDeviceRepository());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DeviceViewModel.class);

        ViewGroup speakerFragmentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_speaker, container, false);

        TextView textViewDeviceName = speakerFragmentLayout.findViewById(R.id.speakerName);
        textViewDeviceName.setText(deviceName);

        Button speakerPlayButton = speakerFragmentLayout.findViewById(R.id.speakerPlay);
        Button speakerStopButton = speakerFragmentLayout.findViewById(R.id.speakerStop);
        Button speakerPauseButton = speakerFragmentLayout.findViewById(R.id.speakerPause);
        Button speakerBackwardButton = speakerFragmentLayout.findViewById(R.id.speakerBackward);
        Button speakerForwardButton = speakerFragmentLayout.findViewById(R.id.speakerForward);


        if(Objects.equals(deviceStatus, Speaker.ACTION_PLAY)) {
            speakerPlayButton.setVisibility(View.GONE);
            speakerBackwardButton.setVisibility(View.VISIBLE);
            speakerForwardButton.setVisibility(View.VISIBLE);
            speakerStopButton.setVisibility(View.VISIBLE);
            speakerPauseButton.setVisibility(View.VISIBLE);
        } else if(Objects.equals(deviceStatus, Speaker.ACTION_PAUSE)) {
            speakerPlayButton.setVisibility(View.VISIBLE);
            speakerBackwardButton.setVisibility(View.GONE);
            speakerForwardButton.setVisibility(View.GONE);
            speakerStopButton.setVisibility(View.VISIBLE);
            speakerPauseButton.setVisibility(View.GONE);
        } else if(Objects.equals(deviceStatus, Speaker.ACTION_STOP)) {
            speakerPlayButton.setVisibility(View.VISIBLE);
            speakerBackwardButton.setVisibility(View.GONE);
            speakerForwardButton.setVisibility(View.GONE);
            speakerStopButton.setVisibility(View.GONE);
            speakerPauseButton.setVisibility(View.GONE);
        }


        speakerPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Speaker.ACTION_PLAY).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus = Speaker.ACTION_PLAY;
                            speakerPlayButton.setVisibility(View.GONE);
                            speakerBackwardButton.setVisibility(View.VISIBLE);
                            speakerForwardButton.setVisibility(View.VISIBLE);
                            speakerStopButton.setVisibility(View.VISIBLE);
                            speakerPauseButton.setVisibility(View.VISIBLE);

                            Toast.makeText(getContext(), getString(R.string.speaker_play), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });

            }
        });

        speakerPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Speaker.ACTION_PAUSE).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus = Speaker.ACTION_PAUSE;
                            speakerPlayButton.setVisibility(View.VISIBLE);
                            speakerBackwardButton.setVisibility(View.GONE);
                            speakerForwardButton.setVisibility(View.GONE);
                            speakerStopButton.setVisibility(View.VISIBLE);
                            speakerPauseButton.setVisibility(View.GONE);

                            Toast.makeText(getContext(), getString(R.string.speaker_pause), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });

            }
        });

        speakerStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.executeDeviceAction(deviceId, Speaker.ACTION_STOP).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceStatus = Speaker.ACTION_STOP;
                            speakerPlayButton.setVisibility(View.VISIBLE);
                            speakerBackwardButton.setVisibility(View.GONE);
                            speakerForwardButton.setVisibility(View.GONE);
                            speakerStopButton.setVisibility(View.GONE);
                            speakerPauseButton.setVisibility(View.GONE);

                            Toast.makeText(getContext(), getString(R.string.speaker_stop), Toast.LENGTH_SHORT).show();
                            break;
                    }
                });

            }
        });


        TextView speakerVolumeNumber = speakerFragmentLayout.findViewById(R.id.speakerVolumeNumber);
        SeekBar speakerSeekBar = speakerFragmentLayout.findViewById(R.id.speakerSeekBar);

        speakerVolumeNumber.setText(String.valueOf(deviceVolume));
        speakerSeekBar.setProgress(deviceVolume);
        speakerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // increment 1 in progress and
                // increase the textsize
                // with the value of progress
                speakerVolumeNumber.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // This method will automatically
                // called when the user touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // This method will automatically called when the user stops touching the SeekBar
                viewModel.executeDeviceActionWithInt(deviceId, Speaker.ACTION_SET_VOLUME, seekBar.getProgress()).observe(getViewLifecycleOwner(), resource -> {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            deviceVolume = seekBar.getProgress();
                            break;
                    }
                });

            }
        });


        autoCompleteText = speakerFragmentLayout.findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item, itemsDropMenu);
        autoCompleteText.setAdapter(adapterItems);
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deviceGenre = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(), getString(R.string.speaker_gender_toast) +" "+ deviceGenre, Toast.LENGTH_SHORT).show();
            }
        });



        Button speakerPlaylist = speakerFragmentLayout.findViewById(R.id.speakerPlaylist);

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
                        songs = "Nombre: All Day And Night, duración: 2:49\n" +
                                "Nombre: No Sleep, duración: 3:27\n" +
                                "Nombre: Speechless, duración: 3:37\n" +
                                "Nombre: Carry On, duración: 3:35\n" +
                                "Nombre: Better When You're Gone, duración: 3:12";
                        break;

                    case Speaker.GENDER_LATINA:
                    songs = "Nombre: Prometiste, duración: 5:05\n" +
                            "Nombre: Tu de Que Vas, duración: 3:58\n" +
                            "Nombre: Me Dedique a Perderte, duración: 3:51\n" +
                            "Nombre: El Sol No Regresa, duración: 3:48\n" +
                            "Nombre: Antologia, duración: 4:11";
                    break;

                    case Speaker.GENDER_POP:
                    songs = "Nombre: Memories, duración: 3:09\n" +
                            "Nombre: Dance Monkey, duración: 3:29\n" +
                            "Nombre: Don't Call Me Angel, duración: 3:10\n" +
                            "Nombre: Graveyard, duración: 3:01\n" +
                            "Nombre: Someone You Loved, duración: 3:02\n" +
                            "Nombre: Liar, duración: 3:27";
                    break;

                    case Speaker.GENDER_ROCK:
                    songs = "Nombre: Hotel California, duración: 6:49\n" +
                            "Nombre: Bohemian Rapsody, duración: 5:54\n" +
                            "Nombre: Sweet Child O' Mine, duración: 5:54\n" +
                            "Nombre: Have You Ever Seen The Rain, duración: 2:40\n" +
                            "Nombre: Come Together, duración: 4:19";
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
















