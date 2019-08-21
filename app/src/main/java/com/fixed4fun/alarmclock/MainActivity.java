package com.fixed4fun.alarmclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static int toolbarHeight;
    static int position;

    static boolean listState;


    static String toastMessage;
    static ArrayList<AlarmData> alarms = new ArrayList<>();
    static ArrayList<AlarmData> tempList;
    @SuppressLint("StaticFieldLeak")
    static private CustomAdapter customAdapter;
    TimePicker timePicker;

    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;
    Switch turnOnOrOffAll;
    CheckBox selectAll;
    Button deleteAll;
    Button changeAll;
    Toast notificationToast;
    Button checkAllAlarms;
    Button notificationButton;
    private ConstraintLayout toolbar;

    public static CustomAdapter getCustomAdapter() {
        return customAdapter;
    }

    public static void sortList(ArrayList<AlarmData> a) {
        for (int i = 0; i < a.size(); i++) {
            for (int j = i + 1; j <= a.size() - 1; j++) {
                if (a.get(i).getHour() > a.get(j).getHour()) {
                    Collections.swap(a, i, j);
                }
            }
        }
        for (int i = 0; i < a.size(); i++) {
            for (int j = i + 1; j <= a.size() - 1; j++) {
                if (a.get(i).getHour() == a.get(j).getHour()) {
                    sortMinute(a, a.get(i), a.get(j));
                }
            }
        }
    }

    public static void sortMinute(ArrayList<AlarmData> a, AlarmData o1, AlarmData o2) {
        if (o1.getMinute() > o2.getMinute()) {
            Collections.swap(a, a.indexOf(o1), a.indexOf(o2));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarms = Alarms.getAlarms();
        tempList = new ArrayList<>();
        Alarms.addFirstAlarm();

        sortList(alarms);

        customAdapter = new CustomAdapter(alarms, getApplicationContext());

        listState = false;

        timePicker = findViewById(R.id.time_picker);
        toolbar = findViewById(R.id.include);
        turnOnOrOffAll = findViewById(R.id.turn_all);
        selectAll = findViewById(R.id.select_all);
        deleteAll = findViewById(R.id.delete_all);
        changeAll = findViewById(R.id.change_all);
        floatingActionButton = findViewById(R.id.new_alarm);
        recyclerView = findViewById(R.id.recyclerView);
        TabLayout tableLayout = findViewById(R.id.tabLayout);

        notificationButton = findViewById(R.id.notification);

        toastMessage = "";
        notificationToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        toolbarHeight = toolbar.getMaxHeight();
        toolbar.setVisibility(View.GONE);

        setOnClickListeners();

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerView.setAdapter(customAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                recyclerView.setAdapter(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void setOnClickListeners() {
        changeAll.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        deleteAll.setOnClickListener(this);
        turnOnOrOffAll.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        notificationButton.setOnClickListener(v -> startNotification());


        customAdapter.SetOnClickItemListener(position -> {
            MainActivity.position = position;
            Bundle bundle = new Bundle();
            DialogFragment dialogFragment = new ModifyTimePicker();
            bundle.putParcelable("modify", alarms.get(position));
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getSupportFragmentManager(), "as");
            customAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(customAdapter);
        });
        customAdapter.SetOnLongClickListener(position -> {
            toolbar.setVisibility(View.VISIBLE);
            listState = true;
            MainActivity.position = position;
            customAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(customAdapter);
        });
    }

    private void onClickListeners(View v) {
        switch (v.getId()) {
            case R.id.select_all:
                if (selectAll.isChecked()) {
                    for (AlarmData ad : alarms) {
                        ad.setSelected(true);
                    }
                } else {
                    for (AlarmData ad : alarms) {
                        ad.setSelected(false);
                    }
                }
                customAdapter.notifyDataSetChanged();
                break;

            case R.id.delete_all:
                for (AlarmData ad : alarms) {
                    if (ad.isSelected()) {
                        tempList.add(ad);
                    }
                }
                alarms.removeAll(tempList);
                toastMessage = "Deleted " + tempList.size() + " alarms.";
                notificationToast.setText(toastMessage);
                notificationToast.show();
                tempList.clear();
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);
                break;

            case R.id.change_all:
                ChangeAllFragment dialogFragment = new ChangeAllFragment();
                dialogFragment.show(getSupportFragmentManager(), "asa");
                customAdapter.notifyDataSetChanged();
                break;

            case R.id.turn_all:
                for (AlarmData ad : alarms) {
                    if (ad.isSelected())
                        ad.setOnOrOff(turnOnOrOffAll.isChecked());
                }
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);
                break;

            case R.id.new_alarm:
                DialogFragment timePickerFragment = new NewTimePicker();
                timePickerFragment.show(getSupportFragmentManager()
                        , "time picker");
                toolbar.setVisibility(View.GONE);
                listState = false;
                recyclerView.setAdapter(customAdapter);
                break;
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ConstraintLayout getToolbar() {
        return toolbar;
    }

    @Override
    public void onBackPressed() {
        if (listState) {
            listState = false;
            selectAll.setChecked(false);
            turnOnOrOffAll.setChecked(false);
            toolbar.setVisibility(View.GONE);
            for (AlarmData ad : alarms) {
                ad.setSelected(false);
            }
            recyclerView.setAdapter(customAdapter);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        onClickListeners(v);
    }

    public void startNotification() {
        for (AlarmData currentAlarmData : alarms) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, currentAlarmData.getHour());
            calendar.set(Calendar.MINUTE, currentAlarmData.getMinute());
            calendar.set(Calendar.SECOND, 0);
            checkForActiveDays(calendar, currentAlarmData);
        }
    }

    private void startAlarm(Calendar c, AlarmData ad) {
        if (!c.before(Calendar.getInstance()) && ad.isOnOrOff()) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReceiver.class);
            intent.setAction(Long.toString(System.currentTimeMillis()));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarms.indexOf(ad), intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
    }


    private void cancelAlarm(AlarmData ad) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                alarms.indexOf(ad), intent, 0);
        alarmManager.cancel(pendingIntent);
    }

    private void resetAlarms(){

        for (AlarmData currentAlarmData : alarms) {
                cancelAlarm(currentAlarmData);
        }
        startNotification();
        }

    private void checkForActiveDays(Calendar calendar, AlarmData alarmData) {
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        switch (date) {
            //check for monday
            case 2:
                if (alarmData.isMonday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
                //check for tuesday
            case 3:
                if (alarmData.isTuesday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
                //check for wednesday
            case 4:
                if (alarmData.isWednesday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
                //check for thursday
            case 5:
                if (alarmData.isThursday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
                //check for friday
            case 6:
                if (alarmData.isFriday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
                //check for saturday
            case 7:
                if (alarmData.isSaturday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
                //check for sunday
            case 1:
                if (alarmData.isSunday()) {
                    startAlarm(calendar, alarmData);
                    break;
                }
        }

    }
}
