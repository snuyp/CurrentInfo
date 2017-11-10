package com.example.dima.currentinfo;

import java.util.UUID;

/**
 * Created by Dima on 10.11.2017.
 */

public class Info {
    private UUID mId;
    private String mTitle;

    public Info()
    {
        mId = UUID.randomUUID();
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
