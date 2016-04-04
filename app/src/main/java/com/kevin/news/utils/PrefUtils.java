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
}
