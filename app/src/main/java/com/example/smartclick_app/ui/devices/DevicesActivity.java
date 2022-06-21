package com.example.smartclick_app.ui.devices;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.smartclick_app.R;
import com.example.smartclick_app.databinding.ActivityDevicesBinding;
import com.example.smartclick_app.model.Room;
import com.google.android.material.button.MaterialButton;

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

        LinearLayout generalLinearLayout = findViewById(R.id.devicesRoomLinearLayout);

//        TODO: Aca hay que pasarle los devices
        forDevices(generalLinearLayout);

    }


//  TODO: Cambiar la lista de rooms por la lista de devices
    private void forDevices(LinearLayout generalLinearLayout){
        for(int i = 0; i < 2 ; i++) {
            LinearLayout row = new LinearLayout(this);
            LinearLayout card = new LinearLayout(this);
            card.setBackgroundColor(card.getContext().getResources().getColor(R.color.main_act_background));


//            Icono del parlante
            TextView deviceIcon = new TextView(this);
            deviceIcon.setCompoundDrawablesWithIntrinsicBounds(R.drawable.speaker_asset, 0, 0, 0);
            deviceIcon.setText("Parlante");
            deviceIcon.setTextSize(20);
            card.addView(deviceIcon);


            card = new LinearLayout(this);
            card.setDividerPadding(10);
            card.setBackgroundColor(card.getContext().getResources().getColor(R.color.main_act_background));
            MaterialButton deviceButton= new MaterialButton(this);
            deviceButton.setId(i);
            deviceButton.setTextSize(25);
            deviceButton.setIcon(ContextCompat.getDrawable(this, R.drawable.play_asset));
            deviceButton.setTransformationMethod(null);
//            deviceButton.setBackgroundColor(deviceButton.getContext().getResources().getColor(R.color.main_act_background));
//            deviceButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            int finalI = i;
            deviceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String roomId = rooms.get(finalI).getId();
//                    String roomName = rooms.get(finalI).getName();
//                    Intent intent = new Intent(getContext(), DevicesActivity.class);
//                    intent.putExtra("ROOM_ID", roomId);
//                    intent.putExtra("ROOM_NAME", roomName);
//                    startActivity(intent);
//                    Toast.makeText(getContext(), getString(R.string.room_selected) + " " + rooms.get(finalI).getName(), Toast.LENGTH_SHORT).show();
                }
            });
            card.addView(deviceButton);


            row.setGravity(Gravity.CENTER);
            row.setPadding(50, 30, 50, 1);
            row.addView(card);
//            row.addView(deviceButton);
            generalLinearLayout.addView(row);
        }

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