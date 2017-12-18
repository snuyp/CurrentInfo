package com.example.dima.currentinfo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Dima on 13.11.2017.
 */

public class InfoListActivity extends SingleFragmentActivity{
    private String[] mTitles;
    private ListView drawerList;
    @Override
    protected Fragment createFragment() {
        return new InfoListFragment();
    }

//    @SuppressLint("ResourceType")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        mTitles= getResources().getStringArray(R.id.left_drawer);
//        drawerList.setAdapter(new ArrayAdapter<String>(
//                this,android.R.layout.simple_list_item_activated_1,mTitles));
//        drawerList.setOnClickListener((View.OnClickListener) new DrawerItemClickListener());
//        super.onCreate(savedInstanceState);
//    }
//
//    private class DrawerItemClickListener implements ListView.OnItemClickListener
//    {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        }
//    }
}
