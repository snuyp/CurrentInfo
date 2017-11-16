package com.example.dima.currentinfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;

public class InfoActivity extends SingleFragmentActivity{
    public static final String EXTRA_INFO_ID = "com.example.dima.currentinfo.info_id";

    public static Intent newIntent(Context packageContext, UUID infoId)
    {
        Intent intent = new Intent(packageContext,InfoActivity.class);
        intent.putExtra(EXTRA_INFO_ID, infoId);
        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return new InfoFragment();
    }
}
