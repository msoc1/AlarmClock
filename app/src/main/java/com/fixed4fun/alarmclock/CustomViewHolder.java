package com.fixed4fun.alarmclock;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout cs;
    TextView timeOfAlarm;
    TextView setTime;
    TextView howLong;
    Switch onOrOff;

    public CustomViewHolder(@NonNull View itemView, final CustomAdapter.OnItemClickListener listener) {
        super(itemView);
        cs = itemView.findViewById(R.id.positionConstraintLayout);
        timeOfAlarm = itemView.findViewById(R.id.timeOfAlarm);
        setTime = itemView.findViewById(R.id.setTime);
        howLong = itemView.findViewById(R.id.howLong);
        onOrOff = itemView.findViewById(R.id.onOrOff);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.OnItemClick(position);
                    }
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity.toolbar.setMaxHeight(MainActivity.toolbarHeight);

                return false;
            }
        });

    }
}
