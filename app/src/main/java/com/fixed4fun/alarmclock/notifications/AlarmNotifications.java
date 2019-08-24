package com.fixed4fun.alarmclock.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.alertReceivers.AlertReceiver;

import java.util.Calendar;

import static com.fixed4fun.alarmclock.activities.MainActivity.alarms;

public class AlarmNotifications extends AppCompatActivity {

    public void startNotification(Context context) {
        for (AlarmData currentAlarmData : alarms) {
            if (currentAlarmData.getNotificationIntent() != null) {
                cancelAlarm(currentAlarmData, context);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, currentAlarmData.getHour());
            calendar.set(Calendar.MINUTE, currentAlarmData.getMinute());
            calendar.set(Calendar.SECOND, 0);
            checkForActiveDays(calendar, currentAlarmData, context);
        }
    }

    private void checkForActiveDays(Calendar calendar, AlarmData alarmData, Context context) {
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        switch (date) {
            //check for monday
            case 2:
                if (alarmData.isMonday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isMonday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
            //check for tuesday
            case 3:
                if (alarmData.isTuesday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isTuesday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
            //check for wednesday
            case 4:
                if (alarmData.isWednesday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isWednesday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
            //check for thursday
            case 5:
                if (alarmData.isThursday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isThursday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
            //check for friday
            case 6:
                if (alarmData.isFriday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isFriday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
            //check for saturday
            case 7:
                if (alarmData.isSaturday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isSaturday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
            //check for sunday
            case 1:
                if (alarmData.isSunday()) {
                    startAlarm(calendar, alarmData, context);
                }
                if (!alarmData.isSunday() && alarmData.getNotificationIntent() != null) {
                    cancelAlarm(alarmData, context);
                }
                break;
        }

    }

    private void startAlarm(Calendar c, AlarmData ad, Context context) {
        Intent intent = ad.getNotificationIntent();
        if (c.after(Calendar.getInstance()) && ad.isOnOrOff()) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (intent == null) {
                intent = new Intent(context, AlertReceiver.class);
                ad.setNotificationIntent(intent);
            } else {
                intent = ad.getNotificationIntent();
            }
            intent.setAction(Long.toString(System.currentTimeMillis()));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarms.indexOf(ad), intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }


    private void cancelAlarm(AlarmData ad, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = ad.getNotificationIntent();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarms.indexOf(ad), intent, 0);
        ad.setNotificationIntent(null);
        alarmManager.cancel(pendingIntent);
    }

    private void resetAlarms() {
        for (AlarmData currentAlarmData : alarms) {
            // cancelAlarm(currentAlarmData);
        }
        // startNotification();
    }


}
