package com.fixed4fun.alarmclock.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.alertReceivers.AlertReceiver;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmNotifications extends AppCompatActivity {

    public void startNotification(Context context, ArrayList<AlarmData> alarmsList) {
        if(alarmsList!=null) {
            for (int i = 0; i < alarmsList.size(); i++) {
                cancelAlarm(alarmsList.get(i), context);
            }

            for (int i = 0; i < alarmsList.size(); i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmsList.get(i).getHour());
                calendar.set(Calendar.MINUTE, alarmsList.get(i).getMinute());
                calendar.set(Calendar.SECOND, 0);
                checkForActiveDays(calendar, alarmsList.get(i), context);
            }
        }
    }

    private void checkForActiveDays(Calendar calendar, AlarmData alarmData, Context context) {
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        if (alarmData.isOnOrOff() && calendar.after(Calendar.getInstance())) {
            switch (date) {
                //check for monday
                case 2:
                    if (alarmData.isMonday()) {
                        Log.d("123456", "checkForActiveDays: Mon "+alarmData.isMonday());
                        startAlarm(alarmData, context);
                    }
                    break;
                //check for tuesday
                case 3:
                    if (alarmData.isTuesday()) {
                        Log.d("123456", "checkForActiveDays: tue"+alarmData.isTuesday());

                        startAlarm(alarmData, context);
                    }
                    break;
                //check for wednesday
                case 4:
                    if (alarmData.isWednesday()) {
                        Log.d("123456", "checkForActiveDays: wed "+alarmData.isWednesday());

                        startAlarm(alarmData, context);
                    }
                    break;
                //check for thursday
                case 5:
                    if (alarmData.isThursday()) {
                        Log.d("123456", "checkForActiveDays: thur "+alarmData.isThursday());

                        startAlarm(alarmData, context);
                    }
                    break;
                //check for friday
                case 6:
                    if (alarmData.isFriday()) {
                        Log.d("123456", "checkForActiveDays:fri " + alarmData.isFriday());

                        startAlarm(alarmData, context);
                    }
                    break;
                //check for saturday
                case 7:
                    if (alarmData.isSaturday()) {
                        Log.d("123456", "checkForActiveDays: sat "+ alarmData.isSaturday());

                        startAlarm(alarmData, context);
                    }
                    break;
                //check for sunday
                case 1:
                    if (alarmData.isSunday()) {
                        Log.d("123456", "checkForActiveDays: sun" + alarmData.isSunday());

                        startAlarm(alarmData, context);
                    }
                    break;
            }
        }

    }

    private void startAlarm(AlarmData ad, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, ad.getHour());
        calendar.set(Calendar.MINUTE, ad.getMinute());
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) ad.getFlag(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    public void cancelAlarm(AlarmData ad, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) ad.getFlag(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

//    public void midnightAlarms(Context context) {
//        //needed to reset alarms at midnight
//        AlarmManager cancelAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, MidnightReceiver.class);
//        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context, 7734, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        cancelAlarmManager.cancel(cancelPendingIntent);
//        cancelPendingIntent.cancel();
//
//
//        Intent dialogIntent = new Intent(ADObject.getAppContext(), MidnightReceiver.class);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 1);
//        calendar.set(Calendar.SECOND, 0);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 7734, dialogIntent, PendingIntent.FLAG_ONE_SHOT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//
//    }

}
