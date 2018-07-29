package com.hzdongcheng.persistent;

import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

/**
 * Created by wangzl on 2017/7/19.
 */

public class DBWrapper {
    private static SqliteHelper database_work = null;
    private static DBWrapper instance = null;
    Log4jUtils log = Log4jUtils.createInstanse(DBWrapper.class);

    private static final int DB_VERSION = 2;

    private DBWrapper() {
        database_work = new SqliteHelper(new SqliteContext(null), "dbs_terminal.db", null, DB_VERSION);
    }

    public SQLiteDatabase openWorkDatabase() {
        return database_work.openDatabase();
    }

    public void closeWorkDatabase() {
        try {
            database_work.closeDatabase();
        } catch (Exception e) {
            log.error("close database error--" + e.getMessage());
        }
    }

    public static void beginTrans(SQLiteDatabase database) {
        database.beginTransaction();
    }

    public static void rollBack(SQLiteDatabase database) {
        if (database != null)
            database.endTransaction();
    }

    public static void commit(SQLiteDatabase database) {
        database.setTransactionSuccessful();
        database.endTransaction();

    }

    public synchronized static DBWrapper getInstance() {
        if (instance == null)
            instance = new DBWrapper();
        return instance;
    }
}
