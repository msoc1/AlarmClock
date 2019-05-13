package com.fixed4fun.alarmclock;



public class AlarmData {

    String timeOfAlarm;
    String whenToGoOff;
    String howLongTillAlarm;
    boolean isOnOrOff;

    public AlarmData(String timeOfAlarm, String whenToGoOff, String howLongTillAlarm, boolean isOnOrOff) {
        this.timeOfAlarm = timeOfAlarm;
        this.whenToGoOff = whenToGoOff;
        this.howLongTillAlarm = howLongTillAlarm;
        this.isOnOrOff = isOnOrOff;
    }

    public String getTimeOfAlarm() {
        return timeOfAlarm;
    }

    public void setTimeOfAlarm(String timeOfAlarm) {
        this.timeOfAlarm = timeOfAlarm;
    }

    public String getWhenToGoOff() {
        return whenToGoOff;
    }

    public void setWhenToGoOff(String whenToGoOff) {
        this.whenToGoOff = whenToGoOff;
    }

    public String getHowLongTillAlarm() {
        return howLongTillAlarm;
    }

    public void setHowLongTillAlarm(String howLongTillAlarm) {
        this.howLongTillAlarm = howLongTillAlarm;
    }

    public boolean isOnOrOff() {
        return isOnOrOff;
    }

    public void setOnOrOff(boolean onOrOff) {
        isOnOrOff = onOrOff;
    }
}
