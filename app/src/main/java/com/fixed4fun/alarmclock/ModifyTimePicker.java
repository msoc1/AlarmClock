package com.fixed4fun.alarmclock;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TimePicker;

public class ModifyTimePicker extends DialogFragment {

    static FrameLayout timePicker;
    Button buttoSetAlatm;
    Button cancelAlarm;
    AlertDialog alertDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_timepicker, null);
        timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
        ((android.widget.TimePicker) timePicker).setIs24HourView(DateFormat.is24HourFormat(getContext()));

        builder.setView(view);
        buttoSetAlatm = view.findViewById(R.id.set_alarm);
        cancelAlarm = view.findViewById(R.id.cancel_alarm);
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
    public static int getHour() {
        return ((android.widget.TimePicker) timePicker).getHour();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getMinute() {
        return ((TimePicker) timePicker).getMinute();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addanalarm() {
        //TODO
        //implement modyfying timepicker position
        MainActivity.customAdapter.notifyDataSetChanged();
        closeTimePicker();
    }


    public void closeTimePicker() {
        alertDialog.dismiss();
    }

}
