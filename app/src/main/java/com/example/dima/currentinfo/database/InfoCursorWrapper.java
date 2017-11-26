package com.example.dima.currentinfo.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.dima.currentinfo.Info;
import com.example.dima.currentinfo.database.InfoDbSchema.InfoTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Dima on 26.11.2017.
 */

public class InfoCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public InfoCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Info getInfo()
    {
        String uuidString = getString(getColumnIndex(InfoTable.Cols.UUID));
        String title = getString(getColumnIndex(InfoTable.Cols.TITLE));
        long date = getLong(getColumnIndex(InfoTable.Cols.DATE));
        int isSent = getInt(getColumnIndex(InfoTable.Cols.SENT));

        Info info = new Info (UUID.fromString(uuidString));
        info.setTitle(title);
        info.setDate(new Date(date));
        info.setSent(isSent != 0);
        return info;
    }
}
