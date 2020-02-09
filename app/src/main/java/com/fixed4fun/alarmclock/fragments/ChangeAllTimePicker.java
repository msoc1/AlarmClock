package com.fixed4fun.alarmclock.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.objectLists.AlarmList;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;

import java.util.ArrayList;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class ChangeAllTimePicker extends DialogFragment implements View.OnClickListener {

    NumberPickerView hours;
    NumberPickerView minutes;
    NumberPickerView earlierOrLater;
    Button cancel;
    Button change;
    ArrayList<AlarmData> alarms;
    CustomAdapter adapter;
    RecyclerView recyclerView;
    ConstraintLayout mainActivityToolbar;
    private AlarmNotifications alarmNotifications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.modify_all_timepickers, container);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        hours = view.findViewById(R.id.hours);
        minutes = view.findViewById(R.id.minutes);
        earlierOrLater = view.findViewById(R.id.earlier_later);
        cancel = view.findViewById(R.id.cancel);
        change = view.findViewById(R.id.change);
        alarms = AlarmList.getAlarms();
        alarmNotifications = new AlarmNotifications();

        setUpPickers();
        setOnClickListeners();

        return view;


    }

    private void setUpPickers() {
        String[] displayedHours = new String[24];
        for (int i = 0; i < 24; i++) {
            displayedHours[i] = "" + i;
        }
        hours.setDisplayedValues(displayedHours);
        hours.setValue(0);
        hours.setMinValue(0);
        hours.setMaxValue(23);

        String[] displayedMinutes = new String[60];
        for (int i = 0; i < 60; i++) {
            displayedMinutes[i] = "" + i;
        }
        minutes.setDisplayedValues(displayedMinutes);
        minutes.setValue(0);
        minutes.setMinValue(0);
        minutes.setMaxValue(59);

        earlierOrLater.setDisplayedValues(new String[]{getString(R.string.earlier), getString(R.string.later)});
        earlierOrLater.setMinValue(0);
        earlierOrLater.setMaxValue(1);
        earlierOrLater.setValue(0);
    }

    private void closeChangeDialog() {
        getDialog().cancel();
    }

    private void setOnClickListeners() {
        change.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void onClickListeners(View v) {
        switch (v.getId()) {
            case R.id.change:
                int turnedOn = 0;

                for (AlarmData ad : alarms) {
                    if (ad.isSelected()) {
                        turnedOn++;
                    }
                }

                if (hours.getValue() != 0 || minutes.getValue() != 0) {

                    for (AlarmData ad : alarms) {
                        if (ad.isSelected()) {
                            if (earlierOrLater.getValue() == 0) {
                                if (ad.getMinute() - minutes.getValue() < 0) {
                                    ad.setMinute((ad.getMinute() - minutes.getValue() + 60));
                                    ad.setHour(ad.getHour() - 1);
                                } else {
                                    ad.setMinute(ad.getMinute() - minutes.getValue());
                                }
                                if (ad.getHour() - hours.getValue() < 0) {
                                    ad.setHour(ad.getHour() - hours.getValue() + 24);
                                } else {
                                    ad.setHour(ad.getHour() - hours.getValue());
                                }
                            } else {
                                if (ad.getMinute() + minutes.getValue() > 59) {
                                    ad.setMinute((ad.getMinute() + minutes.getValue() - 60));
                                    ad.setHour(ad.getHour() + 1);
                                } else {
                                    ad.setMinute(ad.getMinute() + minutes.getValue());
                                }
                                if (ad.getHour() + hours.getValue() > 23) {
                                    ad.setHour(ad.getHour() + hours.getValue() - 24);
                                } else {
                                    ad.setHour(ad.getHour() + hours.getValue());
                                }
                            }
                        }
                    }
                    MainActivity.listState = false;
                    MainActivity.sortList(MainActivity.alarms);

                    adapter = ((MainActivity) getActivity()).getCustomAdapter();
                    recyclerView = ((MainActivity) getActivity()).getRecyclerView();
                    recyclerView.setAdapter(adapter);
                    mainActivityToolbar = ((MainActivity) getActivity()).getToolbar();
                    mainActivityToolbar.setVisibility(View.GONE);


                    for (AlarmData ad : alarms) {
                        ad.setSelected(false);
                    }
                    adapter.notifyDataSetChanged();
                    alarmNotifications.startNotification(getContext(), alarms);
                    Toast.makeText(getContext(),
                            "" + getResources().getText(R.string.changed) + " " + turnedOn + " " + getResources().getText(R.string.alarmy_toast_main),
                            Toast.LENGTH_SHORT).show();
                    MainActivity.showFABs();
                    closeChangeDialog();
                } else {
                    Toast.makeText(getContext(), R.string.select_times, Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.cancel:
                closeChangeDialog();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        onClickListeners(v);
    }
}
