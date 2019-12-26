package com.fixed4fun.alarmclock.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;
import com.fixed4fun.alarmclock.objectLists.AlarmList;


public class ModifyTimePicker extends DialogFragment implements View.OnClickListener {

    private static AlarmData alarmData;
    private TimePicker timePicker;
    private Button buttoSetAlarm;
    private Button cancelAlarm;


    private CheckBox monFriCheckBox;
    private CheckBox satSunCheckBox;
    private CheckBox mondayCheckBox;
    private CheckBox tuesdayCheckBox;
    private CheckBox wednesdayCheckBox;
    private CheckBox thursdayCheckBox;
    private CheckBox fridayCheckBox;
    private CheckBox saturdayCheckBox;
    private CheckBox sundayCheckBox;
    private CustomAdapter customAdapter;
    private AlarmNotifications alarmNotifications;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_timepicker, container);

        timePicker = view.findViewById(R.id.time_picker);
        monFriCheckBox = view.findViewById(R.id.mon_fri_checkBox);
        satSunCheckBox = view.findViewById(R.id.sat_sun_checkBox);
        mondayCheckBox = view.findViewById(R.id.monday_check_box);
        tuesdayCheckBox = view.findViewById(R.id.tuesday_check_box);
        wednesdayCheckBox = view.findViewById(R.id.wednesday_check_box);
        thursdayCheckBox = view.findViewById(R.id.thursday_check_box);
        fridayCheckBox = view.findViewById(R.id.friday_check_box);
        saturdayCheckBox = view.findViewById(R.id.saturday_check_box);
        sundayCheckBox = view.findViewById(R.id.sunday_check_box);
        buttoSetAlarm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);
        alarmNotifications = new AlarmNotifications();


        Bundle bundle = getArguments();
        if (bundle != null) {
            alarmData = bundle.getParcelable("modify");
        }
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        setTimePickerHour();
        setTimePickerMinute();
        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));
        mondayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isMonday());
        tuesdayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isTuesday());
        wednesdayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isWednesday());
        thursdayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isThursday());
        fridayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isFriday());
        saturdayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isSaturday());
        sundayCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isSunday());
        monFriCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isMonday_friday());
        satSunCheckBox.setChecked(AlarmList.getAlarms().get(MainActivity.position).isSaturday_sunday());

        setUpButtons();
        return view;
    }

    private void setUpButtons() {
        buttoSetAlarm.setOnClickListener(this);
        satSunCheckBox.setOnClickListener(this);
        monFriCheckBox.setOnClickListener(this);
        saturdayCheckBox.setOnClickListener(this);
        sundayCheckBox.setOnClickListener(this);
        thursdayCheckBox.setOnClickListener(this);
        fridayCheckBox.setOnClickListener(this);
        mondayCheckBox.setOnClickListener(this);
        tuesdayCheckBox.setOnClickListener(this);
        wednesdayCheckBox.setOnClickListener(this);
        cancelAlarm.setOnClickListener(this);
    }


    private void modifyAlarm() {
        if (!mondayCheckBox.isChecked() && !tuesdayCheckBox.isChecked() && !wednesdayCheckBox.isChecked() && !thursdayCheckBox.isChecked()
                && !fridayCheckBox.isChecked() && !saturdayCheckBox.isChecked() && !sundayCheckBox.isChecked()) {
            AlarmList.getAlarms().get(MainActivity.position).setMonday(true);
            AlarmList.getAlarms().get(MainActivity.position).setTuesday(true);
            AlarmList.getAlarms().get(MainActivity.position).setWednesday(true);
            AlarmList.getAlarms().get(MainActivity.position).setThursday(true);
            AlarmList.getAlarms().get(MainActivity.position).setFriday(true);
            AlarmList.getAlarms().get(MainActivity.position).setSaturday(true);
            AlarmList.getAlarms().get(MainActivity.position).setSunday(true);
            AlarmList.getAlarms().get(MainActivity.position).setMonday_friday(true);
            AlarmList.getAlarms().get(MainActivity.position).setSaturday_sunday(true);
        } else {
            AlarmList.getAlarms().get(MainActivity.position).setMonday(mondayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setTuesday(tuesdayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setWednesday(wednesdayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setThursday(thursdayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setFriday(fridayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setSaturday(saturdayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setSunday(sundayCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setMonday_friday(monFriCheckBox.isChecked());
            AlarmList.getAlarms().get(MainActivity.position).setSaturday_sunday(satSunCheckBox.isChecked());
        }
        AlarmList.getAlarms().get(MainActivity.position).setHour(timePicker.getCurrentHour());
        AlarmList.getAlarms().get(MainActivity.position).setMinute(timePicker.getCurrentMinute());
        AlarmList.getAlarms().get(MainActivity.position).setOnOrOff(true);


        String toastMessage =
                "Modyfied to "
                        + timePicker.getCurrentHour()
                        + ":"
                        + ((timePicker.getCurrentMinute() > 9) ? timePicker.getCurrentMinute() : "0" + timePicker.getCurrentMinute())
                        + " on: "
                        + CustomAdapter.daysWhenToRing(AlarmList.getAlarms().get(MainActivity.position));
        Toast toast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();
        MainActivity.sortList(MainActivity.alarms);
        customAdapter = ((MainActivity) getActivity()).getCustomAdapter();
        customAdapter.notifyDataSetChanged();
        alarmNotifications.startNotification(getContext(), MainActivity.alarms);
        closeTimePicker();
    }


    public void closeTimePicker() {
        getDialog().cancel();
    }

    public void setTimePickerHour() {
        timePicker.setCurrentHour(alarmData.getHour());
    }

    public void setTimePickerMinute() {
        timePicker.setCurrentMinute(alarmData.getMinute());
    }

    public boolean isWorkday() {
        return mondayCheckBox.isChecked() && tuesdayCheckBox.isChecked() && wednesdayCheckBox.isChecked() && thursdayCheckBox.isChecked() && fridayCheckBox.isChecked();
    }

    @Override
    public void onClick(View v) {
        confirmDays(v);
    }

    public void confirmDays(View v) {
        switch (v.getId()) {
            case R.id.cancel_alarm:
                closeTimePicker();
                break;
            case R.id.set_alarm:
                modifyAlarm();
                break;
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

}
