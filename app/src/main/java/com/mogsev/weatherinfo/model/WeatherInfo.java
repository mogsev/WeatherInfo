package com.mogsev.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class WeatherInfo {
    private static final String TAG = WeatherInfo.class.getSimpleName();

    public static final String BUNDLE_NAME = WeatherInfo.class.getSimpleName();

    @Expose
    @SerializedName("coord")
    private Coord coord;

    @Expose
    @SerializedName("weather")
    private List<Weather> weather;

    @Expose
    @SerializedName("base")
    private String base;

    @Expose
    @SerializedName("main")
    private Main main;

    @Expose
    @SerializedName("wind")
    private Wind wind;

    @Expose
    @SerializedName("rain")
    private Rain rain;

    @Expose
    @SerializedName("clouds")
    private Clouds clouds;

    @Expose
    @SerializedName("dt")
    private long dt;

    @Expose
    @SerializedName("sys")
    private Sys sys;

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("cod")
    private Integer cod;

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", wind=" + wind +
                ", rain=" + rain +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }
}
