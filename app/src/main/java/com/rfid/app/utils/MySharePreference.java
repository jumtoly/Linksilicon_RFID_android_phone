package com.rfid.app.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.rfid.app.BaseApp;

/**
 * Created by sly on 2016/5/18.
 */
public class MySharePreference {
    private static final String MY_SHARED_CACHE_FILE_KEY = "MY_SHARED_CACHE_FILE";


    public static void put(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    public static void put(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void put(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.commit();
    }

    public static String get(String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();

        return sharedPreferences.getString(key, defaultValue);
    }

    public static boolean get(String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();

        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static int get(String key, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();

        return sharedPreferences.getInt(key, defaultValue);
    }

    private static SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences = BaseApp.instance().getSharedPreferences(MY_SHARED_CACHE_FILE_KEY, Activity.MODE_PRIVATE);

        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditor() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        return editor;
    }

}
