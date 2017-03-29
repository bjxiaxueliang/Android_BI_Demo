package com.test.bi.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class BiSPUtil {

    private final String TAG = "BiSPUtil";

    // file name
    private static final String PREFERENCES_FILE_NAME = "bi_sp";

    // instance
    private static BiSPUtil INSTANCE = new BiSPUtil();;

    // shared preference
    private SharedPreferences sp;
    // shared preference editor
    private SharedPreferences.Editor editor;

    // is initialized
    private boolean initialized = false;

    public static final synchronized BiSPUtil getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        this.sp = context.getSharedPreferences(PREFERENCES_FILE_NAME,
                Context.MODE_PRIVATE);
        this.editor = sp.edit();
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Push string value
     *
     * @param key
     * @param value
     */
    public void putStringValue(String key, String value) {
        BiLogUtils.d(TAG, "--------putStringValue----------");
        BiLogUtils.d(TAG, "key: " + key);
        BiLogUtils.d(TAG, "value: " + value);

        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Get string value by key
     *
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public String getStringValue(String key, String defaultValue) {
        BiLogUtils.d(TAG, "key: " + key);
        return sp.getString(key, defaultValue);
    }

    /**
     * Push integer value
     *
     * @param key
     * @param value
     */
    public void putIntValue(String key, int value) {
        editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * Get integer value by key
     *
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public int getIntValue(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    /**
     * Push long value
     *
     * @param key
     * @param value
     */
    public void putLongValue(String key, long value) {
        editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Get long value by key
     *
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public long getLongValue(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    /**
     * Push boolean value
     *
     * @param key
     * @param value
     */
    public void putBooleanValue(String key, boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     *
     * @param key
     */
    public void remove(String key) {

        BiLogUtils.d(TAG, "--------remove----------");
        BiLogUtils.d(TAG, "key: " + key);

        editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     *
     */
    public void clear() {
        editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Get boolean value by key
     *
     * @param key
     * @param defaultValue
     *
     * @return
     */
    public boolean getBooleanValue(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }
}