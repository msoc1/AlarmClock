package com.fixed4fun.alarmclock.alarmObject;

public class Sounds {

    private String name;
    private int sound;
    private String descr;

    public Sounds(String name, int sound, String descr) {
        this.name = name;
        this.sound = sound;
        this.descr = descr;
    }

    public String getName() {
        return name;
    }

    public int getSound() {
        return sound;
    }

    public String getDescr() {
        return descr;
    }
}
