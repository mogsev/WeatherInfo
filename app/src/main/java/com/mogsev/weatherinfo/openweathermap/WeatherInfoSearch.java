package com.mogsev.weatherinfo.openweathermap;

import com.google.android.gms.maps.model.LatLng;
import com.mogsev.weatherinfo.model.WeatherInfo;

import java.util.Map;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public interface WeatherInfoSearch {

    public void takeWeatherInfo(String city);

    public void takeWeatherInfo(LatLng latLng);

    public void onCall(Map<String, String> queryMap);

    public void showWeatherInfo(WeatherInfo weatherInfo);

}
