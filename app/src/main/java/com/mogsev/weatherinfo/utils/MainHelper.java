package com.mogsev.weatherinfo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public final class MainHelper {
    private static final String TAG = MainHelper.class.getSimpleName();

    public static final String METADATA_OPENWEATHERMAP_API_KEY = "com.openweathermap.API_KEY";

    @Nullable
    public static String getOpenWeatherMapApiKey(@NonNull final Context context) {
        Log.i(TAG, "getAppType");
        return getMetaData(context, METADATA_OPENWEATHERMAP_API_KEY);
    }

    @Nullable
    public static String getMetaData(@NonNull final Context context, @NonNull final String key) {
        Log.i(TAG, "getMetaData");
        if (context == null) {
            Log.e(TAG, "Context cannot be null");
            return null;
        }
        if (key == null) {
            Log.e(TAG, "Key cannot be null");
            return null;
        }
        final PackageManager packageManager = context.getPackageManager();
        String result = null;
        ApplicationInfo applicationInfo = null;
        Bundle bundle;
        bundle = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo != null) bundle = applicationInfo.metaData;
        if (bundle != null) result = bundle.getString(key);

        return result;
    }

}
