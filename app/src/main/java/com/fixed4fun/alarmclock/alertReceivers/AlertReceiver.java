package com.fixed4fun.alarmclock.alertReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;

import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.notifications.NotificationHelper;

import java.util.Calendar;

public class AlertReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("123456", "onReceive: receiver");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        String amPMString ="";
        Calendar calendar = Calendar.getInstance();
        if (!DateFormat.is24HourFormat(ADObject.getAppContext())) {
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            if (mHour >= 12) {
                amPMString += "PM";
            } else {
                amPMString +="AM";
            }
        } else {
            amPMString+="";
        }
        String hourString = "";
        if (!DateFormat.is24HourFormat(ADObject.getAppContext())) {
            if((calendar.get(Calendar.HOUR_OF_DAY)<9 && calendar.get(Calendar.HOUR_OF_DAY)!=0 )||   ( calendar.get(Calendar.HOUR_OF_DAY)<22 && calendar.get(Calendar.HOUR_OF_DAY)>13)){
                hourString += "0";
            }
            if (calendar.get(Calendar.HOUR_OF_DAY) == 0) {
                hourString += "12";
            } else if (calendar.get(Calendar.HOUR_OF_DAY) > 12) {
                hourString += (calendar.get(Calendar.HOUR_OF_DAY)- 12);
            } else {
                hourString += calendar.get(Calendar.HOUR_OF_DAY);
            }
        } else {
            hourString += calendar.get(Calendar.HOUR_OF_DAY);
        }
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf((calendar.get(Calendar.MINUTE)) > 9 ? calendar.get(Calendar.MINUTE) : "0" + calendar.get(Calendar.MINUTE));
        String title = notificationHelper.getString(R.string.alarm_notification);
        String message = notificationHelper.getString(R.string.it_is_notification) + hourString + ":" + minute + " " + amPMString;
        NotificationCompat.Builder nb = notificationHelper
                .getChannel1Notification(title, message);
        notificationHelper.getManager().notify(calendar.get(Calendar.MINUTE), nb.build());
        Toast.makeText(context, notificationHelper.getString(R.string.current_alarm_notification) + hourString + ":" + minute + " " + amPMString, Toast.LENGTH_LONG).show();
    }



}
