package com.example.dima.currentinfo.weather;


import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.dima.currentinfo.R;


/**
 * Created by Dima on 05.02.2018.
 */

public class WeatherWebPageFragment extends Fragment {
    public final static String URI_WEATHER_MAP = "https://openweathermap.org/city/";
    private ProgressBar mProgressBar;

    public static WeatherWebPageFragment newInstance() {
        return new WeatherWebPageFragment();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_web_page, container, false);
        mProgressBar = v.findViewById(R.id.fragment_page_progress_bar);
        mProgressBar.setMax(100);
        WebView webView = v.findViewById(R.id.fragment_page_web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                assert activity != null;
                activity.getSupportActionBar().setSubtitle(title);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.loadUrl(URI_WEATHER_MAP);
        return v;
    }
}
