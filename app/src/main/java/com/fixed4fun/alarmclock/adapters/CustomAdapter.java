package com.fixed4fun.alarmclock.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.viewHolders.CustomViewHolder;
import com.fixed4fun.alarmclock.R;

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
            customViewHolder.setTime.setText(daysWhenToRing(alarmDataArrayList.get(i)));

        } else {
            customViewHolder.timeOfAlarm.setText(displayTimeOfAlarm(alarmDataArrayList.get(i)));
            customViewHolder.onOrOff.setChecked(alarmDataArrayList.get(i).isOnOrOff());
            customViewHolder.selected.setChecked(alarmDataArrayList.get(i).isSelected());

        }
    }

    private String displayTimeOfAlarm(AlarmData alarmData) {
        return ((alarmData.getHour() > 9) ? alarmData.getHour() : ("0" + alarmData.getHour()))
                + ":"
                + ((alarmData.getMinute() > 9) ? alarmData.getMinute() : ("0" + alarmData.getMinute()));
    }

    public static String daysWhenToRing(AlarmData alarm) {

        String message = "";

        boolean workDays = alarm.isMonday_friday();
        boolean weekend = alarm.isSaturday_sunday();
        boolean wholeWeek = alarm.isMonday() && alarm.isTuesday() && alarm.isWednesday() && alarm.isThursday() && alarm.isFriday() && alarm.isSaturday() && alarm.isSunday();

        if (workDays && weekend || wholeWeek) {
            return "Everyday";
        } else if (workDays) {
            if (alarm.isSaturday()) {
                return "Mon - Sat";
            }
            if (alarm.isSunday()) {
                return "Mon - Fri & Sun";
            } else {
                return "Mon - Fri";
            }
        } else {
            if (alarm.isMonday()) {
                message += "M ";
            }
            if (alarm.isTuesday()) {
                message += "T ";
            }
            if (alarm.isWednesday()) {
                message += "W ";
            }
            if (alarm.isThursday()) {
                message += "T ";
            }
            if (alarm.isFriday()) {
                message += "F ";
            }
            if (alarm.isSaturday()) {
                message += "S ";
            }
            if (alarm.isSunday()) {
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
