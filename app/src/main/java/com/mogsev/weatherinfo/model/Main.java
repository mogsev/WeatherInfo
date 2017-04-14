package com.mogsev.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class Main {

    @Expose
    @SerializedName("temp")
    private Double temp;

    @Expose
    @SerializedName("pressure")
    private Double pressure;

    @Expose
    @SerializedName("humidity")
    private int humidity;

    @Expose
    @SerializedName("temp_min")
    private Double tempMin;

    @Expose
    @SerializedName("temp_max")
    private Double tempMax;

    @Expose
    @SerializedName("sea_level")
    private Double seaLevel;

    @Expose
    @SerializedName("grnd_level")
    private Double grndLevel;

    public Double getTemp() {
        return temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public Double getSeaLevel() {
        return seaLevel;
    }

    public Double getGrndLevel() {
        return grndLevel;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                '}';
    }
}
