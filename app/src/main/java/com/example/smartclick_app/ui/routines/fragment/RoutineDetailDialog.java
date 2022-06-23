package com.example.smartclick_app.ui.routines.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.smartclick_app.R;

public class RoutineDetailDialog extends AppCompatDialogFragment {
    private String routineInfo;
    private String routineName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builer = new AlertDialog.Builder(getActivity());
        builer.setTitle(getString(R.string.details_routine) + " " + routineName).setMessage(routineInfo).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builer.create();
    }

    public RoutineDetailDialog(String routineInfo, String routineName) {
        this.routineInfo = routineInfo;
        this.routineName = routineName;
    }
}
