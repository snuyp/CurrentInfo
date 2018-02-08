package com.example.dima.currentinfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by Dima on 19.11.2017.
 */

public class InfoPagerActivity extends AppCompatActivity {
    private static final String EXTRA_INFO_ID = "info_id";

    private List<Info> mInfoList;

    public static Intent newIntent(Context context, UUID infoId) {
        Intent intent = new Intent(context, InfoPagerActivity.class);
        intent.putExtra(EXTRA_INFO_ID, infoId);
        return intent;
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pager);

        UUID infoId = (UUID) getIntent().getSerializableExtra(EXTRA_INFO_ID);

        ViewPager viewPager = findViewById(R.id.activity_info_pager_view_pager);

        mInfoList = InfoLab.get(this).getInfoList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Info info = mInfoList.get(position);
                return InfoFragment.newInstance(info.getId());
            }

            @Override
            public int getCount() {
                return mInfoList.size();
            }

        });

        for (int i = 0; i < mInfoList.size(); i++) {
            if (mInfoList.get(i).getId().equals(infoId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
