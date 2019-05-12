package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DateFormat;

public class TimePickerViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout cs;
    FrameLayout tp;
    TextView monday;
    RadioButton mondayRadioButton;

    public TimePickerViewHolder(@NonNull View itemView) {
        super(itemView);
        cs = itemView.findViewById(R.id.custom_time);
        tp = itemView.findViewById(R.id.time_picker);




        monday = itemView.findViewById(R.id.monday);
        mondayRadioButton = itemView.findViewById(R.id.mondayRadioButton);



    }
}
