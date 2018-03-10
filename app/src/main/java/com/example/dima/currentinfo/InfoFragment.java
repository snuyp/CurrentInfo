package com.example.dima.currentinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.example.dima.currentinfo.common.Common;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileReader;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.sure_delete)
                        .setCancelable(true)
                        .setPositiveButton(R.string.info_delete,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        InfoLab.get(getActivity()).deleteInfo(mInfo.getId());
                                        dialog.cancel();
                                        getActivity().finish();
                                        Toast.makeText(getActivity(), R.string.info_delete, Toast.LENGTH_SHORT).show();
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
//            case R.id.menu_item_show_subtitle:
//                updateSubtitle();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_info, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID) getArguments().getSerializable(ARG_INFO_ID);
        mInfo = InfoLab.get(getActivity()).getInfo(uuid);
        mPhotoFile = InfoLab.get(getActivity()).getPhotoFile(mInfo);
        setHasOptionsMenu(true);
    }

    private void setCoordinates() {
        if (mInfo.getLatitude() == 0.0 && mInfo.getLongitude() == 0.0) {
            mInfo.setLatitude(Common.lastLocation.getLatitude());
            mInfo.setLongitude(Common.lastLocation.getLongitude());
        }
    }

    private void updateCoordinates() {
        mInfo.setLatitude(Common.lastLocation.getLatitude());
        mInfo.setLongitude(Common.lastLocation.getLongitude());
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
//        PhotoView photoView = v.findViewById(R.id.photo_ImageView);
//        photoView.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                //toIncreasePhoto();
//                return true;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                return false;
//            }
//        });
        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v);
                return true;
            }
        });

        updatePhotoView();

        setCoordinates();
        return v;
    }

    private void showPopupMenu(final View v) {
        final PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.photo_popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.to_increase_photo:
                        toIncreasePhoto();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();

    }

    public void toIncreasePhoto() {
        FragmentManager fragment = getFragmentManager();
        PhotoViewFragment photoView = PhotoViewFragment.newInstance(mInfo);
        photoView.setTargetFragment(InfoFragment.this, REQUEST_PHOTO);
        photoView.show(fragment, ARG_INFO_ID);
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
