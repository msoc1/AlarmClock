package com.fixed4fun.alarmclock;

import java.util.ArrayList;

public class Alarms {
    //Test class to add fictional easy to edit alarms


    private static ArrayList<AlarmData> alarmDataArrayList = new ArrayList<AlarmData>();

    public static void addFirstAlarm() {
        alarmDataArrayList.add(new AlarmData(23, 47, true, false
                , true, true, true, true, true
                , true, true
                , false, 2, false, false));
        alarmDataArrayList.add(new AlarmData(13, 17, true, false
                , true, true, true, true, true
                , true, true
                , false, 2, true, false));
        alarmDataArrayList.add(new AlarmData(2, 24, true, false
                , true, true, true, true, true
                , true, true
                , false, 2, false, false));
        alarmDataArrayList.add(new AlarmData(23, 47, true, false
                , true, true, true, true, true
                , true, true
                , false, 2, false, false));
        alarmDataArrayList.add(new AlarmData(13, 17, true, false
                , true, true, true, true, true
                , true, true
                , false, 2, true, false));
        alarmDataArrayList.add(new AlarmData(2, 24, true, false
                , true, true, true, true, true
                , true, true
                , false, 2, true, false));
    }

    public static ArrayList<AlarmData> getAlarms() {
        return alarmDataArrayList;
    }

    public static void addAlarm(int hour, int minute, boolean monday_friday, boolean saturday_sunday,
                                boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
                                boolean saturday, boolean sunday, boolean vibrate, int sound, boolean onOrOff, boolean selected) {

        alarmDataArrayList.add(new AlarmData(hour, minute, monday_friday, saturday_sunday,
                monday, tuesday, wednesday, thursday, friday,
                saturday, sunday, vibrate, sound, onOrOff, selected));
    }

    public static void addAlarm(AlarmData a) {
        alarmDataArrayList.add(a);
    }

    public static void deleteAlarm(int position) {
        alarmDataArrayList.remove(position);
    }


}
