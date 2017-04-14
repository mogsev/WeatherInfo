package com.mogsev.weatherinfo;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class WeatherInfo extends Application {
    private static final String TAG = WeatherInfo.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        // use vector drawables
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
