package com.example.dima.currentinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class Info {
    private UUID mId;
    private String mTitle;
    private Date mDate = new Date();
    private boolean mSent;
    private SimpleDateFormat mSimpleDate;

    public Info()
    {
        this(UUID.randomUUID());
    }

    public Info(UUID id) {
        mId = id;
        mSimpleDate = new SimpleDateFormat("E dd.MM.yyyy '[' HH:mm:ss ']'" ,new Locale("en","US"));
    }

    public String getSimpleDate() {

        return mSimpleDate.format(mDate);
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
        mSimpleDate.format(date);
    }

    public boolean isSent() {
        return mSent;
    }

    public void setSent(boolean sent) {
        mSent = sent;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPhotoFilename()
    {
        return "IMG_" +getId().toString()+".jpg";
    }
}
