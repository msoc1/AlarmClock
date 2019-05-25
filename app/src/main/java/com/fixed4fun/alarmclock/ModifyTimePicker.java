package com.fixed4fun.alarmclock;

import android.app.Dialog;
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

public class ModifyTimePicker extends DialogFragment implements View.OnClickListener {

    TimePicker timePicker;

    static AlarmData alarmData;

    Button buttoSetAlatm;
    Button cancelAlarm;
    Button changeSound;

    AlertDialog alertDialog;

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
    CustomAdapter customAdapter;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_timepicker, null);

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
        vibrateCheckBox = view.findViewById(R.id.vibration_check_box);
        buttoSetAlatm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);

        Bundle bundle = getArguments();
        if (bundle != null) {
            alarmData = bundle.getParcelable("modify");
        }

        setTimePickerHour();
        setTimePickerMinute();
        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));
        mondayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isMonday());
        tuesdayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isTuesday());
        wednesdayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isWednesday());
        thursdayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isThursday());
        fridayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isFriday());
        saturdayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isSaturday());
        sundayCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isSunday());
        monFriCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isMonday_friday());
        satSunCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isSaturday_sunday());
        vibrateCheckBox.setChecked(Alarms.getAlarms().get(MainActivity.position).isVibrate());

        builder.setView(view);
        setUpButtons();
        alertDialog = builder.create();
        return alertDialog;
    }

    public void setUpButtons() {
        buttoSetAlatm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyAlarm();
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


        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTimePicker();
            }
        });

    }


    public void modifyAlarm() {
        //TODO
        // implement sound modyfying timepicker position


        if (!mondayCheckBox.isChecked() && !tuesdayCheckBox.isChecked() && !wednesdayCheckBox.isChecked() && !thursdayCheckBox.isChecked()
                && !fridayCheckBox.isChecked() && !saturdayCheckBox.isChecked() && !sundayCheckBox.isChecked()) {
            Alarms.getAlarms().get(MainActivity.position).setMonday(true);
            Alarms.getAlarms().get(MainActivity.position).setTuesday(true);
            Alarms.getAlarms().get(MainActivity.position).setWednesday(true);
            Alarms.getAlarms().get(MainActivity.position).setThursday(true);
            Alarms.getAlarms().get(MainActivity.position).setFriday(true);
            Alarms.getAlarms().get(MainActivity.position).setSaturday(true);
            Alarms.getAlarms().get(MainActivity.position).setSunday(true);
            Alarms.getAlarms().get(MainActivity.position).setMonday_friday(true);
            Alarms.getAlarms().get(MainActivity.position).setSaturday_sunday(true);
        } else {
            Alarms.getAlarms().get(MainActivity.position).setMonday(mondayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setTuesday(tuesdayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setWednesday(wednesdayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setThursday(thursdayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setFriday(fridayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setSaturday(saturdayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setSunday(sundayCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setMonday_friday(monFriCheckBox.isChecked());
            Alarms.getAlarms().get(MainActivity.position).setSaturday_sunday(satSunCheckBox.isChecked());
        }

        Alarms.getAlarms().get(MainActivity.position).setHour(timePicker.getCurrentHour());
        Alarms.getAlarms().get(MainActivity.position).setMinute(timePicker.getCurrentMinute());
        Alarms.getAlarms().get(MainActivity.position).setVibrate(vibrateCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setOnOrOff(true);

        String toastMessage =
                "Modyfied to "
                        + timePicker.getCurrentHour()
                        + ":"
                        + ((timePicker.getCurrentMinute() > 9) ? timePicker.getCurrentMinute() : "0" + timePicker.getCurrentMinute())
                        + " on: "
                        + CustomAdapter.daysWhenToRing(Alarms.getAlarms().get(MainActivity.position));
        Toast toast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();
        customAdapter = ((MainActivity) getActivity()).getCustomAdapter();
        customAdapter.notifyDataSetChanged();
        closeTimePicker();
    }


    public void closeTimePicker() {
        alertDialog.dismiss();
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
                break;
            case R.id.sat_sun_checkBox:
                if (satSunCheckBox.isChecked()) {
                    sundayCheckBox.setChecked(true);
                    saturdayCheckBox.setChecked(true);
                } else {
                    sundayCheckBox.setChecked(false);
                    saturdayCheckBox.setChecked(false);
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

}
