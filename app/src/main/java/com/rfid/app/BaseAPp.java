package com.rfid.app;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by sly on 2016/5/18.
 */
public class BaseApp extends Application {
    private static BaseApp instance;

    private static int mNum = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApp instance() {

        return instance;
    }
}
