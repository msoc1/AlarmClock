package com.fixed4fun.alarmclock.viewHolders;

import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;
import com.fixed4fun.alarmclock.objectLists.AlarmList;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView timeOfAlarm;
    public TextView setTime;
    public TextView amPmText;

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
        amPmText = itemView.findViewById(R.id.am_pm_textview);




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
                amPmText.setTypeface(roboto_bold);
                amPmText.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
                if (!MainActivity.listState) {
                    setTime.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColor));
                    setTime.setTypeface(roboto_bold);
                }
            } else {

                Typeface roboto_light = Typeface.createFromAsset(ADObject.getAppContext().getAssets(), "fonts/roboto_light.ttf");

                timeOfAlarm.setTypeface(roboto_light);
                timeOfAlarm.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
                amPmText.setTypeface(roboto_light);
                amPmText.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
                if (!MainActivity.listState) {
                    setTime.setTextColor(ContextCompat.getColor(ADObject.getAppContext(), R.color.textColorAlarmOff));
                    setTime.setTypeface(roboto_light);
                }
            }
        });

        onOrOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
            }
        });

        if (MainActivity.listState) {

            selected.setOnClickListener(v -> AlarmList.getAlarms().get(getAdapterPosition()).setSelected(selected.isChecked()));

            deleteAlarm.setOnClickListener(v -> {
                alarmNotifications.cancelAlarm(MainActivity.alarms.get(getAdapterPosition()), ADObject.getAppContext());
                deleteSelectedAlarm(getAdapterPosition());
                adapter = MainActivity.getCustomAdapter();
                adapter.notifyDataSetChanged();
                adapter = null;
//                alarmNotifications.startNotification(ADObject.getAppContext(), MainActivity.alarms);
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
