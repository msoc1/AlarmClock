package com.fixed4fun.alarmclock.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fixed4fun.alarmclock.R;
import com.fixed4fun.alarmclock.adapters.CustomAdapter;
import com.fixed4fun.alarmclock.alarmObject.ADObject;
import com.fixed4fun.alarmclock.alarmObject.AlarmData;
import com.fixed4fun.alarmclock.objectLists.AlarmList;
import com.fixed4fun.alarmclock.fragments.SettingsFragment;
import com.fixed4fun.alarmclock.notifications.AlarmNotifications;
import com.fixed4fun.alarmclock.fragments.ChangeAllTimePicker;
import com.fixed4fun.alarmclock.fragments.ModifyTimePicker;
import com.fixed4fun.alarmclock.fragments.NewTimePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    static int toolbarHeight;
    public static int position;

    public static boolean listState;


    static String toastMessage;
    public static ArrayList<AlarmData> alarms = new ArrayList<>();
    static ArrayList<AlarmData> tempList;
    @SuppressLint("StaticFieldLeak")
    static private CustomAdapter customAdapter;
    TimePicker timePicker;
    public static AlarmNotifications alarmNotifications;

    FloatingActionButton floatingActionButton;
    FloatingActionButton settingButton;

    RecyclerView recyclerView;
    SwitchCompat turnOnOrOffAll;
    CheckBox selectAll;
    Button deleteAll;
    Button changeAll;
    Toast notificationToast;
    private ConstraintLayout toolbar;

    private static final int PERMISION_REQUESTCODE = 456;
    public static final String XIAOMI_PERMISSION = "xiaomi";
    public static final int XIAOMI_REQUESTCODE = 7890;
    String[] PERMISSIONS_NEEDED = {Manifest.permission.WAKE_LOCK, Manifest.permission.SET_ALARM, Manifest.permission.DISABLE_KEYGUARD, Manifest.permission.RECEIVE_BOOT_COMPLETED,};


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
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        editor.remove("ALARMS");
        String json = gson.toJson(alarms);
        editor.putString("ALARMS", json);
        editor.apply();
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String json2 = sharedPrefs.getString("ALARMS", "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<AlarmData>>() {
        }.getType();
        ArrayList<AlarmData> arrayList = gson.fromJson(json2, type);
        alarms.clear();
        if (arrayList != null) {
            alarms.addAll(arrayList);
        }
        customAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(customAdapter);


    }

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarms = AlarmList.getAlarms();
        tempList = new ArrayList<>();
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
        settingButton = findViewById(R.id.settings);
        recyclerView = findViewById(R.id.recyclerView);
        TabLayout tableLayout = findViewById(R.id.tabLayout);
        //Toast will be shown later
        toastMessage = "";
        notificationToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        toolbarHeight = toolbar.getMaxHeight();
        toolbar.setVisibility(View.GONE);
        alarmNotifications = new AlarmNotifications();

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
        settingButton.setOnClickListener(this);


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
            selectAll.setChecked(false);
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
                alarmNotifications.startNotification(ADObject.getAppContext(), alarms);
                break;

            case R.id.change_all:
                ChangeAllTimePicker dialogFragment = new ChangeAllTimePicker();
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
                checkPermissionsAndAddAlarm();
                break;
            case R.id.settings:
                DialogFragment settingsFragment = new SettingsFragment();
                settingsFragment.show(getSupportFragmentManager(), "settings_fragment");
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
    protected void onDestroy() {
        super.onDestroy();
        Log.d("123456", "onDestroy: ");
        alarmNotifications.midnightAlarms(ADObject.getAppContext());
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

    @AfterPermissionGranted(PERMISION_REQUESTCODE)
    private void checkPermissionsAndAddAlarm() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean xiaomiPermission = sharedPrefs.getBoolean(XIAOMI_PERMISSION, false);
        if (isMiUi() && !xiaomiPermission) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alarm Information");
            builder.setMessage("Accept permissions for full functionallity");
            builder.setPositiveButton("Continue", (dialog, which) -> {
                //open system settings to get other permissions needed specifically for xioami phones
                startActivity(new Intent("miui.intent.action.APP_PERM_EDITOR").putExtra("extra_pkgname", getPackageName()));
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putBoolean(XIAOMI_PERMISSION, true);
                editor.apply();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            addAlarm();
        }
    }

    public void addAlarm() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS_NEEDED)) {
            DialogFragment timePickerFragment = new NewTimePicker();
            timePickerFragment.show(getSupportFragmentManager()
                    , "time picker");
            toolbar.setVisibility(View.GONE);
            listState = false;
            recyclerView.setAdapter(customAdapter);
        } else {
            EasyPermissions.requestPermissions(this, "Permissions needed for full functionallity",
                    PERMISION_REQUESTCODE, PERMISSIONS_NEEDED);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onClick(View v) {
        onClickListeners(v);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        checkPermissionsAndAddAlarm();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE || requestCode == XIAOMI_REQUESTCODE) {
            checkPermissionsAndAddAlarm();
        }
    }

    public static boolean isMiUi() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            java.lang.Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }
}
