package com.example.dima.currentinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.dima.currentinfo.database.InfoBaseHelper;
import com.example.dima.currentinfo.database.InfoCursorWrapper;
import com.example.dima.currentinfo.database.InfoDbSchema.InfoTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dima on 13.11.2017.
 */
public class InfoLab {

    private static InfoLab sInfoLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public static InfoLab get(Context context) {
        if (sInfoLab == null) {
            sInfoLab = new InfoLab(context);
        }
        return sInfoLab;
    }
    private InfoLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new InfoBaseHelper(mContext).getWritableDatabase();

    }
    private static ContentValues getContentValues(Info info)
    {
        ContentValues values = new ContentValues();
        values.put(InfoTable.Cols.UUID, info.getId().toString());
        values.put(InfoTable.Cols.TITLE, info.getTitle());
        values.put(InfoTable.Cols.DATE, info.getDate().getTime());
        values.put(InfoTable.Cols.TEMP, info.getTemp());
        values.put(InfoTable.Cols.LATITUDE, info.getLatitude());
        values.put(InfoTable.Cols.LONGITUDE, info.getLongitude());
        values.put(InfoTable.Cols.SENT, info.isSent() ? 1 : 0);
        return values;
    }
    private InfoCursorWrapper queryData(String whereClause, String [] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                InfoTable.NAME,
                null, // all columns
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new InfoCursorWrapper(cursor);
    }
    public File getPhotoFile(Info info)
    {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if(externalFilesDir == null)
        {
            return null;
        }
        return new File(externalFilesDir, info.getPhotoFilename());
    }

    public void updateInfo(Info info)
    {
        String uuidString = info.getId().toString();
        ContentValues values = getContentValues(info);

        mDatabase.update(InfoTable.NAME, values,
                InfoTable.Cols.UUID + " = ?",
                new String[]{uuidString}
                );
    }

    public List<Info> getInfoList() {
        List<Info> data = new ArrayList<>();
        InfoCursorWrapper cursor = queryData(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                data.add(cursor.getInfo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return data;
    }

    public Info getInfo(UUID id) {
        InfoCursorWrapper cursor  = queryData(
                InfoTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try{
            if(cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getInfo();
        } finally {
            cursor.close();
        }
    }
    public void addInfo(Info info)
    {
        ContentValues values = getContentValues(info);
        mDatabase.insert(InfoTable.NAME, null, values);
    }

    public void deleteInfo(UUID infoId)
    {
        String uuidString = infoId.toString();
        mDatabase.delete(InfoTable.NAME,  InfoTable.Cols.UUID + " = ?", new String[] {uuidString});
    }
}
