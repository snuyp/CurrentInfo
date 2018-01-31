package com.example.dima.currentinfo;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class InfoFragment extends Fragment {
    private static final String ARG_INFO_ID = "info_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private Info mInfo;
    private Button mDateButton;
    private CheckBox mSentCheckBox;
    private ImageView mPhotoView;
    private File mPhotoFile;


    public static InfoFragment newInstance(UUID infoId) {
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
        mPhotoFile = InfoLab.get(getActivity()).getPhotoFile(mInfo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        EditText titleField = v.findViewById(R.id.info_title);
        titleField.setText(mInfo.getTitle());
        titleField.addTextChangedListener(new TextWatcher() {
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

        mDateButton = v.findViewById(R.id.info_date);
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

        mSentCheckBox = v.findViewById(R.id.info_sent);
        mSentCheckBox.setChecked(mInfo.isSent());
        mSentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mInfo.setSent(b);
            }
        });
        Button deleteButton = v.findViewById(R.id.info_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoLab.get(getActivity()).deleteInfo(mInfo.getId());
                getActivity().finish();
            }
        });
        Button reportButton = v.findViewById(R.id.info_report);
        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getDataReport());
//                i = Intent.createChooser(i,)
//                i.setType("image/png");
                startActivity(i);
            }
        });

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(getContext(),
                getContext().getApplicationContext().getPackageName() + ".provider", mPhotoFile);
        //Uri uri = Uri.fromFile(mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);


        mPhotoView = v.findViewById(R.id.photo_ImageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v);
                return true;
            }
        });
        updatePhotoView();
        return v;
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.photo_popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.to_increase_photo:
                        FragmentManager fragment = getFragmentManager();
                        PhotoViewFragment photoView = PhotoViewFragment.newInstance(mInfo);
                        photoView.setTargetFragment(InfoFragment.this, REQUEST_PHOTO);
                        photoView.show(fragment, ARG_INFO_ID);
                }
                return true;
            }
        });
        popupMenu.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mInfo.setDate(date);
            mDateButton.setText(mInfo.getSimpleDate());
        } else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        InfoLab.get(getActivity()).updateInfo(mInfo);
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
          Glide.with(this)
                    .load(mPhotoFile)
                    .into(mPhotoView);

            //mPhotoView.setImageBitmap(bitmap);
//
        }

    }

    private String getDataReport() {
//        TODO
        String stringReport = mInfo.getTitle() + " " + mInfo.getSimpleDate();
        mSentCheckBox.setChecked(true);
        return stringReport;
    }
}
