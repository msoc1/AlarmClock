package com.fixed4fun.alarmclock.timePickerFragments;


import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.alarmsList.Alarms;

public class NewTimePicker extends DialogFragment implements View.OnClickListener {

    TimePicker timePicker;

    AlertDialog alertDialog;

    AlarmData alarmData;

    Button buttoSetAlatm;
    Button cancelAlarm;
    Button changeSound;

    CheckBox monFriCheckBox;
    CheckBox satSunCheckBox;
    CheckBox mondayCheckBox;
    CheckBox tuesdayCheckBox;
    CheckBox wednesdayCheckBox;
    CheckBox thursdayCheckBox;
    CheckBox fridayCheckBox;
    CheckBox saturdayCheckBox;
    CheckBox sundayCheckBox;
    CheckBox vibrateCheckBox;

    CustomAdapter adapter;

    TextView sound;

    public static int getHour(TimePicker timePicker) {
        //support older versions of API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getHour();
        } else {
            return timePicker.getCurrentHour();
        }
    }

    public static int getMinute(TimePicker timePicker) {
        //support older versions of API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getMinute();
        } else {
            return timePicker.getCurrentMinute();
        }
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.custom_timepicker, container);
        monFriCheckBox = view.findViewById(R.id.mon_fri_checkBox);
        satSunCheckBox = view.findViewById(R.id.sat_sun_checkBox);
        mondayCheckBox = view.findViewById(R.id.monday_check_box);
        tuesdayCheckBox = view.findViewById(R.id.tuesday_check_box);
        wednesdayCheckBox = view.findViewById(R.id.wednesday_check_box);
        thursdayCheckBox = view.findViewById(R.id.thursday_check_box);
        fridayCheckBox = view.findViewById(R.id.friday_check_box);
        saturdayCheckBox = view.findViewById(R.id.saturday_check_box);
        sundayCheckBox = view.findViewById(R.id.sunday_check_box);
        vibrateCheckBox = view.findViewById(R.id.vibration_check_box);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        sound = view.findViewById(R.id.current_sound_textView);
        sound.setText(getResources().getResourceEntryName(R.raw.pager_beeps));
        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));
        builder.setView(view);
        buttoSetAlatm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);
        setUpButtons();
        alertDialog = builder.create();

        return view;
    }

    public void confirmDays(View v) {
        switch (v.getId()) {
            case R.id.mon_fri_checkBox:
                if (monFriCheckBox.isChecked()) {
                    changeMondayToFriday(true);
                } else {
                    changeMondayToFriday(false);
                }
                break;
            case R.id.sat_sun_checkBox:
                if (satSunCheckBox.isChecked()) {
                    changeSaturdayAndSunday(true);
                } else {
                    changeSaturdayAndSunday(false);
                }
                break;
            case R.id.saturday_check_box:
                if (saturdayCheckBox.isChecked() && sundayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(true);
                } else if (!saturdayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(false);
                }
                break;
            case R.id.sunday_check_box:
                if (saturdayCheckBox.isChecked() && sundayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(true);
                } else if (!sundayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(false);
                }
                break;
            case R.id.monday_check_box:
                if (isWorkday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
                break;
            case R.id.tuesday_check_box:
                if (isWorkday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
                break;
            case R.id.wednesday_check_box:
                if (isWorkday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
                break;
            case R.id.thursday_check_box:
                if (isWorkday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
                break;
            case R.id.friday_check_box:
                if (isWorkday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
                break;
        }
    }


    public void changeMondayToFriday(boolean bool) {
        mondayCheckBox.setChecked(bool);
        tuesdayCheckBox.setChecked(bool);
        wednesdayCheckBox.setChecked(bool);
        thursdayCheckBox.setChecked(bool);
        fridayCheckBox.setChecked(bool);
    }

    public void changeSaturdayAndSunday(boolean bool) {
        sundayCheckBox.setChecked(bool);
        saturdayCheckBox.setChecked(bool);
    }

    @Override
    public void onClick(View v) {
        confirmDays(v);
    }

    public boolean isWorkday() {
        return mondayCheckBox.isChecked() && tuesdayCheckBox.isChecked() && wednesdayCheckBox.isChecked() && thursdayCheckBox.isChecked() && fridayCheckBox.isChecked();
    }

    public void setUpButtons() {
        buttoSetAlatm.setOnClickListener(v -> addAnAlarm());
        cancelAlarm.setOnClickListener(v -> closeTimePicker());
        satSunCheckBox.setOnClickListener(this);
        monFriCheckBox.setOnClickListener(this);
        saturdayCheckBox.setOnClickListener(this);
        sundayCheckBox.setOnClickListener(this);
        thursdayCheckBox.setOnClickListener(this);
        fridayCheckBox.setOnClickListener(this);
        mondayCheckBox.setOnClickListener(this);
        tuesdayCheckBox.setOnClickListener(this);
        wednesdayCheckBox.setOnClickListener(this);

    }

    public void addAnAlarm() {
        alarmData = new AlarmData(getHour(timePicker), getMinute(timePicker), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
                , tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
                , vibrateCheckBox.isChecked(), 3, true, false, null);

        if (!alarmData.isMonday() && !alarmData.isTuesday() && !alarmData.isWednesday() && !alarmData.isThursday() && !alarmData.isFriday() && !alarmData.isSaturday() && !alarmData.isSunday()) {
            alarmData.setMonday_friday(true);
            alarmData.setSaturday_sunday(true);
            alarmData.setMonday(true);
            alarmData.setTuesday(true);
            alarmData.setWednesday(true);
            alarmData.setThursday(true);
            alarmData.setFriday(true);
            alarmData.setSaturday(true);
            alarmData.setSunday(true);
            Alarms.addAlarm(alarmData);

        } else {
            Alarms.addAlarm(getHour(timePicker), getMinute(timePicker), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
                    , tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
                    , vibrateCheckBox.isChecked(), 3, true, false, null);
        }


        String toastMessage = "New Alarm change to "
                + timePicker.getCurrentHour()
                + ":"
                + ((timePicker.getCurrentMinute() > 9) ? timePicker.getCurrentMinute() : "0" + timePicker.getCurrentMinute())
                + " on: "
                + CustomAdapter.daysWhenToRing(alarmData);
        Toast toast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();
        adapter = ((MainActivity) getActivity()).getCustomAdapter();
        MainActivity.sortList(MainActivity.alarms);
        adapter.notifyDataSetChanged();
        closeTimePicker();
    }


    public void closeTimePicker() {
        alertDialog.dismiss();
        getDialog().cancel();
    }


}
