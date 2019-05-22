package com.fixed4fun.alarmclock;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewTimePicker extends DialogFragment implements View.OnClickListener {

    static TimePicker timePicker;
    Button buttoSetAlatm;
    Button cancelAlarm;
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
    Button changeSound;
    AlarmData alarmData;


    AlertDialog alertDialog;

    public static int getHour() {
        //support older versions of API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getHour();
        } else {
            return timePicker.getCurrentHour();
        }
    }

    public static int getMinute() {
        //support older versions of API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getMinute();
        } else {
            return timePicker.getCurrentMinute();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_timepicker, null);
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

        timePicker = view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));
        builder.setView(view);
        buttoSetAlatm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);
        setUpButtons();
        alertDialog = builder.create();
        return alertDialog;
    }

    public void confirmDays(View v) {
        switch (v.getId()) {
            case R.id.mon_fri_checkBox:
                if (monFriCheckBox.isChecked()) {
                    mondayCheckBox.setChecked(true);
                    tuesdayCheckBox.setChecked(true);
                    wednesdayCheckBox.setChecked(true);
                    thursdayCheckBox.setChecked(true);
                    fridayCheckBox.setChecked(true);
                } else {
                    mondayCheckBox.setChecked(false);
                    tuesdayCheckBox.setChecked(false);
                    wednesdayCheckBox.setChecked(false);
                    thursdayCheckBox.setChecked(false);
                    fridayCheckBox.setChecked(false);
                }
            case R.id.sat_sun_checkBox:
                if (satSunCheckBox.isChecked()) {
                    sundayCheckBox.setChecked(true);
                    saturdayCheckBox.setChecked(true);
                } else {
                    sundayCheckBox.setChecked(false);
                    saturdayCheckBox.setChecked(false);
                }
            case R.id.saturday_check_box:
                if (saturdayCheckBox.isChecked() && sundayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(true);
                } else if (!saturdayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(false);
                }
            case R.id.sunday_check_box:
                if (saturdayCheckBox.isChecked() && sundayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(true);
                } else if (!sundayCheckBox.isChecked()) {
                    satSunCheckBox.setChecked(false);
                }
            case R.id.monday_check_box:
                if (isMondayToFriday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
            case R.id.tuesday_check_box:
                if (isMondayToFriday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
            case R.id.wednesday_check_box:
                if (isMondayToFriday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
            case R.id.thursday_check_box:
                if (isMondayToFriday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }
            case R.id.friday_check_box:
                if (isMondayToFriday()) {
                    monFriCheckBox.setChecked(true);
                } else {
                    monFriCheckBox.setChecked(false);
                }


        }
    }

    @Override
    public void onClick(View v) {
        confirmDays(v);

    }

    public boolean isMondayToFriday() {
        return mondayCheckBox.isChecked() && tuesdayCheckBox.isChecked() && wednesdayCheckBox.isChecked() && thursdayCheckBox.isChecked() && fridayCheckBox.isChecked();
    }

    public void setUpButtons() {
        buttoSetAlatm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addanalarm();
            }
        });
        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTimePicker();
            }
        });

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

    public void addanalarm() {
        //TODO
        // before adding an alarm check where it should go on the list

        alarmData = new AlarmData(getHour(), getMinute(), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
                , tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
                , vibrateCheckBox.isChecked(), 3, true);
        Alarms.addAlarm(getHour(), getMinute(), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
                , tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
                , vibrateCheckBox.isChecked(), 3, true);

        String toastMessage = "New Alarm set to " + timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute() + " on: " + CustomAdapter.daysWhenToRing(alarmData);
        Toast toast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();
        MainActivity.customAdapter.notifyDataSetChanged();
        closeTimePicker();
    }


    public void closeTimePicker() {
        alertDialog.dismiss();
    }


}
