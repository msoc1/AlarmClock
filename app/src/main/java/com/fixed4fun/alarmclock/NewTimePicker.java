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
import android.widget.FrameLayout;
import android.widget.TimePicker;

public class NewTimePicker extends DialogFragment {

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



    AlertDialog alertDialog;

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

        timePicker =  view.findViewById(R.id.time_picker);
         timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

        builder.setView(view);
        buttoSetAlatm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);
        setUpButtons();
        alertDialog = builder.create();
        return alertDialog;
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

    }


    public static int getHour(){
        //support older versions of API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getHour();
        } else {
            return timePicker.getCurrentHour();
        }
    }

    public static int getMinute(){
        //support older versions of API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getMinute();
        }
        else {
            return timePicker.getCurrentMinute();
        }
    }


    public void addanalarm(){
        //TODO
        // before adding an alarm check where it should go on the list

        Alarms.addAlarm(getHour(), getMinute(), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
                ,tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
               ,vibrateCheckBox.isChecked(), 3, true);




//        Alarms.addAlarm(new AlarmData( NewTimePicker.getHour(), NewTimePicker.getMinute(), monFriCheckBox.isChecked(), satSunCheckBox.isChecked(), mondayCheckBox.isChecked()
//                ,tuesdayCheckBox.isChecked(), wednesdayCheckBox.isChecked(), thursdayCheckBox.isChecked(), fridayCheckBox.isChecked(), saturdayCheckBox.isChecked(), sundayCheckBox.isChecked()
//                ,vibrateCheckBox.isChecked(), 3, true));
        MainActivity.customAdapter.notifyDataSetChanged();
        closeTimePicker();
    }






    public void closeTimePicker(){
        alertDialog.dismiss();
    }

}
