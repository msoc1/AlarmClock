package com.fixed4fun.alarmclock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    ArrayList<AlarmData> alarmDataArrayList;
    Context context;

    private OnItemClickListener mListener;

    private OnLongClickListener mLongListener;

    public interface OnLongClickListener{
        void OnLongClick();
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void SetOnClickItemListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void SetOnLongClickListener(OnLongClickListener listener){
        mLongListener = listener;
    }



    public CustomAdapter(ArrayList<AlarmData> alarmDataArrayList, Context context) {
        this.alarmDataArrayList = alarmDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.position_layout, viewGroup, false);
        return new CustomViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.onOrOff.setChecked(alarmDataArrayList.get(i).isOnOrOff());
        customViewHolder.howLong.setText(alarmDataArrayList.get(i).getHowLongTillAlarm());
        customViewHolder.timeOfAlarm.setText(alarmDataArrayList.get(i).getTimeOfAlarm());
        customViewHolder.setTime.setText(alarmDataArrayList.get(i).getWhenToGoOff());
    }

    @Override
    public int getItemCount() {
        return alarmDataArrayList.size();
    }
}
