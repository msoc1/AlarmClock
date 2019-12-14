package com.fixed4fun.alarmclock.objectLists;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.alarmObject.Sounds;

import java.util.ArrayList;

public class SoundsList {

    private static ArrayList<Sounds> soundsArrayList = new ArrayList<>();

    public static ArrayList<Sounds> getAvailableSounds() {
        soundsArrayList.clear();
        soundsArrayList.add(new Sounds("Pager Beeps", R.raw.pager_beeps));

        return soundsArrayList;
    }




}
