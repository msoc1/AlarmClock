package com.fixed4fun.alarmclock.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alarmObject.Sounds;
import com.fixed4fun.alarmclock.objectLists.SoundsList;

import java.util.ArrayList;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class SelectSongFragment extends DialogFragment {
    NumberPickerView songSelection;
    Button save;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_song_layout, container);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        songSelection = view.findViewById(R.id.nap_length);
        save = view.findViewById(R.id.save_songs);

        ArrayList<Sounds> as = SoundsList.getAvailableSounds();

        String[] asd = new String[as.size()];
        for(int i =0 ; i<as.size() ; i++){
            asd[i] = as.get(i).getName();
        }
        songSelection.setDisplayedValues(asd);
        songSelection.setMinValue(0);
        songSelection.setMaxValue(as.size()-1);


        save.setOnClickListener(v->{
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt("SONG_TO_PLAY", songSelection.getValue());
            editor.apply();
            getDialog().dismiss();
        });


        return view;
    }


}
