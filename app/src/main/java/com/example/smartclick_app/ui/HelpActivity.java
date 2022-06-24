package com.example.smartclick_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartclick_app.R;
import com.example.smartclick_app.databinding.ActivityHelpBinding;

public class HelpActivity extends AppCompatActivity {

    private ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHelpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button homeSelectionInfo = findViewById(R.id.homeSelectionInfo);
        Button routineSelectionInfo = findViewById(R.id.routineSelectionInfo);
        Button deviceSelectionInfo = findViewById(R.id.deviceSelectionInfo);

        homeSelectionInfo.setText(R.string.tab_houses);
        homeSelectionInfo.setBackgroundColor(homeSelectionInfo.getContext().getResources().getColor(R.color.teal_700));
        homeSelectionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpInfoDialog homeHelpInfoDialog = new HelpInfoDialog(getString(R.string.description_help_home), getString(R.string.home_help_title));
                homeHelpInfoDialog.show(getSupportFragmentManager(), "HomeHelp");
            }
        });

        routineSelectionInfo.setText(R.string.tab_routines);
        routineSelectionInfo.setBackgroundColor(routineSelectionInfo.getContext().getResources().getColor(R.color.teal_700));
        routineSelectionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpInfoDialog routineHelpInfoDialog = new HelpInfoDialog(getString(R.string.description_help_routine), getString(R.string.routine_help_title));
                routineHelpInfoDialog.show(getSupportFragmentManager(), "RoutineHelp");
            }
        });

        deviceSelectionInfo.setText(R.string.tab_devices);
        deviceSelectionInfo.setBackgroundColor(deviceSelectionInfo.getContext().getResources().getColor(R.color.teal_700));
        deviceSelectionInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpInfoDialog deviceHelpInfoDialog = new HelpInfoDialog(getString(R.string.description_help_device), getString(R.string.device_help_title));
                deviceHelpInfoDialog.show(getSupportFragmentManager(), "DeviceHelp");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
}