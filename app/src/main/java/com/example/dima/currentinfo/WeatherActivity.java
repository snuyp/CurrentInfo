package com.example.dima.currentinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

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
