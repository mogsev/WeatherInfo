package com.mogsev.weatherinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.mogsev.weatherinfo.model.Sys;
import com.mogsev.weatherinfo.model.Weather;
import com.mogsev.weatherinfo.model.WeatherInfo;
import com.mogsev.weatherinfo.network.Network;
import com.mogsev.weatherinfo.openweathermap.WeatherInfoSearch;
import com.mogsev.weatherinfo.utils.DateHelper;
import com.mogsev.weatherinfo.utils.MainHelper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Eugene Sikaylo (mogsev@gmail.com)
 */
public class MainActivity extends AppCompatActivity implements WeatherInfoSearch, LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String BUNDLE_NAME_QUERY_MAP = "query_map";
    public static final int PLACE_PICKER_FLAG = 10001;

    // weather info card
    private TextView mTextViewCity;
    private TextView mTextViewDate;
    private TextView mTextViewWind;
    private TextView mTextViewWeather;
    private TextView mTextViewSunrise;
    private TextView mTextViewSunset;

    // weather search
    private ImageView mImageViewSearch;
    private EditText mEditTextCity;
    private ProgressBar mProgressBar;
    private TextView mTextViewNoResult;
    private FloatingActionButton mFabChoicePlace;

    private boolean isSearching = false;

    private Map<String, String> mQueryMap;

    private LocationManager mLocationManager;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initView();

        if (savedInstanceState != null) {
            mQueryMap = (HashMap) savedInstanceState.getSerializable(BUNDLE_NAME_QUERY_MAP);
            if (mQueryMap != null && !mQueryMap.isEmpty()) {
                onCall(mQueryMap);
            }
        } else {
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(mLocation != null) {
                Log.i(TAG, "Location: " + mLocation.getLatitude() + " / " + mLocation.getLongitude());
                takeWeatherInfo(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
            } else {
                Log.i(TAG, "Last location is empty");
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 30, this);
            }
        }

        mImageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSearching) {
                    if (TextUtils.isEmpty(mEditTextCity.getText().toString())) {
                        Toast.makeText(MainActivity.this, getString(R.string.toast_warning_weather_info_search_city_is_empty), Toast.LENGTH_SHORT);
                        mTextViewNoResult.setVisibility(View.VISIBLE);
                    } else {
                        mTextViewNoResult.setVisibility(View.GONE);
                        takeWeatherInfo(mEditTextCity.getText().toString());
                    }
                }
            }
        });

        mFabChoicePlace = (FloatingActionButton) findViewById(R.id.fabChoicePlace);
        mFabChoicePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_FLAG);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "GooglePlayServicesError: " + e.getMessage());
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.google_play_service_error), Toast.LENGTH_LONG).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "GooglePlayServicesNotAvailable: " + e.getMessage());
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.google_play_service_error), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BUNDLE_NAME_QUERY_MAP, (HashMap) mQueryMap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PLACE_PICKER_FLAG:
                    final Place place = PlacePicker.getPlace(MainActivity.this, data);
                    if (place != null) {
                        Log.i(TAG, "Place: " + place.getName() + " / " + place.getAddress() + " / " + place.getLatLng());
                        takeWeatherInfo(place.getLatLng());
                    }
                    break;
            }
        }
    }

    private void initView() {
        mTextViewCity = (TextView) findViewById(R.id.tvCity);
        mTextViewDate = (TextView) findViewById(R.id.tvTime);
        mTextViewWind = (TextView) findViewById(R.id.tvWind);
        mTextViewWeather = (TextView) findViewById(R.id.tvWeather);
        mTextViewSunrise = (TextView) findViewById(R.id.tvSunrise);
        mTextViewSunset = (TextView) findViewById(R.id.tvSunset);
        mImageViewSearch = (ImageView) findViewById(R.id.ivSearch);
        mEditTextCity = (EditText) findViewById(R.id.etCity);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextViewNoResult = (TextView) findViewById(R.id.tvNoResult);
        mFabChoicePlace = (FloatingActionButton) findViewById(R.id.fabChoicePlace);
    }

    @Override
    public void takeWeatherInfo(String city) {
        mProgressBar.setVisibility(View.VISIBLE);
        if (mQueryMap == null) {
            mQueryMap = new HashMap<>();
        } else {
            mQueryMap.clear();
        }
        mQueryMap.put("q", city);
        mQueryMap.put("appid", MainHelper.getOpenWeatherMapApiKey(this));
        onCall(mQueryMap);
    }

    @Override
    public void takeWeatherInfo(@Nullable LatLng latLng) {
        if (latLng == null) {
            Toast.makeText(MainActivity.this, getString(R.string.toast_warning_weather_info_search_city_is_empty), Toast.LENGTH_SHORT);
            return;
        }
        if (mTextViewNoResult.getVisibility() == View.VISIBLE) {
            mTextViewNoResult.setVisibility(View.GONE);
        }
        if (mQueryMap == null) {
            mQueryMap = new HashMap<>();
        } else {
            mQueryMap.clear();
        }
        mQueryMap.put("lat", String.valueOf(latLng.latitude));
        mQueryMap.put("lon", String.valueOf(latLng.longitude));
        mQueryMap.put("appid", MainHelper.getOpenWeatherMapApiKey(this));
        onCall(mQueryMap);
    }

    @Override
    public void onCall(Map<String, String> queryMap) {
        if (queryMap == null && queryMap.isEmpty()) {
            throw new IllegalArgumentException("queryMap cannot be null or empty");
        }
        Call<WeatherInfo> call = Network.API_OPENWEATHERMAP.getWeatherInfo(queryMap);
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Response is successful: " + response.body());
                    showWeatherInfo(response.body());
                    mTextViewNoResult.setVisibility(View.GONE);
                } else {
                    Network.showResponseErrorBody(TAG, response);
                    mTextViewNoResult.setVisibility(View.VISIBLE);
                }
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                if (mProgressBar != null) mProgressBar.setVisibility(View.GONE);
                if (mTextViewNoResult != null) mTextViewNoResult.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        // empty
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // empty
    }

    @Override
    public void onProviderEnabled(String provider) {
        // empty
    }

    @Override
    public void onProviderDisabled(String provider) {
        // empty
    }

    public void showWeatherInfo(WeatherInfo weatherInfo) {
        mTextViewCity.setText(getString(R.string.card_weather_info_city, weatherInfo.getName()));
        mTextViewDate.setText(getString(R.string.card_weather_info_date, DateHelper.convertTimestamp(weatherInfo.getDt())));
        mTextViewWind.setText(getString(R.string.card_weather_info_wind, weatherInfo.getWind().getSpeed(), weatherInfo.getWind().getDeg()));
        Weather weather = weatherInfo.getWeather().get(0);
        if (weather != null) {
            mTextViewWeather.setText(getString(R.string.card_weather_info_weather, weather.getMain(), weather.getDescription()));
        }
        Sys sys = weatherInfo.getSys();
        if (sys != null) {
            mTextViewSunrise.setText(getString(R.string.card_weather_info_sunrise, DateHelper.convertTimestamp(sys.getSunrise(), DateHelper.DATE_FORMAT_DDMMYYYYHHMM)));
            mTextViewSunset.setText(getString(R.string.card_weather_info_sunset, DateHelper.convertTimestamp(sys.getSunset(), DateHelper.DATE_FORMAT_DDMMYYYYHHMM)));
        }
    }
}
