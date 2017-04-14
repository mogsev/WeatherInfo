package com.mogsev.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class Sys {

    @Expose
    @SerializedName("message")
    private Double message;

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("sunrise")
    private Integer sunrise;

    @Expose
    @SerializedName("sunset")
    private Integer sunset;

    public Double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public Integer getSunrise() {
        return sunrise;
    }

    public Integer getSunset() {
        return sunset;
    }

    @Override
    public String toString() {
        return "Sys{" +
                "message=" + message +
                ", country='" + country + '\'' +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}
