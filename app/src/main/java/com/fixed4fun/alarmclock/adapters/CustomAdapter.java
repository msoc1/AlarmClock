package com.fixed4fun.alarmclock.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.viewHolders.CustomViewHolder;

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
        Typeface roboto_bold = Typeface.createFromAsset(ADObject.getAppContext().getAssets(), "fonts/roboto_bold.ttf");
        Typeface roboto_light = Typeface.createFromAsset(ADObject.getAppContext().getAssets(), "fonts/roboto_light.ttf");
        Calendar calendar = Calendar.getInstance();

        if (!DateFormat.is24HourFormat(ADObject.getAppContext())) {
            int mHour = alarmDataArrayList.get(i).getHour();
            if (mHour >= 12) {
                customViewHolder.amPmText.setText("PM");
            } else {
                customViewHolder.amPmText.setText("AM");
            }
        } else {
            customViewHolder.amPmText.setText("");
        }


        if (!MainActivity.listState) {
            //normal list item view
            customViewHolder.timeOfAlarm.setText(displayTimeOfAlarm(alarmDataArrayList.get(i)));
            customViewHolder.onOrOff.setChecked(alarmDataArrayList.get(i).isOnOrOff());
            customViewHolder.setTime.setText(daysWhenToRing(alarmDataArrayList.get(i)));
            if (alarmDataArrayList.get(i).isOnOrOff()) {
                customViewHolder.setTime.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
                customViewHolder.setTime.setTypeface(roboto_bold);

            } else {
                customViewHolder.setTime.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
                customViewHolder.setTime.setTypeface(roboto_light);

            }

        } else {
            customViewHolder.timeOfAlarm.setText(displayTimeOfAlarm(alarmDataArrayList.get(i)));
            customViewHolder.onOrOff.setChecked(alarmDataArrayList.get(i).isOnOrOff());
            customViewHolder.selected.setChecked(alarmDataArrayList.get(i).isSelected());
        }

        if (alarmDataArrayList.get(i).isOnOrOff()) {
            customViewHolder.amPmText.setTypeface(roboto_bold);
            customViewHolder.amPmText.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
            customViewHolder.timeOfAlarm.setTypeface(roboto_bold);
            customViewHolder.timeOfAlarm.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
        } else {
            customViewHolder.amPmText.setTypeface(roboto_light);
            customViewHolder.amPmText.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
            customViewHolder.timeOfAlarm.setTypeface(roboto_light);
            customViewHolder.timeOfAlarm.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
        }

    }

    private String displayTimeOfAlarm(AlarmData alarmData) {

        String hourString = "";
        if (!DateFormat.is24HourFormat(ADObject.getAppContext())) {
            if((alarmData.getHour()<9 && alarmData.getHour()!=0 )||   ( alarmData.getHour()<22 && alarmData.getHour()>13)){
                hourString += "0";
            }
            if (alarmData.getHour() == 0) {
                hourString += "12";
            } else if (alarmData.getHour() > 12) {
                hourString += (alarmData.getHour() - 12);
            } else {
                hourString += alarmData.getHour();
            }
        } else {
            hourString += alarmData.getHour();
        }

        return hourString
                + ":"
                + ((alarmData.getMinute() > 9) ? alarmData.getMinute() : ("0" + alarmData.getMinute()));
    }

    public static String daysWhenToRing(AlarmData alarm) {

        String message = "";

        boolean workDays = alarm.isMonday_friday();
        boolean weekend = alarm.isSaturday_sunday();
        boolean wholeWeek = alarm.isMonday() && alarm.isTuesday() && alarm.isWednesday() && alarm.isThursday() && alarm.isFriday() && alarm.isSaturday() && alarm.isSunday();

        if (workDays && weekend || wholeWeek) {

            return ADObject.getAppContext().getResources().getString(R.string.everyday);
        } else if (workDays) {
            if (alarm.isSaturday()) {
                return ADObject.getAppContext().getResources().getString(R.string.mon_sat);
            }
            if (alarm.isSunday()) {
                return ADObject.getAppContext().getResources().getString(R.string.mon_fri_sun);
            } else {
                return ADObject.getAppContext().getResources().getString(R.string.mon_fri_adapter);
            }
        } else {
            if (alarm.isMonday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.monday) + " ";
            }
            if (alarm.isTuesday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.tuesday) + " ";
            }
            if (alarm.isWednesday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.wednesday) + " ";
            }
            if (alarm.isThursday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.thursday) + " ";
            }
            if (alarm.isFriday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.friday) + " ";
            }
            if (alarm.isSaturday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.saturday) + " ";
            }
            if (alarm.isSunday()) {
                message += ADObject.getAppContext().getResources().getString(R.string.sunday);
            }
        }
        return message;
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
