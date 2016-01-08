package com.razor.transit.wrappers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.razor.transit.helpers.LogHelper;

public class ApplicationPreferences {
    private static final String APP_SHARED_PREFS = "com.razor.transit.wrappers.preferences";
    private static final String LOG_TAG = "ApplicationPreferences";
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;

    private static String getApplicationPreferenceName() {
        return APP_SHARED_PREFS;
    }

    public ApplicationPreferences(final Context context)
    {
        String preferenceKey = getApplicationPreferenceName();
        LogHelper.i(LOG_TAG, "create preferences with key %s", preferenceKey);
        this.appSharedPrefs = context.getSharedPreferences(preferenceKey, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getPreference(final String key, final String defaultValue) {
        LogHelper.i(LOG_TAG,"get preferences %s",key);
        return appSharedPrefs.getString(key, defaultValue);
    }

    public void setPreference(final String key, final String value) {
        LogHelper.i(LOG_TAG,"set preferences %s with value %s",key,value);
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

}
