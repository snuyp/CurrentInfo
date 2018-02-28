package com.example.dima.currentinfo;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dima.currentinfo.weather.WeatherFragment;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Dima on 13.11.2017.
 */

public class InfoListActivity extends SingleFragmentActivity {
    private static final String TAG = "INFO_LIST";
    private String[] mTitles;
    private ListView mDrawerList;
    private int mPosition = 0;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected Fragment createFragment() {
        return new InfoListFragment();
    }

    private void getLocationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Tracker.REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        return isGranted(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)) &&
                isGranted(ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION));
    }

    private boolean isGranted(int permission) {
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public void getLocation() {
        if (!checkPermission()) {
            getLocationPermissions();
        } else {
            //TODO
            // without it not working (in first time, in mapsFragment)
            //GpsTracker working only second time
            Tracker.get(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocation();
        mTitles = getResources().getStringArray(R.array.titles);
        mDrawerList = findViewById(R.id.left_drawer);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt("position");
            setActionBarTitle(mPosition);
        } else {
            selectItem(0);
        }

        mDrawerList.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_activated_1, mTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", mPosition);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mPosition = position;
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new InfoListFragment();
                break;
            case 1:
                fragment = new WeatherFragment();
                break;
            case 2:


                fragment = new MapsFragment();
                break;
            default:
                fragment = null;
        }
        FragmentManager ft = getSupportFragmentManager();
        ft.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        setActionBarTitle(position);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(mDrawerList);
    }

    private void setActionBarTitle(int position) {
        String title = null;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        }
        if (position == 1) {
            title = mTitles[position];
        }
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
