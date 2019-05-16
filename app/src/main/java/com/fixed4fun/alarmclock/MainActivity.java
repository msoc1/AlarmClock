package com.fixed4fun.alarmclock;

import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Alarms alarms = new Alarms();
     static com.fixed4fun.alarmclock.CustomAdapter customAdapter;
    FrameLayout timePicker;
    static ConstraintLayout toolbar;
    static int toolbarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customAdapter = new com.fixed4fun.alarmclock.CustomAdapter(alarms.getAlarms(), getApplicationContext());
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        toolbar = findViewById(R.id.include);

        final TabLayout tableLayout = findViewById(R.id.tabLayout);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);

        toolbarHeight = toolbar.getMaxHeight();

        toolbar.setMaxHeight(0);




        customAdapter.SetOnClickItemListener(new com.fixed4fun.alarmclock.CustomAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
               // Alarms.deleteAlarm(position);
                Toast n = new Toast(getBaseContext());
                n.makeText(getApplicationContext(), "asf", Toast.LENGTH_SHORT).show();

                customAdapter.notifyDataSetChanged();
            }
        });

        customAdapter.SetOnLongClickListener(new CustomAdapter.OnLongClickListener() {
            @Override
            public void OnLongClick() {
                toolbar.setMaxHeight(toolbarHeight);
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
                DialogFragment dialogFragment = new CustomTimePicker();
                dialogFragment.show(getSupportFragmentManager()
                , "time picker");

              //  Alarms.addAlarm("1:32", "wed", "123", true);
              //  customAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Alarms.addAlarm(hourOfDay+ "h "+ minute + "min", "wed", "123", true);
        customAdapter.notifyDataSetChanged();
    }



}
