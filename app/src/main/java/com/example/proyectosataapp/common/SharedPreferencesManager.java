package com.example.proyectosataapp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {

    private static final String APP_SETTINGS_FILE = "APP_SETTINGS";
    private Context ctx;
    private SharedPreferencesManager(Context ctx){}

    private static SharedPreferences getSharedPreferences(){
        return MyApp.getCtx().getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
    }

    public static String getStringValue(String dataLabel){
        return getSharedPreferences().getString(dataLabel,null);
    }

    public static void removeStringValue(String dataLabel){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.remove(dataLabel);
        editor.commit();
    }
}
