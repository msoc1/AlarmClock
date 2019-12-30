package com.fixed4fun.alarmclock.alertReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import android.widget.Toast;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.notifications.NotificationHelper;

import java.util.Calendar;

public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Calendar calendar = Calendar.getInstance();
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf((calendar.get(Calendar.MINUTE)) > 9 ? calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE));
        String title = notificationHelper.getString(R.string.alarm_notification);
        String message = notificationHelper.getString(R.string.it_is_notification) + hour + ":" + minute;
        NotificationCompat.Builder nb = notificationHelper
                .getChannel1Notification(title, message);
        notificationHelper.getManager().notify(calendar.get(Calendar.MINUTE), nb.build());
        Toast.makeText(context, notificationHelper.getString(R.string.current_alarm_notification) + hour + ":" + minute, Toast.LENGTH_LONG).show();
    }



}
