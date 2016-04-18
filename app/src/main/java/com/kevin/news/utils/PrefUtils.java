package com.kevin.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin on 2016/4/4.
 */
public class PrefUtils {
    public static final String PREF_NAME = "config";

    public static boolean getBoolen(Context ctx, String key, boolean deafultValue) {

        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(key, deafultValue);
    }

    public static void setBloolen(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
