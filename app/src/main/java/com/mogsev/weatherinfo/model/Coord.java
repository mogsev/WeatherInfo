package com.mogsev.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class Coord {
    private static final String TAG = Coord.class.getSimpleName();

    public static final String BUNDLE_NAME = Coord.class.getSimpleName();
    public static final String LONGITUDE = "lon";
    public static final String LATITUDE = "lat";

    @Expose
    @SerializedName(LATITUDE)
    private Double latitude;

    @Expose
    @SerializedName(LONGITUDE)
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
