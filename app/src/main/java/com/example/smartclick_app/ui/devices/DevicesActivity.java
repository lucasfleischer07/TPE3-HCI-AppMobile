package com.example.smartclick_app.ui.devices;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartclick_app.R;
import com.example.smartclick_app.databinding.ActivityDevicesBinding;
import com.example.smartclick_app.model.Room;

import java.util.ArrayList;
import java.util.List;

public class DevicesActivity extends AppCompatActivity {

    private ActivityDevicesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String roomId = intent.getStringExtra("ROOM_ID");
        String roomName = intent.getStringExtra("ROOM_NAME");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(roomName);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }// If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

}