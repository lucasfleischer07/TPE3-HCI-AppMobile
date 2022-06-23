package com.example.smartclick_app.data.remote;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.smartclick_app.R;
import com.example.smartclick_app.ui.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class NotificationsWorker extends Worker {

    public static final String TAG = "NotificationWorker";
    public String URLDEVS = ApiClient.BASE_URL + "devices/";
    public static final String RESULT = "RESULT";

    public NotificationsWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Running worker...");
        Result result;
        try {

            URL url = new URL(URLDEVS);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));

                String line;
                StringBuilder buffer = new StringBuilder();
                while ((line = in.readLine()) != null)
                    buffer.append(line);
                in.close();

                Log.d(TAG, buffer.toString());
                if(buffer.toString().contains("result"))
                    compareResults(buffer.toString());

                Data outputData = new Data.Builder()
                        .putString(RESULT, buffer.toString())
                        .build();
                setProgressAsync(outputData);
                // Delay success result to report progress
                SystemClock.sleep(1000);
                result = Result.success();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            result = Result.failure();
        }

        Log.d(TAG, "Worker ran with result [" + result + "]");
        return result;
    }

    private void compareResults(String actual) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String previousDevices=preferences.getString("prevDevices",null);

        if(actual!=null && previousDevices!=null && !previousDevices.equals(actual)){

            showNotification();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("prevDevices",actual);
        editor.apply();
    }

    private void showNotification() {
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        if(myProcess.importance !=   ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntent(notificationIntent);
        final PendingIntent contentIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "1")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Cambios en dispositivos")
                .setContentText("Se han registrado cambios en sus dispositivos")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Se han registrado cambios en sus dispositivos"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from (getApplicationContext());
        notificationManager.notify(1, builder.build());}
    }
}
