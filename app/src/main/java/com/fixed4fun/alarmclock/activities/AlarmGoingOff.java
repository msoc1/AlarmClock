package com.fixed4fun.alarmclock.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alertReceivers.AlertReceiver;
import com.fixed4fun.alarmclock.objectLists.SoundsList;

import java.time.LocalDate;
import java.util.Calendar;

public class AlarmGoingOff extends AppCompatActivity {

    TextView hours;
    TextView minutes;
    Button turnOff;
    Button napTime;
    MediaPlayer mMediaPlayer;
    Handler mHandler = new Handler();
    Runnable timeRunnable;
    TransitionDrawable transitionDrawable;
    TextView dayOfTheWeek;
    public static final String VIBRATETAG = "vibrate";
    public static final String NAPTAG = "nap";
    Vibrator vibrator;
    int turnOffAfter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_going_off);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());
        boolean vibrate = sharedPrefs.getBoolean(VIBRATETAG, true);

        Calendar calendar = Calendar.getInstance();

        hours = findViewById(R.id.hours_alarm);
        minutes = findViewById(R.id.minutes_alarm);
        turnOff = findViewById(R.id.turnoff_alarm);
        dayOfTheWeek = findViewById(R.id.day_of_the_week);
        napTime = findViewById(R.id.nap_time);

        hours.setText(String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
        int timeInMinutes = calendar.get(Calendar.MINUTE);
        minutes.setText(((timeInMinutes > 9) ? timeInMinutes : "0" + timeInMinutes).toString());
        dayOfTheWeek.setText(LocalDate.now().getDayOfWeek().name());

        turnOff.setOnTouchListener(buttonOnTouchListener);

        napTime.setOnClickListener(v -> {
            int napTimeInMinutes = sharedPrefs.getInt(NAPTAG, 1);
            long time = calendar.getTimeInMillis() + (napTimeInMinutes * 60000 * 5);
            Log.d("123456", "onCreate: " + (napTimeInMinutes * 60000 * 5));
            AlarmManager alarmManager = (AlarmManager) ADObject.getAppContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(ADObject.getAppContext(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ADObject.getAppContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            finish();
            Toast.makeText(ADObject.getAppContext(), "Nap Time: " + napTimeInMinutes*5 + "minutes", Toast.LENGTH_LONG).show();
        });


        mMediaPlayer = new MediaPlayer();

        int song = sharedPrefs.getInt("SONG_TO_PLAY", 0);
        turnOffAfter = sharedPrefs.getInt("secondsoff", 0) * 1000;

        mMediaPlayer = MediaPlayer.create(this, SoundsList.getAvailableSounds().get(song).getSound());
        mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
        if (vibrate) {
            long[] pattern = {0, 1000, 300};
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(pattern, 0);
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        vibrator.cancel();
    }

    private View.OnTouchListener buttonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            timeRunnable = () -> finish();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    transitionDrawable = (TransitionDrawable) turnOff.getBackground();
                    transitionDrawable.startTransition(turnOffAfter);
                    mHandler.postDelayed(timeRunnable, turnOffAfter);
                    break;
                case MotionEvent.ACTION_UP:
                    transitionDrawable.resetTransition();
                    mHandler.removeCallbacksAndMessages(null);

                    break;

            }
            return true;
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onAttachedToWindow();
    }
}
