package com.hzdongcheng.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Modify by zxy on 2017/9/14.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    Log4jUtils log = Log4jUtils.createInstanse(SqliteHelper.class);

    private AtomicInteger openCounter = new AtomicInteger();
    private SQLiteDatabase database;

    public synchronized SQLiteDatabase openDatabase() {
        try {
            if (openCounter.incrementAndGet() == 1) {
                database = getWritableDatabase();
                //database.enableWriteAheadLogging();
            }
            return database;
        } catch (Exception e) {
            log.error("open database failed " + e.getMessage());
            return null;
        }
    }

    public synchronized void closeDatabase() {
        if (openCounter.decrementAndGet() == 0)
            database.close();
    }

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
