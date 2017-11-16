package com.example.dima.currentinfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class InfoFragment extends Fragment {
    private static final String ARG_INFO_ID = "info_id";
    private Info mInfo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSentCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID) getActivity().getIntent().getSerializableExtra(InfoActivity.EXTRA_INFO_ID);

        mInfo = InfoLab.get(getActivity()).getInfo(uuid);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info,container,false);

        mTitleField = (EditText) v.findViewById(R.id.info_title);
        mTitleField.setText(mInfo.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mInfo.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.info_date);
        mDateButton.setText(mInfo.getDate());
        mDateButton.setEnabled(false);

        mSentCheckBox = (CheckBox) v.findViewById(R.id.info_sent);
        mSentCheckBox.setChecked(mInfo.isSent());
        mSentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mInfo.setSent(b);
            }
        });
        return v;
    }
}
