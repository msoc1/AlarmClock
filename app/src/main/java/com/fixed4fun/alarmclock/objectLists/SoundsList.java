package com.fixed4fun.alarmclock.objectLists;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.alarmObject.Sounds;

import java.util.ArrayList;

public class SoundsList {

    private static ArrayList<Sounds> soundsArrayList = new ArrayList<>();

    public static ArrayList<Sounds> getAvailableSounds() {
        soundsArrayList.clear();
        soundsArrayList.add(new Sounds("Bells" , R.raw.bells ));
        soundsArrayList.add(new Sounds("Pager Beeps", R.raw.pager_beeps));
        soundsArrayList.add(new Sounds("Siren" ,R.raw.siren ));
        soundsArrayList.add(new Sounds("Siren 2" ,R.raw.siren2 ));
        soundsArrayList.add(new Sounds("Siren 3" ,R.raw.siren3 ));
        soundsArrayList.add(new Sounds("Whistle" ,R.raw.whistle ));


        return soundsArrayList;
    }




}
