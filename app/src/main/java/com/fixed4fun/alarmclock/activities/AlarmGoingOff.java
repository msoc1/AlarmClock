package com.fixed4fun.alarmclock.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.TransitionDrawable;
import android.icu.text.SimpleDateFormat;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;
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
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.alertReceivers.AlertReceiver;
import com.fixed4fun.alarmclock.notifications.NotificationHelper;
import com.fixed4fun.alarmclock.objectLists.SoundsList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmGoingOff extends AppCompatActivity {

    TextView hours;
    TextView minutes;
    TextView amPM;
    Button turnOff;
    Button napTime;
    Handler mHandler = new Handler();
    Runnable timeRunnable;
    TransitionDrawable transitionDrawable;
    TextView dayOfTheWeek;
    public static final String VIBRATETAG = "vibrate";
    public static final String NAPTAG = "nap";
    Vibrator vibrator;
    int turnOffAfter;
    Handler turnOffHandler = new Handler();
    public ArrayList<AlarmData> alarms = new ArrayList<>();
    static int alarmIndex = -1;
    public static boolean ringAlarm;
    static MediaPlayer mediaPlayer = new MediaPlayer();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_going_off);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ADObject.getAppContext());
        boolean vibrate = sharedPrefs.getBoolean(VIBRATETAG, true);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);


        String json2 = sharedPrefs.getString("ALARMS", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<AlarmData>>() {
        }.getType();
        ArrayList<AlarmData> arrayList = gson.fromJson(json2, type);
        alarms.clear();
        if (arrayList != null) {
            alarms.addAll(arrayList);
        }

        Calendar c = Calendar.getInstance();
        int napTimeInMinutes2 = sharedPrefs.getInt(NAPTAG, 1);
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if (minute + napTimeInMinutes2 * 5 > 60) {
            hour++;
            minute = (minute + napTimeInMinutes2 * 5) - 60;
        }
        for (AlarmData ad : alarms) {
            if (!ad.isCalled() && ad.getHour() == c.get(Calendar.HOUR_OF_DAY) && (ad.getMinute() == c.get(Calendar.MINUTE) || (ad.getMinute() + napTimeInMinutes2 * 5) == c.get(Calendar.MINUTE)) && ad.isOnOrOff()) {
                ringAlarm = true;
                ad.setCalled(true);
                alarmIndex = alarms.indexOf(ad);
                break;
            }
            if (!ad.isCalled() && ad.getHour() == hour && ad.getMinute() == minute && ad.isOnOrOff()) {
                ringAlarm = true;
                ad.setCalled(true);
                alarmIndex = alarms.indexOf(ad);
                break;
            }
        }

        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson2 = new Gson();
        editor.remove("ALARMS");
        String json = gson2.toJson(alarms);
        editor.putString("ALARMS", json);
        editor.apply();

        if (ringAlarm) {
            hours = findViewById(R.id.hours_alarm);
            minutes = findViewById(R.id.minutes_alarm);
            turnOff = findViewById(R.id.turnoff_alarm);
            dayOfTheWeek = findViewById(R.id.day_of_the_week);
            napTime = findViewById(R.id.nap_time);
            amPM = findViewById(R.id.am_pm_text_view);
            Calendar calendar = Calendar.getInstance();

            boolean napPossible = sharedPrefs.getBoolean("nap_option", true);
            if (isNap() || !napPossible) {
                napTime.setVisibility(View.INVISIBLE);
            }

            if (!DateFormat.is24HourFormat(ADObject.getAppContext())) {
                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                if (mHour >= 12) {
                    amPM.setText("PM");
                } else {
                    amPM.setText("AM");
                }
            } else {
                amPM.setText("");
            }

            String hourString = "";
            if (!DateFormat.is24HourFormat(ADObject.getAppContext())) {
                if (calendar.get(Calendar.HOUR_OF_DAY) > 12) {
                    hourString += calendar.get(Calendar.HOUR_OF_DAY) - 12;
                } else {
                    hourString += calendar.get(Calendar.HOUR_OF_DAY);
                }
            } else {
                hourString += calendar.get(Calendar.HOUR_OF_DAY);
            }

            hours.setText(hourString);
            int timeInMinutes = calendar.get(Calendar.MINUTE);
            minutes.setText(((timeInMinutes > 9) ? timeInMinutes : "0" + timeInMinutes).toString());
            Date date = calendar.getTime();
            //"EEEE" is for day of the week in it's full name
            dayOfTheWeek.setText(new SimpleDateFormat("EEEE", Locale.getDefault()).format(date.getTime()));

            turnOff.setOnTouchListener(buttonOnTouchListener);

            napTime.setOnClickListener(v -> {
                int napTimeInMinutes = sharedPrefs.getInt(NAPTAG, 1);
                long time = calendar.getTimeInMillis() + (napTimeInMinutes * 60000 * 5);
                AlarmManager alarmManager = (AlarmManager) ADObject.getAppContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(ADObject.getAppContext(), AlertReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ADObject.getAppContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                if (alarmIndex >= 0) {
                    alarms.get(alarmIndex).setCalled(false);
                    Gson gson3 = new Gson();
                    editor.remove("ALARMS");
                    String json3 = gson3.toJson(alarms);
                    editor.putString("ALARMS", json3);
                    editor.apply();
                }
                finish();
                Toast.makeText(ADObject.getAppContext(),
                        "" + getResources().getText(R.string.nap_time_l) + napTimeInMinutes * 5 + " " + getResources().getText(R.string.minutes),
                        Toast.LENGTH_LONG).show();

            });

            int song = sharedPrefs.getInt("SONG_TO_PLAY", 1);
            turnOffAfter = sharedPrefs.getInt("secondsoff", 3) * 1000;
            playAlarmSound(SoundsList.getAvailableSounds().get(song).getDescr());
            if (vibrate) {
                long[] pattern = {0, 1000, 300};
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    vibrator.vibrate(pattern, 0);
                }
            }
            //start handler to finish activity after minute has passed
            turnOffHandler.postDelayed(this::finish, 60000);


        } else {
            NotificationHelper n = new NotificationHelper();
            n.getManager().cancelAll();
            finish();
        }
    }

    static public void playAlarmSound(String songDescr) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mediaPlayer.setOnCompletionListener(mp -> {
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    });
                    mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
                    int resourdeID = ADObject.getAppContext().getResources().getIdentifier(songDescr, "raw", ADObject.getAppContext().getPackageName());
                    AssetFileDescriptor afd = ADObject.getAppContext().getResources().openRawResourceFd(resourdeID);

                    if (afd == null) return false;
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mediaPlayer.setVolume(1.0f, 1.0f);
                    mediaPlayer.prepare();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

        }.execute();
    }


    public boolean isNap() {
        Calendar c = Calendar.getInstance();
        for (AlarmData ad : alarms) {
            if (ad.getHour() == c.get(Calendar.HOUR_OF_DAY) && ad.getMinute() == c.get(Calendar.MINUTE)) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
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
                    //dissapear notification on turning off alarm
                    NotificationHelper n = new NotificationHelper();
                    n.getManager().cancelAll();
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
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);

        super.onAttachedToWindow();
    }
}
