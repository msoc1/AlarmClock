package com.fixed4fun.alarmclock.timePickerFragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;

import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.alarmsList.Alarms;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.activities.MainActivity;
import com.fixed4fun.alarmclock.R;

import java.util.ArrayList;

public class ChangeAllTimePicker extends DialogFragment implements View.OnClickListener {
    AlertDialog alertDialog;

    NumberPicker hours;
    NumberPicker minutes;
    NumberPicker earlierOrLater;

    Button cancel;
    Button change;
    ArrayList<AlarmData> alarms;

    CustomAdapter adapter;

    RecyclerView recyclerView;

    ConstraintLayout mainActivityToolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = inflater.inflate(R.layout.modify_all_timepickers, container);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        builder.setView(view);
        hours = view.findViewById(R.id.hours);
        minutes = view.findViewById(R.id.minutes);
        earlierOrLater = view.findViewById(R.id.earlier_later);
        cancel = view.findViewById(R.id.cancel);
        change = view.findViewById(R.id.change);
        alarms = Alarms.getAlarms();

        setUpPickers();
        setOnClickListeners();

        alertDialog = builder.create();
        return view;


    }

    private void setUpPickers() {
        hours.setMinValue(0);
        hours.setMaxValue(23);

        minutes.setMinValue(0);
        minutes.setMaxValue(59);

        earlierOrLater.setMinValue(0);
        earlierOrLater.setMaxValue(1);
        earlierOrLater.setDisplayedValues(new String[]{"Earlier", "Later"});

    }

    private void closeChangeDialog() {
        alertDialog.dismiss();
        getDialog().cancel();
    }

    private void setOnClickListeners() {
        change.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void onClickListeners(View v) {
        switch (v.getId()) {
            case R.id.change:
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
                closeChangeDialog();

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
