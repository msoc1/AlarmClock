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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.objectLists.SoundsList;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class SettingsFragment extends DialogFragment {
    Button closeSettings;
    NumberPickerView napTimes;
    NumberPickerView turnoffTimer;
    TextView selectSong;
    public static final String VIBRATETAG = "vibrate";
    public static final String THEMETAG = "theme";
    public static final String NAPTAG = "nap";
    public static final String ALERTOFFTAG = "secondsoff";
    public static final String NAPOPTIONTAG = "nap_option";


    SwitchCompat vibrateSwitch;
    SwitchCompat themeSwitch;
    SwitchCompat napSwitch;
    int napTime;
    int turnOffAfter;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());

        View view = inflater.inflate(R.layout.settings_layout, container);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);


        vibrateSwitch = view.findViewById(R.id.vibrate_switch);
        themeSwitch = view.findViewById(R.id.theme_switch);
        napTimes = view.findViewById(R.id.nap_length);
        selectSong = view.findViewById(R.id.select_song);
        closeSettings = view.findViewById(R.id.close_settings);
        turnoffTimer = view.findViewById(R.id.turnoff_timer);
        napSwitch = view.findViewById(R.id.nap_option_switch);


        closeSettings.setOnClickListener(v -> {
            Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putBoolean(VIBRATETAG, vibrateSwitch.isChecked());
            editor.putBoolean(THEMETAG, themeSwitch.isChecked());
            editor.putInt(NAPTAG, napTimes.getValue());
            editor.putInt(ALERTOFFTAG, turnoffTimer.getValue());
            editor.putBoolean(NAPOPTIONTAG, napSwitch.isChecked());
            editor.apply();
            getDialog().cancel();
        });

        napTime = sharedPrefs.getInt(NAPTAG, 1);
        turnOffAfter = sharedPrefs.getInt(ALERTOFFTAG, 3);
        vibrateSwitch.setChecked(sharedPrefs.getBoolean(VIBRATETAG, true));
        napSwitch.setChecked(sharedPrefs.getBoolean(NAPOPTIONTAG, true));
        themeSwitch.setChecked(sharedPrefs.getBoolean(THEMETAG, false));

        napTimes.setDisplayedValues(new String[]{"5", "10", "15", "20"});
        napTimes.setMinValue(1);
        napTimes.setMaxValue(4);
        napTimes.setValue(napTime);


        String[] availableSeconds = new String[11];
        for (int i = 0; i < 11; i++) {
            availableSeconds[i] = "" + i;
        }
        turnoffTimer.setDisplayedValues(availableSeconds);
        turnoffTimer.setMinValue(0);
        turnoffTimer.setMaxValue(10);
        turnoffTimer.setValue(turnOffAfter);


        selectSong.setOnClickListener(v -> {
            SelectSongFragment songFragment = new SelectSongFragment();
            assert getFragmentManager() != null;
            songFragment.show(getFragmentManager().beginTransaction(), "settings_fragment");
        });


        int song = sharedPrefs.getInt("SONG_TO_PLAY", 0);

        selectSong.setText(SoundsList.getAvailableSounds().get(song).getName());


        return view;


    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());
        int song = sharedPrefs.getInt("SONG_TO_PLAY", 0);
        selectSong.setText(SoundsList.getAvailableSounds().get(song).getName());

    }
}

