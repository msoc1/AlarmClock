package com.fixed4fun.alarmclock.viewHolders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.alarmsList.Alarms;
import com.fixed4fun.alarmclock.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView timeOfAlarm;
    public TextView setTime;
    public TextView howLong;

    public Switch onOrOff;

    public CheckBox selected;
    CustomAdapter adapter;
    private Button deleteAlarm;

    public CustomViewHolder(@NonNull View itemView, final CustomAdapter.OnItemClickListener listener, final CustomAdapter.OnLongClickListener longListener) {
        super(itemView);

        timeOfAlarm = itemView.findViewById(R.id.time_of_alarm);
        setTime = itemView.findViewById(R.id.set_time);
        howLong = itemView.findViewById(R.id.how_long);
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

        onOrOff.setOnClickListener(v -> Alarms.getAlarms().get(getAdapterPosition()).setOnOrOff(onOrOff.isChecked()));

        if (MainActivity.listState) {

            selected.setOnClickListener(v -> Alarms.getAlarms().get(getAdapterPosition()).setSelected(selected.isChecked()));

            deleteAlarm.setOnClickListener(v -> {
                deleteSelectedAlarm(getAdapterPosition());
                adapter = MainActivity.getCustomAdapter();
                adapter.notifyDataSetChanged();
                adapter = null;
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
        Alarms.getAlarms().remove(positon);
    }
}
