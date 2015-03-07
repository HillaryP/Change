package edu.washington.prathh.change;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by hillaryprather on 3/7/15.
 */
public class ChangeApp extends Application {
    public static final String PREFS_NAME = "MyPrefs";
    private SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("ChangeApp", "Application created");
    }

    public String getAccessToken() {
        return prefs.getString("accessToken", null);
    }

    public void setAccessToken(String token) {
        Log.i("ChangeApp", "Access token set to: " + token);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("accessToken", token);
        editor.apply();
    }
}
