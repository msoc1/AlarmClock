package com.fixed4fun.alarmclock.alarmObject;

import android.app.Application;
import android.content.Context;

public class ADObject extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ADObject.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ADObject.context;
    }
}
