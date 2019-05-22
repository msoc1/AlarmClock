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

public class ModifyTimePicker extends DialogFragment {

    static TimePicker timePicker;
    Button buttoSetAlatm;
    Button cancelAlarm;
    AlertDialog alertDialog;
    static AlarmData alarmData;

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
         mondayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isMonday());
         tuesdayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isTuesday());
         wednesdayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isWednesday());
         thursdayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isThursday());
         fridayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isFriday());
         saturdayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isSaturday());
         sundayCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isSunday());
        monFriCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isMonday_friday());
        satSunCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isSaturday_sunday());
         vibrateCheckBox.setChecked( Alarms.getAlarms().get(MainActivity.position).isVibrate());

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
        Alarms.getAlarms().get(MainActivity.position).setHour(timePicker.getCurrentHour());
        Alarms.getAlarms().get(MainActivity.position).setMinute(timePicker.getCurrentMinute());
        Alarms.getAlarms().get(MainActivity.position).setMonday(mondayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setTuesday(tuesdayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setWednesday(wednesdayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setThursday(thursdayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setFriday(fridayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setSaturday(saturdayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setSunday(sundayCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setMonday_friday(monFriCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setSaturday_sunday(satSunCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setVibrate(vibrateCheckBox.isChecked());
        Alarms.getAlarms().get(MainActivity.position).setOnOrOff(true);

        String toastMessage = "Modyfied to " + timePicker.getCurrentHour()+":"+timePicker.getCurrentMinute() + " on: " + CustomAdapter.daysWhenToRing(Alarms.getAlarms().get(MainActivity.position));
        Toast toast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();
        MainActivity.customAdapter.notifyDataSetChanged();
        closeTimePicker();
    }


    public void closeTimePicker() {
        alertDialog.dismiss();
    }

    public void setTimePickerHour(){
        timePicker.setCurrentHour(alarmData.getHour());
    }
    public void setTimePickerMinute(){
         timePicker.setCurrentMinute(alarmData.getMinute());
    }



}
