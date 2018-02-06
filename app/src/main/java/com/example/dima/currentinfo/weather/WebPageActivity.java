package com.example.dima.currentinfo.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.dima.currentinfo.SingleFragmentActivity;

public class WebPageActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context)
    {
        return new Intent(context, WebPageActivity.class);
    }
    @Override
    protected Fragment createFragment() {
        return WeatherWebPageFragment.newInstance();
    }
}
