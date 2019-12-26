package com.fixed4fun.alarmclock.alertReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MidnightReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());
        String json2 = sharedPrefs.getString("ALARMS", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<AlarmData>>() {
        }.getType();
        ArrayList<AlarmData> arrayList = gson.fromJson(json2, type);
        AlarmNotifications alarmNotifications = new AlarmNotifications();
        alarmNotifications.startNotification(ADObject.getAppContext(), arrayList);
    }
}
