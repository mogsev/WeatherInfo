package com.mogsev.weatherinfo.openweathermap;

import com.mogsev.weatherinfo.model.WeatherInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public interface ApiOpenWeatherMap {
    public static final String BASE_URL = "http://api.openweathermap.org";

    public static final String HEADER_ACCEPT_APP_JSON = "Accept: application/json";
    public static final String HEADER_CONTENT_TYPE_APP_JSON = "Content-type: application/json";

    @Headers({HEADER_ACCEPT_APP_JSON, HEADER_CONTENT_TYPE_APP_JSON})
    @GET("data/2.5/weather")
    Call<WeatherInfo> getWeatherInfo(@QueryMap Map<String, String> options);
}
