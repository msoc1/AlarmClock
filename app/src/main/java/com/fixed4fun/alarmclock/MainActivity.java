package com.fixed4fun.alarmclock;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    @SuppressLint("StaticFieldLeak")
    private static CustomAdapter customAdapter;

    private ConstraintLayout toolbar;


    static int toolbarHeight;
    static int position;
    static boolean listState;
    ArrayList<AlarmData> alarms = new ArrayList<>();
    TimePicker timePicker;
    RecyclerView recyclerView;
    Switch turnOnOrOffAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarms = Alarms.getAlarms();
        customAdapter = new CustomAdapter(alarms, getApplicationContext());
        timePicker = findViewById(R.id.time_picker);
        toolbar = findViewById(R.id.include);
        listState = false;
        turnOnOrOffAll = findViewById(R.id.turn_all);

        Alarms.addFirstAlarm();

        final TabLayout tableLayout = findViewById(R.id.tabLayout);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        toolbarHeight = toolbar.getMaxHeight();
        toolbar.setVisibility(View.GONE);

        customAdapter.SetOnClickItemListener(new com.fixed4fun.alarmclock.CustomAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                MainActivity.position = position;
                Bundle bundle = new Bundle();
                DialogFragment dialogFragment = new ModifyTimePicker();
                bundle.putParcelable("modify", alarms.get(position));
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "as");
              //  Alarms.getAlarms().remove(position);
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);
            }
        });

        turnOnOrOffAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // implement turning on and off selected alarms
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);
            }
        });

        //TODO
        // implement changing selected alarms
        // #2 deleting selected alarms


        customAdapter.SetOnLongClickListener(new CustomAdapter.OnLongClickListener() {
            @Override
            public void OnLongClick(int position) {
                toolbar.setVisibility(View.VISIBLE);
                listState = true;
                MainActivity.position = position;
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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

        FloatingActionButton floatingActionButton = findViewById(R.id.new_alarm);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new NewTimePicker();
                dialogFragment.show(getSupportFragmentManager()
                        , "time picker");
                toolbar.setVisibility(View.GONE);
                listState = false;
                recyclerView.setAdapter(customAdapter);
            }
        });




    }

    public static CustomAdapter getCustomAdapter() {
        return customAdapter;
    }


    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        //Alarms.addAlarm(hourOfDay+ "h "+ minute + "min", "wed", "123", true);
        customAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {

        if (listState) {
            listState = false;
            toolbar.setVisibility(View.GONE);
            recyclerView.setAdapter(customAdapter);
        } else {
            super.onBackPressed();
        }
    }
}
