package com.example.myapplication;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class CustomTimePicker extends DialogFragment {

    FrameLayout timePicker;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_timepicker, null);
        timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        ((TimePicker) timePicker).setIs24HourView(true);
       // timePicker.get

        builder.setView(view);

        return builder.create();

    }
}
