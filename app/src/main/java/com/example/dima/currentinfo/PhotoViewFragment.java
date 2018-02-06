package com.example.dima.currentinfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

/**
 * Created by Dima on 30.01.2018.
 */

public class PhotoViewFragment extends DialogFragment
{
    private static final String ARG_INFO_ID = "info_id";

    public static PhotoViewFragment newInstance(Info info) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_INFO_ID, info);
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.large_photo_view, null);

        Info info = (Info) getArguments().getSerializable(ARG_INFO_ID);
        File photoFile = InfoLab.get(getActivity()).getPhotoFile(info);

        PhotoView photoView = v.findViewById(R.id.large_photo_view);
        photoView.setImageBitmap(BitmapFactory.decodeFile(photoFile.getPath()));

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                .create();
    }
}
