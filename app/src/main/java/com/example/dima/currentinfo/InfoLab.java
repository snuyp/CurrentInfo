package com.example.dima.currentinfo;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dima on 13.11.2017.
 */
public class InfoLab {

    private static InfoLab sInfoLab;
    private List<Info> mInfoList;

    public static InfoLab get(Context context) {
        if (sInfoLab == null) {
            sInfoLab = new InfoLab(context);
        }
        return sInfoLab;
    }

    private InfoLab(Context context) {
        mInfoList = new ArrayList<>();
    }

    public List<Info> getInfoList() {
        return mInfoList;
    }

    public Info getInfo(UUID id) {
        for (Info info : mInfoList) {
            if (info.getId().equals(id)) {
                return info;
            }
        }
        return null;
    }
    public void addInfo(Info info)
    {
        mInfoList.add(info);
    }
    public void deleteInfo(Info info)
    {
        mInfoList.remove(info);
    }
}
