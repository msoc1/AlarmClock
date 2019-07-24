package com.fixed4fun.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        String hour = String.valueOf(MainActivity.alarms.get(0).getHour());
        String minute = String.valueOf(MainActivity.alarms.get(0).getMinute());
        String title = "Alarm!!!";
        String message = "It is " + hour + ":" + minute;
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(title, message);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
