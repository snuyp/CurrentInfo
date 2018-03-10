package com.example.dima.currentinfo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dima.currentinfo.database.InfoDbSchema.InfoTable;

/**
 * Created by Dima on 26.11.2017.
 */

public class InfoBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "infoBase.db";

    public InfoBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ InfoTable.NAME + "("+
        "_id integer primary key autoincrement, " +
                InfoTable.Cols.UUID + ", " +
                InfoTable.Cols.TITLE + ", " +
                InfoTable.Cols.DATE + ", " +
                InfoTable.Cols.TEMP + ", " +
                InfoTable.Cols.LATITUDE + ", " +
                InfoTable.Cols.LONGITUDE + ", " +
                InfoTable.Cols.SENT +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
