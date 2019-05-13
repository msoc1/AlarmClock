package com.fixed4fun.alarmclock;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePicker extends DialogFragment  {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar currentCalendar = Calendar.getInstance();
        int hourOfDay = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int minuteOfHour = currentCalendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity()
                ,(TimePickerDialog.OnTimeSetListener) getActivity()
                , hourOfDay
                , minuteOfHour
                , DateFormat.is24HourFormat(getActivity()));
    }



}
