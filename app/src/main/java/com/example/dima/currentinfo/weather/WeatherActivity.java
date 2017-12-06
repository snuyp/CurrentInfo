package com.example.dima.currentinfo.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.dima.currentinfo.SingleFragmentActivity;

/**
 * Created by Dima on 04.12.2017.
 */

public class WeatherActivity  extends SingleFragmentActivity{

    public static Intent newIntent(Context context)
    {
        return new Intent(context, WeatherActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return WeatherFragment.newInstance();
    }
}
