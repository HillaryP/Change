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

    public double getChange() {
        double change = (double) prefs.getFloat("change", (float) 0.0);
        change = Math.round(change * 100) / 100.0;
        return change;
    }

    public void setChange(double change) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("change", (float) change);
        editor.apply();
    }

    public String getCharity() {
        return prefs.getString("charity", "GLAAD");
    }

    public void setCharity(String charity) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("charity", charity);
        editor.apply();
    }
}
