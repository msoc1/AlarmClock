package com.fixed4fun.alarmclock.objectLists;

import com.fixed4fun.alarmclock.alarmObject.AlarmData;

import java.util.ArrayList;

public class AlarmList {
    //Test class to add fictional easy to edit alarms


    public static ArrayList<AlarmData> alarmDataArrayList = new ArrayList<>();


    public static ArrayList<AlarmData> getAlarms() {
        return alarmDataArrayList;
    }

    public static void addAlarm(int hour, int minute, boolean monday_friday, boolean saturday_sunday,
                                boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
                                boolean saturday, boolean sunday, boolean onOrOff, boolean selected , int flag) {

        alarmDataArrayList.add(new AlarmData(hour, minute, monday_friday, saturday_sunday,
                monday, tuesday, wednesday, thursday, friday,
                saturday, sunday, onOrOff, selected, flag));
    }

    public static void addAlarm(AlarmData a) {
        alarmDataArrayList.add(a);
    }

    public static void deleteAlarm(int position) {
        alarmDataArrayList.remove(position);
    }



}
