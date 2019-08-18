package com.fixed4fun.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Calendar calendar = Calendar.getInstance();
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        String title = "Alarm!!!";
        String message = "It is " + hour + ":" + minute;
        NotificationCompat.Builder nb = notificationHelper
                .getChannel1Notification(title, message);
        // nb.setSmallIcon(R.drawable.ch_sat_sun);
        notificationHelper.getManager().notify(1, nb.build());
        Toast.makeText(context, "current alarm \n" + hour + ":" + minute, Toast.LENGTH_LONG).show();
    }
}
