package com.example.dima.currentinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Dima on 13.11.2017.
 */
public class InfoListFragment extends Fragment {
    private RecyclerView mInfoRecyclerView;
    private InfoAdapter mAdapter;
    private int mCurrentPosition;
    private boolean mSubtitleVisible;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_list, container, false);
        mInfoRecyclerView = (RecyclerView) v.findViewById(R.id.info_recycler_view);
        mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_item_new_info:
                Info info = new Info();
                InfoLab.get(getActivity()).addInfo(info);
                Intent intent = InfoPagerActivity.newIntent(getActivity(), info.getId());
                startActivity(intent);
                return true;
//            case R.id.menu_item_show_subtitle:
//                updateSubtitle();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateSubtitle()
    {
        InfoLab infoLab = InfoLab.get(getActivity());
        int infoCount  = infoLab.getInfoList().size();
        String subtitle = getString(R.string.subtitle_format, infoCount);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    private void updateUI() {
        InfoLab infoLab = InfoLab.get(getActivity());
        List<Info> infoList = infoLab.getInfoList();

        if (mAdapter == null) {
            mAdapter = new InfoAdapter(infoList);
            mInfoRecyclerView.setAdapter(mAdapter);
        } else {
//          TODO: Change this for optimize
            mAdapter.notifyDataSetChanged(); // update all position
//          mAdapter.notifyItemChanged(mCurrentPosition); // update current position (is bug)
        }
        updateSubtitle();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_info_list, menu);
//        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
//        if(mSubtitleVisible)
//        {
//            subtitleItem.setTitle(R.string.hide_subtitle);
//        }else{
//            subtitleItem.setTitle(R.string.show_subtitle);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSentCheckBox;
        private Info mInfo;


        public InfoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.card_list_item_info_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.card_list_item_info_date_text_view);
            mSentCheckBox = (CheckBox) itemView.findViewById(R.id.card_list_item_info_sent_check_box);

        }

        public void bindInfo(Info info) {
            mInfo = info;
            mTitleTextView.setText(mInfo.getTitle());
            mDateTextView.setText(mInfo.getSimpleDate());
            mSentCheckBox.setChecked(mInfo.isSent());
            mSentCheckBox.setText(R.string.was_sent);
            mSentCheckBox.setEnabled(false);
        }

        @Override
        public void onClick(View v) {
            Intent intent = InfoPagerActivity.newIntent(getActivity(), mInfo.getId());
            startActivity(intent);
            mCurrentPosition = getAdapterPosition();
//            Toast.makeText(getActivity(),mInfo.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class InfoAdapter extends RecyclerView.Adapter<InfoHolder> {
        private List<Info> mInfoList;

        public InfoAdapter(List<Info> infoList) {
            mInfoList = infoList;
        }

        @Override
        public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.card_list_item_info, parent, false);
            return new InfoHolder(view);
        }

        @Override
        public void onBindViewHolder(InfoHolder holder, int position) {
            Info info = mInfoList.get(position);
            holder.bindInfo(info);
        }

        @Override
        public int getItemCount() {
            return mInfoList.size();
        }


    }
}
