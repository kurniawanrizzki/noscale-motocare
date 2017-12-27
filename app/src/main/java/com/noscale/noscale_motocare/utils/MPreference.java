package com.noscale.noscale_motocare.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class MPreference {

    private static MPreference instance;
    private static SharedPreferences preferences;

    public static MPreference getInstance (Context context) {
        if (null == instance) {
            instance = new MPreference();
            preferences = context.getSharedPreferences(
                    context.getPackageName(),
                    Context.MODE_PRIVATE
            );
        }

        return instance;
    }

    public void putStringToMPreference (String value, String tag) {
        preferences.edit().putString(tag, value).commit();
    }

    public String getStringFromMPreference (String tag, String defValue) {
        return preferences.getString(tag, defValue);
    }

    public void putIntToMPreference (int value, String tag) {
        preferences.edit().putInt(tag, value).commit();
    }

    public int getIntFromMPreference (String tag, int defValue) {
        return preferences.getInt(tag, defValue);
    }

    public void putBooleanToMPreference (boolean value, String tag) {
        preferences.edit().putBoolean(tag, value).commit();
    }

    public boolean getBooleanFromMPreference (String tag, boolean defValue) {
        return preferences.getBoolean(tag, defValue);
    }

}
