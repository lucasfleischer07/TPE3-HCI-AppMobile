package com.example.smartclick_app.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclick_app.R;
import com.example.smartclick_app.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;


    //    TODO: Hardcodeado, estoy hay que cambiarlo por las casas que esten en la API
    private final String [] housesOptions = new String[]{"Casa 1", "Casa 2", "Casa 3"};
    private int housesOptionsIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        housesOptionsIndex= preferences.getInt("actualHouse",-1);

        Button buttonHouseSelector = findViewById(R.id.openHouseSelectorButton);
        TextView houseSelected = (TextView) findViewById(R.id.houseSelected);

        TextView houseSelectedText = (TextView) findViewById(R.id.houseSelectedText);
        if(housesOptions==null || housesOptions.length==0) {
            houseSelectedText.setText(R.string.house_selected_text_null);//Cambiar texto
            buttonHouseSelector.setEnabled(false);
        } else {
            if(housesOptionsIndex!=-1){
                houseSelected.setText(housesOptions[housesOptionsIndex]);
            }else{
                houseSelected.setText(housesOptions[0]);
            }
            houseSelectedText.setText(R.string.house_selected_text);
        }


        buttonHouseSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this).setTitle("Select your house: ");
                builder.setTitle(R.string.title_select_house);
                builder.setPositiveButton(getString(R.string.confirm_house_selected), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = preferences.edit();

                        if(housesOptionsIndex == 0) {
                            houseSelected.setText(housesOptions[0]);
                        }
                        houseSelected.setText(housesOptions[housesOptionsIndex]);
                        houseSelectedText.setText(R.string.house_selected_text);
                        Toast.makeText(getApplicationContext(), R.string.selected_house, Toast.LENGTH_SHORT).show();
                        if(housesOptions.length == 0){
                            editor.putInt("actualHouse",-1);
                        }else {
                            editor.putInt("actualHouse", housesOptionsIndex);
                        }
                        editor.apply();
                    }
                });
                builder.setSingleChoiceItems(housesOptions, housesOptionsIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        housesOptionsIndex = which;
                    }
                });
                builder.show();
            }
        });
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