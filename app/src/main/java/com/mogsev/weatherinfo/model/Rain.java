package com.mogsev.weatherinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class Rain {

    @Expose
    @SerializedName("3h")
    private Double _3h;

    public Double get3h() {
        return _3h;
    }
}
