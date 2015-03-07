package edu.washington.prathh.change;

import android.app.Application;
import android.util.Log;

/**
 * Created by hillaryprather on 3/7/15.
 */
public class ChangeApp extends Application {
    private String accessToken;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("ChangeApp", "Application created");
        this.accessToken = "";
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String token) {
        Log.i("ChangeApp", "Access token set to: " + token);
        this.accessToken = token;
    }
}
