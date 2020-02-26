package com.fixed4fun.alarmclock.alertReceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class BootCompletedReceiver extends BroadcastReceiver {
    AlarmNotifications alarmNotifications;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equalsIgnoreCase(intent.getAction()) || "android.intent.action.QUICKBOOT_POWERON".equalsIgnoreCase(intent.getAction())) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());
            String json2 = sharedPrefs.getString("ALARMS", "");
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<AlarmData>>() {
            }.getType();
            ArrayList<AlarmData> arrayList = gson.fromJson(json2, type);
            alarmNotifications = new AlarmNotifications();
            alarmNotifications.startNotification(ADObject.getAppContext(), arrayList);
            if (arrayList.size() != 0) {
                Intent dialogIntent = new Intent(ADObject.getAppContext(), MidnightReceiver.class);
                AlarmManager alarmManager = (AlarmManager) ADObject.getAppContext().getSystemService(Context.ALARM_SERVICE);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 1);
                calendar.set(Calendar.SECOND, 0);
                calendar.setTimeInMillis(calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ADObject.getAppContext(), 7734, dialogIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            }


        }

    }


}
