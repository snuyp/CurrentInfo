package com.example.dima.currentinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dima on 13.11.2017.
 */
public class InfoListFragment extends Fragment {
    private RecyclerView mInfoRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info_list,container,false);
        mInfoRecyclerView = (RecyclerView) v.findViewById(R.id.info_recycler_view);
        mInfoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }
    private class InfoHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public InfoHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
    }
    private class InfoAdapter extends RecyclerView.Adapter<InfoHolder>
    {
        private List<Info> mInfoList;

        public InfoAdapter(List<Info> infoList)
        {
            mInfoList = infoList;
        }
        @Override
        public InfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new InfoHolder(view);
        }

        @Override
        public void onBindViewHolder(InfoHolder holder, int position) {
            Info info = mInfoList.get(position);
            holder.mTitleTextView.setText(info.getTitle());
        }

        @Override
        public int getItemCount() {
            return mInfoList.size();
        }
    }
}
