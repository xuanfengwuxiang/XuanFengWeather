package com.xuanfeng.xflibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * sharepreference工具
 */
public class SpManager {


    private static final String FILE_NAME = "SpCache";
    private static volatile SpManager spManager;
    private SharedPreferences mSp;

    private SpManager(Context context) {

        mSp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static SpManager getInstance(Context context) {
        if (spManager == null) {

            synchronized (SpManager.class) {
                if (spManager == null) {
                    spManager = new SpManager(context.getApplicationContext());
                }
            }
        }
        return spManager;
    }


    public boolean setString(String key, String val) {
        return mSp.edit().putString(key, val).commit();
    }

    public String getString(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }

    public boolean setBoolean(String key, boolean val) {
        return mSp.edit().putBoolean(key, val).commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSp.getBoolean(key, defaultValue);
    }

    public boolean setFloat(String key, float val) {
        return mSp.edit().putFloat(key, val).commit();
    }

    public float getFloat(String key, float defaultValue) {
        return mSp.getFloat(key, defaultValue);
    }

    public boolean setInt(String key, int val) {
        return mSp.edit().putInt(key, val).commit();
    }

    public int getInt(String key, int defaultValue) {
        return mSp.getInt(key, defaultValue);
    }

    public boolean setLong(String key, long val) {
        return mSp.edit().putLong(key, val).commit();
    }

    public long getLong(String key, long defaultValue) {
        return mSp.getLong(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return mSp.getAll();
    }

    public void remove(String key) {
        mSp.edit().remove(key).commit();
    }

    public void clear() {
        mSp.edit().clear().commit();
    }
}
