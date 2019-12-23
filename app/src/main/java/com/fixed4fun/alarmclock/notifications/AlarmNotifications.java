package com.fixed4fun.alarmclock.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fixed4fun.alarmclock.activities.AlarmGoingOff;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.alertReceivers.AlertReceiver;

import java.util.Calendar;

import static com.fixed4fun.alarmclock.activities.MainActivity.alarms;

public class AlarmNotifications extends AppCompatActivity {

    public void startNotification(Context context) {

        for (int i = 0; i < alarms.size(); i++) {
            cancelAlarm(alarms.get(i), context);
        }

        for (int i = 0; i < alarms.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarms.get(i).getHour());
            calendar.set(Calendar.MINUTE, alarms.get(i).getMinute());
            calendar.set(Calendar.SECOND, 0);
            checkForActiveDays(calendar, alarms.get(i), context);
        }
    }

    private void checkForActiveDays(Calendar calendar, AlarmData alarmData, Context context) {
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        if (alarmData.isOnOrOff() && calendar.after(Calendar.getInstance())) {
            switch (date) {
                //check for monday
                case 2:
                    if (alarmData.isMonday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
                //check for tuesday
                case 3:
                    if (alarmData.isTuesday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
                //check for wednesday
                case 4:
                    if (alarmData.isWednesday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
                //check for thursday
                case 5:
                    if (alarmData.isThursday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
                //check for friday
                case 6:
                    if (alarmData.isFriday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
                //check for saturday
                case 7:
                    if (alarmData.isSaturday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
                //check for sunday
                case 1:
                    if (alarmData.isSunday()) {
                        startAlarm(calendar, alarmData, context);
                    }
                    break;
            }
        }

    }

    private void startAlarm(Calendar c, AlarmData ad, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) ad.getFlag(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.d("123456", "ad.flag start: " + ad.getFlag() + " index " + alarms.indexOf(ad));
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }


    private void cancelAlarm(AlarmData ad, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) ad.getFlag(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Log.d("123456", "ad.flag cancel: " + ad.getFlag() + " index " + alarms.indexOf(ad));
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        ad.setNotificationIntent(null);
    }

    private void resetAlarms() {
        for (AlarmData currentAlarmData : alarms) {
            // cancelAlarm(currentAlarmData);
        }
        // startNotification();
    }


}
