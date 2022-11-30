package com.example.myapp;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.myapp.NotificationHelper;

public class AlertReceiver extends BroadcastReceiver {
    String title, message;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1,nb.build());
        Log.d(TAG, "## Alarm!!!!!!!!!!!!!!!!!!");

    }
}