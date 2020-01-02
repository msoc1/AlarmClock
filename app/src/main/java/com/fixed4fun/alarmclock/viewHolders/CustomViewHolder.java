package com.fixed4fun.alarmclock.viewHolders;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.objectLists.AlarmList;
import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView timeOfAlarm;
    public TextView setTime;

    public SwitchCompat onOrOff;

    public CheckBox selected;
    CustomAdapter adapter;
    private ImageView deleteAlarm;
    private AlarmNotifications alarmNotifications;

    public CustomViewHolder(@NonNull View itemView, final CustomAdapter.OnItemClickListener listener, final CustomAdapter.OnLongClickListener longListener) {
        super(itemView);
        alarmNotifications = new AlarmNotifications();

        timeOfAlarm = itemView.findViewById(R.id.time_of_alarm);
        setTime = itemView.findViewById(R.id.set_time);
        onOrOff = itemView.findViewById(R.id.on_or_off);
        deleteAlarm = itemView.findViewById(R.id.delete_alarm);
        selected = itemView.findViewById(R.id.selected);




        itemView.setOnClickListener(v -> {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.OnItemClick(position);
                }
            }
        });

        onOrOff.setOnClickListener(v -> {
            AlarmList.getAlarms().get(getAdapterPosition()).setOnOrOff(onOrOff.isChecked());
            alarmNotifications.startNotification(ADObject.getAppContext(), MainActivity.alarms);
            if (onOrOff.isChecked()) {
                Typeface roboto_bold = Typeface.createFromAsset(ADObject.getAppContext().getAssets(), "fonts/roboto_bold.ttf");

                timeOfAlarm.setTypeface(roboto_bold);
                timeOfAlarm.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
                if (!MainActivity.listState) {
                    setTime.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
                    setTime.setTypeface(roboto_bold);
                }
            } else {

                Typeface roboto_light = Typeface.createFromAsset(ADObject.getAppContext().getAssets(), "fonts/roboto_light.ttf");

                timeOfAlarm.setTypeface(roboto_light);
                timeOfAlarm.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
                if (!MainActivity.listState) {
                    setTime.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
                    setTime.setTypeface(roboto_light);
                }
            }
        });

        if (MainActivity.listState) {

            selected.setOnClickListener(v -> AlarmList.getAlarms().get(getAdapterPosition()).setSelected(selected.isChecked()));

            deleteAlarm.setOnClickListener(v -> {
                deleteSelectedAlarm(getAdapterPosition());
                adapter = MainActivity.getCustomAdapter();
                adapter.notifyDataSetChanged();
                adapter = null;
                alarmNotifications.startNotification(ADObject.getAppContext(), MainActivity.alarms);
            });
        }

        itemView.setOnLongClickListener(v -> {
            if (longListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    longListener.OnLongClick(position);
                }
            }
            return true;
        });
    }

    private void deleteSelectedAlarm(int positon) {
        AlarmList.getAlarms().remove(positon);
    }
}
