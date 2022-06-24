package com.example.smartclick_app.ui;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import com.example.smartclick_app.MyApplication;
import com.example.smartclick_app.R;
import com.example.smartclick_app.data.HouseRepository;
import com.example.smartclick_app.data.Resource;
import com.example.smartclick_app.data.RoomRepository;
import com.example.smartclick_app.data.remote.NotificationsWorker;
import com.example.smartclick_app.model.House;
import com.example.smartclick_app.model.Room;
import com.example.smartclick_app.ui.home.SettingsActivity;
import com.example.smartclick_app.ui.room.RoomFragment;
import com.example.smartclick_app.ui.routines.RoutinesFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.smartclick_app.ui.main.SectionsPagerAdapter;
import com.example.smartclick_app.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final String WORKER_TAG = "com.example.smartclick_app.data.remote.NotificationsWorker";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        RoomFragment room = RoomFragment.newInstance(null, null);
        FragmentManager manager = getSupportFragmentManager();
        RoutinesFragment routine = RoutinesFragment.newInstance(null, null);
        if (!isTablet() || (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)) {
            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
            ViewPager viewPager = binding.viewPager;
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = binding.tabs;
            tabs.setupWithViewPager(viewPager);
            viewPager.setOffscreenPageLimit(2);
        }
        LiveData<Resource<List<Room>>> rooms;
        MyApplication application = (MyApplication) this.getApplication();
        RoomRepository roomRepository = application.getRoomRepository();
        HouseRepository houseRepository = application.getHouseRepository();
        rooms = roomRepository.getRooms();
        LiveData<Resource<List<House>>> houses = houseRepository.getHouses();
        setUpWorker();
        createNotificationChannel();

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_help:
                Intent intent2 = new Intent(this, HelpActivity.class);
                startActivity(intent2);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }


    void setUpWorker() {
        PeriodicWorkRequest notificationsRequest =
                new PeriodicWorkRequest.Builder(NotificationsWorker.class, 15, TimeUnit.MINUTES)
                        .build();
        WorkManager
                .getInstance(this)
                .enqueue(notificationsRequest);
        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(notificationsRequest.getId())
                ;
    }

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel("1", getString(R.string.notifications_device_chg), importance);
        channel.setDescription(getString(R.string.notifications_main_act));
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}