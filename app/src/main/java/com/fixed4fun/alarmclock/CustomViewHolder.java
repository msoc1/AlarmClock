package com.fixed4fun.alarmclock;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView timeOfAlarm;
    TextView setTime;
    TextView howLong;

    Switch onOrOff;

    CheckBox selected;
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


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }
            }
        });

        onOrOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alarms.getAlarms().get(getAdapterPosition()).setOnOrOff(onOrOff.isChecked());
            }
        });

        if (MainActivity.listState) {

            selected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alarms.getAlarms().get(getAdapterPosition()).setSelected(selected.isChecked());
                }
            });

            deleteAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteSelectedAlarm(getAdapterPosition());
                    adapter = MainActivity.getCustomAdapter();
                    adapter.notifyDataSetChanged();
                    adapter = null;
                }
            });
        }

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        longListener.OnLongClick(position);
                    }
                }
                return true;
            }
        });
    }

    private void deleteSelectedAlarm(int positon) {
        Alarms.getAlarms().remove(positon);
    }
}
