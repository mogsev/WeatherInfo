package com.mogsev.weatherinfo.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class DateHelper {
    private static final String TAG = DateHelper.class.getSimpleName();

    public static final String DATE_FORMAT_DDMMYYYY = "dd.MM.yyyy";
    public static final String DATE_FORMAT_DDMMYYYYHHMM = "dd.MM.yyyy HH:mm";

    /**
     * @return String date formate "dd.mm.yyyy"
     */
    public static String convertTimestamp(@NonNull long timestamp) {
        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_DDMMYYYY);
        return format.format(date);
    }

    public static String convertTimestamp(@NonNull long timestamp, String dateFormat) {
        Date date = new Date(timestamp * 1000L);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }
}
