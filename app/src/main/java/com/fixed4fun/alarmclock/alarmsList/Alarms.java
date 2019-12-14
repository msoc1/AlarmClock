package com.fixed4fun.alarmclock.alarmsList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Alarms {
    //Test class to add fictional easy to edit alarms


    public static ArrayList<AlarmData> alarmDataArrayList = new ArrayList<>();

    public static void addFirstAlarm() {
//        alarmDataArrayList.add(new AlarmData(20, 29, true, true
//                , true, true, true, true, true
//                , true, true
//                , false, 2, true, false, null));
//        alarmDataArrayList.add(new AlarmData(20, 7, true, true
//                , true, true, true, true, true
//                , true, true
//                , false, 2, true, false, null));
//        alarmDataArrayList.add(new AlarmData(20, 8, true, true
//                , true, true, true, true, true
//                , true, true
//                , false, 2, true, false, null));
//        alarmDataArrayList.add(new AlarmData(20, 9, true, true
//                , true, true, true, true, true
//                , true, true
//                , false, 2, true, false, null));
//        alarmDataArrayList.add(new AlarmData(4, 1, true, true
//                , true, true, true, true, true
//                , true, true
//                , false, 2, false, false));
//        alarmDataArrayList.add(new AlarmData(6, 6, true, true
//                , true, true, true, true, true
//                , true, true
//                , false, 2, false, false));
    }

    public static ArrayList<AlarmData> getAlarms() {
        return alarmDataArrayList;
    }

    public static void addAlarm(int hour, int minute, boolean monday_friday, boolean saturday_sunday,
                                boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
                                boolean saturday, boolean sunday, boolean vibrate, int sound, boolean onOrOff, boolean selected, Intent notificationIntent) {

        alarmDataArrayList.add(new AlarmData(hour, minute, monday_friday, saturday_sunday,
                monday, tuesday, wednesday, thursday, friday,
                saturday, sunday, vibrate, sound, onOrOff, selected, null));
    }

    public static void addAlarm(AlarmData a) {
        alarmDataArrayList.add(a);
    }

    public static void deleteAlarm(int position) {
        alarmDataArrayList.remove(position);
    }



}
