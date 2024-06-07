package com.example.taskque_mobile_app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.util.ArrayList;

public class ApplicationClass extends Application
{
   public static ArrayList<Timers> pendingList;
    @Override
    public void onCreate() {
        super.onCreate();
        pendingList = new ArrayList<>();

        // Creating a notification channel for showing notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("not","not", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
