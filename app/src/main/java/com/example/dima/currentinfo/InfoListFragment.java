package com.example.dima.currentinfo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by Dima on 13.11.2017.
 */
public class InfoListFragment extends Fragment {
    private RecyclerView mInfoRecyclerView;
    private InfoAdapter mAdapter;
    private int mCurrentPosition;

    //    private boolean mSubtitleVisible;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_list, container, false);
        mInfoRecyclerView = v.findViewById(R.id.info_recycler_view);
        mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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

    private void updateSubtitle() {
        InfoLab infoLab = InfoLab.get(getActivity());
        int infoCount = infoLab.getInfoList().size();
        @SuppressLint("StringFormatMatches") String subtitle = getString(R.string.subtitle_format, infoCount);

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
            mAdapter.setInfoList(infoList);
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

    private class InfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSentCheckBox;
        private Info mInfo;


        public InfoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.card_list_item_info_title_text_view);
            mDateTextView = itemView.findViewById(R.id.card_list_item_info_date_text_view);
            mSentCheckBox = itemView.findViewById(R.id.card_list_item_info_sent_check_box);
        }

        public void bindInfo(Info info) {
            mInfo = info;
            mTitleTextView.setText(mInfo.getTitle());
            mDateTextView.setText(mInfo.getSimpleDate());
            updateSentCheckBox();


        }
        private void updateSentCheckBox() {
            mSentCheckBox.setEnabled(false);
            mSentCheckBox.setChecked(mInfo.isSent());
            if (mInfo.isSent()) {
                mSentCheckBox.setText(R.string.was_sent);
            } else {
                mSentCheckBox.setText(R.string.not_sent);
            }
        }

        @Override
        public void onClick(View v) {
            Intent intent = InfoPagerActivity.newIntent(getActivity(), mInfo.getId());
            startActivity(intent);
//            mCurrentPosition = getAdapterPosition();
        }


        @Override
        public boolean onLongClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.sure_delete)
                    .setCancelable(true)
                    .setPositiveButton(R.string.info_delete,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    InfoLab.get(getActivity()).deleteInfo(mInfo.getId());
                                    updateUI();
                                    dialog.cancel();
                                    Toast.makeText(getActivity(),R.string.info_delete, Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setNegativeButton(R.string.return_dialog,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
    }
    private class InfoAdapter extends RecyclerView.Adapter<InfoHolder>  {
        private List<Info> mInfoList;

        public void setInfoList(List<Info> infoList) {
            mInfoList = infoList;
        }

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
