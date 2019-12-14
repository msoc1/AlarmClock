package com.fixed4fun.alarmclock.alarmObject;

public class Sounds {

    private String name;
    private int sound;

    public Sounds(String name, int sound) {
        this.name = name;
        this.sound = sound;
    }

    public String getName() {
        return name;
    }

    public int getSound() {
        return sound;
    }
}
