package com.fixed4fun.alarmclock.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alarmObject.Sounds;
import com.fixed4fun.alarmclock.objectLists.SoundsList;

import java.util.ArrayList;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class SelectSongFragment extends DialogFragment {
    NumberPickerView songSelection;
    Button save;
    ImageView playOrPause;
    MediaPlayer mMediaPlayer;



    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_song_layout, container);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        songSelection = view.findViewById(R.id.nap_length);
        save = view.findViewById(R.id.save_songs);
        playOrPause = view.findViewById(R.id.play_or_pause);

        playOrPause.setImageResource(R.drawable.play);

        mMediaPlayer = new MediaPlayer();
        ArrayList<Sounds> listOfAvailableSongs = SoundsList.getAvailableSounds();

        String[] availableSongs = new String[listOfAvailableSongs.size()];
        for(int i =0 ; i<listOfAvailableSongs.size() ; i++){
            availableSongs[i] = listOfAvailableSongs.get(i).getName();
        }
        songSelection.setDisplayedValues(availableSongs);
        songSelection.setMinValue(0);
        songSelection.setMaxValue(listOfAvailableSongs.size()-1);
        songSelection.setValue(sharedPrefs.getInt("SONG_TO_PLAY", 0));


        save.setOnClickListener(v->{
            Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt("SONG_TO_PLAY", songSelection.getValue());
            editor.apply();
            TextView songTitleTextViewOnSettingsFragment = MainActivity.settingsFragment.getDialog().getWindow().findViewById(R.id.select_song);
            songTitleTextViewOnSettingsFragment.setText(listOfAvailableSongs.get(songSelection.getValue()).getName());
            getDialog().dismiss();
        });

        playOrPause.setOnClickListener( v -> {
            if(!mMediaPlayer.isPlaying()) {
                playOrPause.setImageResource(R.drawable.pause);
                mMediaPlayer = MediaPlayer.create(getContext(), listOfAvailableSongs.get(songSelection.getValue()).getSound());
                mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
                mMediaPlayer.setLooping(false);
                mMediaPlayer.start();
            } else {
                mMediaPlayer.stop();
                playOrPause.setImageResource(R.drawable.play);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
