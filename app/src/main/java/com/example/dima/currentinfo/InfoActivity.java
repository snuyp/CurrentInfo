package com.example.dima.currentinfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class InfoActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new InfoFragment();
    }
}
