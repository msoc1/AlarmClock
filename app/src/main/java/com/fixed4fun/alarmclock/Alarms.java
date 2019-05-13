package com.fixed4fun.alarmclock;

import java.util.ArrayList;

public class Alarms {

    private static ArrayList<AlarmData> alarmDataArrayList = new ArrayList<AlarmData>();

    public ArrayList<AlarmData> getAlarms(){
//        alarmDataArrayList.add(new AlarmData("15:34", "Everyday", "Alarm will go off in 3 hours and 45 minutes", true));
//        alarmDataArrayList.add(new AlarmData("1:14", "Everyday", "Alarm will go off in 45 minutes", false));
//        alarmDataArrayList.add(new AlarmData("11:30", "Mon | Fri| Sat | Sun", "Alarm will go off in 13 hours", true));
//        alarmDataArrayList.add(new AlarmData("14:57", "Sat | Sun", "Alarm will go off in 3 hours and 45 minutes", false));
        return alarmDataArrayList;
    }

    public static void addAlarm(String time, String whentoGooff, String tillAlarm, boolean onOff){
        alarmDataArrayList.add(new AlarmData(time,whentoGooff,tillAlarm,onOff));
    }

    public static void deleteAlarm(int position){
        alarmDataArrayList.remove(position);
    }

}
