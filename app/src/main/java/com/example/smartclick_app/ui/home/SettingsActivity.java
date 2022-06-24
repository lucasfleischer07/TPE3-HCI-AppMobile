package com.example.smartclick_app.ui.home;

import static com.example.smartclick_app.data.Status.LOADING;
import static com.example.smartclick_app.data.Status.SUCCESS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.databinding.ActivitySettingsBinding;
import com.example.smartclick_app.model.House;
import com.example.smartclick_app.ui.RepositoryViewModelFactory;
import com.example.smartclick_app.ui.room.RoomViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private List<House> houses;

    private int housesOptionsIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MyApplication application = (MyApplication)this.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(HouseRepository.class, application.getHouseRepository());
        HouseViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(HouseViewModel.class);
        List<House> houses=new ArrayList<>();
        viewModel.gethouses().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;

                case SUCCESS:
                    houses.clear();
                    if (resource.data != null && resource.data.size() > 0) {
                        houses.addAll(resource.data);
                        setHousesList(houses);
                    }
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        MyApplication application = (MyApplication)this.getApplication();
        ViewModelProvider.Factory viewModelFactory = new RepositoryViewModelFactory<>(HouseRepository.class, application.getHouseRepository());
        HouseViewModel viewModel = new ViewModelProvider(this, viewModelFactory).get(HouseViewModel.class);
        List<House> houses=new ArrayList<>();
        viewModel.gethouses().observe(this, resource -> {
            switch (resource.status) {
                case LOADING:
                    break;

                case SUCCESS:
                    houses.clear();
                    if (resource.data != null) {
                        houses.addAll(resource.data);
                        setHousesList(houses);

                    }

            }
        });

    }

    private void setHousesList(List<House> houses){
        StringBuilder[] houseNames = new StringBuilder[houses.size()];
        Arrays.setAll(houseNames, i -> new StringBuilder(houses.get(i).getName()));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String actualId=preferences.getString("actualHouse",null);
        if(actualId!=null){
            for(int i=0;i<=houses.size();i++){
                if(i==houses.size()){
                    actualId=null;
                    housesOptionsIndex=-1;}
                else if(houses.get(i).getId().equals(actualId)){
                    housesOptionsIndex=i;
                    break;
                }
            }

        }
        Button buttonHouseSelector = findViewById(R.id.openHouseSelectorButton);
        TextView houseSelected = (TextView) findViewById(R.id.houseSelected);
        TextView houseSelectedText = (TextView) findViewById(R.id.houseSelectedText);

        if(houses.size()==0) {
            houseSelectedText.setText(R.string.house_selected_text_null);
            buttonHouseSelector.setEnabled(false);
        } else {
            if(housesOptionsIndex!=-1){
                houseSelected.setText(houses.get(housesOptionsIndex).getName());
            }else{
                houseSelected.setText("No house selected");
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
                            houseSelected.setText(houseNames[0]);
                        }
                        houseSelected.setText(houseNames[housesOptionsIndex]);
                        houseSelectedText.setText(R.string.house_selected_text);
                        Toast.makeText(getApplicationContext(), R.string.selected_house, Toast.LENGTH_SHORT).show();
                        if(houses.size() == 0){
                            editor.putString("actualHouse",null);
                        }else {
                            editor.putString("actualHouse", houses.get(housesOptionsIndex).getId());

                        }
                        editor.apply();
                    }
                });

                builder.setSingleChoiceItems(houseNames, housesOptionsIndex, new DialogInterface.OnClickListener() {
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
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

}