package com.example.dima.currentinfo;

import android.support.v4.app.Fragment;

/**
 * Created by Dima on 13.11.2017.
 */

public class InfoListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new InfoListFragment();
    }
}
