package com.fixed4fun.alarmclock.activities;

import android.graphics.drawable.TransitionDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fixed4fun.alarmclock.R;

import java.time.LocalDate;
import java.util.Calendar;

public class AlarmGoingOff extends AppCompatActivity {

    TextView hours;
    TextView minutes;
    Button turnOff;
    MediaPlayer mMediaPlayer;
    Handler mHandler = new Handler();
    Runnable timeRunnable;
    TransitionDrawable transitionDrawable;
    TextView dayOfTheWeek;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_going_off);

        Calendar calendar = Calendar.getInstance();

        hours = findViewById(R.id.hours_alarm);
        minutes = findViewById(R.id.minutes_alarm);
        turnOff = findViewById(R.id.turnoff_alarm);
        dayOfTheWeek = findViewById(R.id.day_of_the_week);

        hours.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        int timeInMinutes = calendar.get(Calendar.MINUTE);
        minutes.setText(((timeInMinutes> 9) ? timeInMinutes : "0" + timeInMinutes).toString());
        dayOfTheWeek.setText(LocalDate.now().getDayOfWeek().name());

        turnOff.setOnTouchListener(buttonOnTouchListener);

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this,R.raw.pager_beeps);
        mMediaPlayer.setAudioAttributes( new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private View.OnTouchListener buttonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            timeRunnable = () -> finish();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    transitionDrawable = (TransitionDrawable) turnOff.getBackground();
                    transitionDrawable.startTransition(3000);
                    mHandler.postDelayed(timeRunnable, 3000);
                    break;
                case MotionEvent.ACTION_UP:
                    transitionDrawable.resetTransition();
                    mHandler.removeCallbacksAndMessages(null);

                    break;

            }
            return true;
        }
    };

}
