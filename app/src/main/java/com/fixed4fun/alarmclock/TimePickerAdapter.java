package com.fixed4fun.alarmclock;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimePickerAdapter extends RecyclerView.Adapter<TimePickerViewHolder> {


    @NonNull
    @Override
    public TimePickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_timepicker, viewGroup,false);
        return new TimePickerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimePickerViewHolder timePickerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
