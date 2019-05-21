package com.fixed4fun.alarmclock;

import android.app.TimePickerDialog;
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
import android.widget.FrameLayout;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    ArrayList<AlarmData> alarms = new ArrayList<>();
    static CustomAdapter customAdapter;
    FrameLayout timePicker;
    static ConstraintLayout toolbar;
    static int toolbarHeight;
    static boolean listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarms = Alarms.getAlarms();
        customAdapter = new CustomAdapter(alarms, getApplicationContext());
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        toolbar = findViewById(R.id.include);
        listState = false;

        final TabLayout tableLayout = findViewById(R.id.tabLayout);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);

        toolbarHeight = toolbar.getMaxHeight();
        toolbar.setVisibility(View.GONE);





        customAdapter.SetOnClickItemListener(new com.fixed4fun.alarmclock.CustomAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                Bundle bundle = new Bundle();
                DialogFragment dialogFragment = new ModifyTimePicker();
                bundle.putParcelable("modify", alarms.get(position));
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getSupportFragmentManager(), "as");
              //  toolbar.setVisibility(View.GONE);
               // listState = false;
                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);


            }
        });

        customAdapter.SetOnLongClickListener(new CustomAdapter.OnLongClickListener() {
            @Override
            public void OnLongClick(int position) {
               toolbar.setVisibility(View.VISIBLE);
                listState = true;


                customAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(customAdapter);


            }
        });




        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0) {
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
            }
        });

    }



    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        //Alarms.addAlarm(hourOfDay+ "h "+ minute + "min", "wed", "123", true);
        customAdapter.notifyDataSetChanged();
    }



}
