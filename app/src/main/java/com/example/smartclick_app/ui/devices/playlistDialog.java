package com.example.smartclick_app.ui.devices;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

//import com.example.smartclick_app.ui.MainActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.smartclick_app.R;
import com.example.smartclick_app.ui.devices.fragments.SpeakerFragment;

public class playlistDialog extends AppCompatDialogFragment {

    private String songs;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builer = new AlertDialog.Builder(getActivity());
        builer.setTitle(getString(R.string.speaker_playlist_gender)).setMessage(songs).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builer.create();
    }

    public playlistDialog(String songs){
        this.songs = songs;
    }
}
