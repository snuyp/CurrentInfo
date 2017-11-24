package com.example.dima.currentinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class Info {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSent;
    private SimpleDateFormat mSimpleDate;

    public Info()
    {
        mId = UUID.randomUUID();
        mDate = new Date();
        mSimpleDate = new SimpleDateFormat("E dd.MM.yyyy '[' hh:mm:ss ']'" );
    }

    public String getSimpleDate() {

        return mSimpleDate.format(mDate);
    }
    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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
}
