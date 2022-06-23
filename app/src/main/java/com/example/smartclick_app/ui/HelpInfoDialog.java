package com.example.smartclick_app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.smartclick_app.R;

public class HelpInfoDialog extends AppCompatDialogFragment {
    private String info;
    private String location;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builer = new AlertDialog.Builder(getActivity());
        builer.setTitle(location).setMessage(info).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builer.create();
    }

    public HelpInfoDialog(String info, String location) {
        this.info = info;
        this.location = location;
    }
}
