package com.example.dima.currentinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class InfoFragment extends Fragment {
    private static final String ARG_INFO_ID = "info_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Info mInfo;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSentCheckBox;
    private Button mDeleteButton;

    public static InfoFragment newInstance (UUID infoId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_INFO_ID, infoId);

        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(args);
        return infoFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID) getArguments().getSerializable(ARG_INFO_ID);

        mInfo = InfoLab.get(getActivity()).getInfo(uuid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
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
        mDateButton.setText(mInfo.getSimpleDate());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragment = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mInfo.getDate());
                dialog.setTargetFragment(InfoFragment.this, REQUEST_DATE);
                dialog.show(fragment, DIALOG_DATE);
            }
        });

        mSentCheckBox = (CheckBox) v.findViewById(R.id.info_sent);
        mSentCheckBox.setChecked(mInfo.isSent());
        mSentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mInfo.setSent(b);
            }
        });
        mDeleteButton = (Button) v.findViewById(R.id.info_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoLab.get(getActivity()).deleteInfo(mInfo);
                getActivity().finish();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if(requestCode == REQUEST_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mInfo.setDate(date);
            mDateButton.setText(mInfo.getSimpleDate());
        }
    }
}
