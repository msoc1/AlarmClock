package com.fixed4fun.alarmclock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    ArrayList<AlarmData> alarmDataArrayList;
    Context context;

    private OnItemClickListener mListener;

    private OnLongClickListener mLongListener;

    public CustomAdapter(ArrayList<AlarmData> alarmDataArrayList, Context context) {
        this.alarmDataArrayList = alarmDataArrayList;
        this.context = context;
    }

    public void SetOnClickItemListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void SetOnLongClickListener(OnLongClickListener listener) {
        mLongListener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        if (!MainActivity.listState) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.position_layout, viewGroup, false);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.position_onhold_layout, viewGroup, false);
        }
        return new CustomViewHolder(v, mListener, mLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        if (!MainActivity.listState) {
            //normal list item view
            customViewHolder.timeOfAlarm.setText(displayTimeOfAlarm(alarmDataArrayList.get(i)));
            customViewHolder.onOrOff.setChecked(alarmDataArrayList.get(i).isOnOrOff());
            customViewHolder.howLong.setText(timeUntilAlarm(context.getApplicationContext(), alarmDataArrayList.get(i)));
            customViewHolder.setTime.setText(daysWhenToRing(alarmDataArrayList.get(i)));

        } else {
            customViewHolder.timeOfAlarm.setText(displayTimeOfAlarm(alarmDataArrayList.get(i)));
            customViewHolder.onOrOff.setChecked(alarmDataArrayList.get(i).isOnOrOff());
        }
    }

    private String displayTimeOfAlarm(AlarmData alarmData) {
        return alarmData.getHour() + ":" +((alarmData.getMinute()>9)? alarmData.getMinute() : ("0" + alarmData.getMinute())) ;
    }

    private String daysWhenToRing(AlarmData alarmData) {
        String message = "";

        if (alarmData.isMonday_friday() && alarmData.isSaturday_sunday()) {
            return "Everyday";
        } else if (alarmData.isMonday_friday()) {
            return "Mon - Fri";
        } else if (alarmData.isSaturday_sunday()) {
            return "Sat-Sun";
        } else {
            if (alarmData.isMonday()) {
                message += "M ";
            }
            if (alarmData.isTuesday()) {
                message += "T ";
            }
            if (alarmData.isWednesday()) {
                message += "W ";
            }
            if (alarmData.isThursday()) {
                message += "T ";
            }
            if (alarmData.isFriday()) {
                message += "F ";
            }
            if (alarmData.isSaturday()) {
                message += "S ";
            }
            if (alarmData.isSunday()) {
                message += "S";
            }
        }
        return message;
    }


    private String timeUntilAlarm(Context context, AlarmData alarmData) {

        Calendar c = Calendar.getInstance();
        int hour = alarmData.getHour();
        int minute = alarmData.getMinute();


        if (DateFormat.is24HourFormat(context)) {
           // hour = c.get(Calendar.HOUR_OF_DAY);
        } else {
          //  hour = c.get(Calendar.HOUR);
        }
        //TODO
        //find way to get time difference
        return "Time difference is: XX:XX";
    }


    @Override
    public int getItemCount() {
        return alarmDataArrayList.size();
    }

    public interface OnLongClickListener {
        void OnLongClick(int position);
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
}
