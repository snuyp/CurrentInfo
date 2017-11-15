package com.example.dima.currentinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_list, container, false);
        mInfoRecyclerView = (RecyclerView) v.findViewById(R.id.info_recycler_view);
        mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        InfoLab infoLab = InfoLab.get(getActivity());
        List<Info> infoList = infoLab.getInfoList();
        mAdapter = new InfoAdapter(infoList);
        mInfoRecyclerView.setAdapter(mAdapter);
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
        public void bindInfo(Info info)
        {
            mInfo = info;
            mTitleTextView.setText(mInfo.getTitle());
            mDateTextView.setText(mInfo.getDate());
            mSentCheckBox.setChecked(mInfo.isSent());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),mInfo.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
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
