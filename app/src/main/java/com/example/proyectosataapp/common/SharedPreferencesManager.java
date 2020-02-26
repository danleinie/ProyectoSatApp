package com.example.proyectosataapp.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharePreferences() {

        SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void setSomeStringValue(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharePreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.commit();

    }

    public static String getSomeStringValue(String dataLabel) {

        return getSharePreferences().getString(dataLabel, null);

    }

}
