package com.fixed4fun.alarmclock;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class TimePickerViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout cs;
    FrameLayout tp;
    CheckBox mondayCheckBox;


    public TimePickerViewHolder(@NonNull View itemView) {
        super(itemView);
        cs = itemView.findViewById(R.id.custom_time);
        tp = itemView.findViewById(R.id.time_picker);
        mondayCheckBox = itemView.findViewById(R.id.mondayCheckBox);

    }
}
