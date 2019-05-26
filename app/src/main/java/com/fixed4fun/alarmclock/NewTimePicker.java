package com.fixed4fun.alarmclock;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewTimePicker extends DialogFragment implements View.OnClickListener {

    TimePicker timePicker;

    AlertDialog alertDialog;

    AlarmData alarmData;

    Button buttoSetAlatm;
    Button cancelAlarm;
    Button changeSound;

    CheckBox monFriCheckBox;
    CheckBox satSunCheckBox;
    static CheckBox mondayCheckBox;
    static CheckBox tuesdayCheckBox;
    static CheckBox wednesdayCheckBox;
    static CheckBox thursdayCheckBox;
    static CheckBox fridayCheckBox;
    static CheckBox saturdayCheckBox;
    static CheckBox sundayCheckBox;
    CheckBox vibrateCheckBox;

    CustomAdapter adapter;

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


    public static void changeMondayToFriday(boolean bool){
        mondayCheckBox.setChecked(bool);
        tuesdayCheckBox.setChecked(bool);
        wednesdayCheckBox.setChecked(bool);
        thursdayCheckBox.setChecked(bool);
        fridayCheckBox.setChecked(bool);
    }

    public static void changeSaturdayAndSunday(boolean bool){
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
        buttoSetAlatm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnAlarm();
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

    public void addAnAlarm() {
        //TODO
        // before adding an alarm check where it should go on the list
        // check if added alarm is copy of existing alarm


        alarmData = new AlarmData(getHour(timePicker), getMinute(timePicker), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
                , tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
                , vibrateCheckBox.isChecked(), 3, true, false);

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
                    , vibrateCheckBox.isChecked(), 3, true, false);
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
        adapter.notifyDataSetChanged();
        closeTimePicker();
    }


    public void closeTimePicker() {
        alertDialog.dismiss();
    }


}
