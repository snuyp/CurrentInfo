package com.example.dima.currentinfo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class Info implements Serializable{
    private UUID mId;
    private String mTitle;
    private Double mTemp;
    private Double latitude;
    private Double longitude;
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

    public Double getTemp() {
        return mTemp;
    }

    public void setTemp(Double temp) {
        mTemp = temp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
