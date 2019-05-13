package com.fixed4fun.alarmclock;


import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TimePicker;

public class CustomTimePicker extends DialogFragment {

   static FrameLayout timePicker;
    Button buttoSetAlatm;
    Button cancelAlarm;
    AlertDialog alertDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_timepicker, null);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        ((TimePicker) timePicker).setIs24HourView(true);
        builder.setView(view);
        buttoSetAlatm = view.findViewById(R.id.setAlarm);
        cancelAlarm = view.findViewById(R.id.cancelAlarm);
        setUpButtons();
        alertDialog = builder.create();
        return alertDialog;
    }

    public void setUpButtons() {
        buttoSetAlatm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getHour(){
        return ((TimePicker) timePicker).getHour();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getMinute(){
       return ((TimePicker) timePicker).getMinute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addanalarm(){
        String minutes =""+ ((CustomTimePicker.getMinute()<10) ? "0"+CustomTimePicker.getMinute() : CustomTimePicker.getMinute());
        Alarms.addAlarm(CustomTimePicker.getHour()+ ":" + minutes
                , "wed"
                , "123"
                , true);
        MainActivity.customAdapter.notifyDataSetChanged();
        closeTimePicker();
    }




    public void closeTimePicker(){
        alertDialog.dismiss();
    }

}
