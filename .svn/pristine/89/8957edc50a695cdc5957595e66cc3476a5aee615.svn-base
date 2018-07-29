package com.hzdongcheng.persistent;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Modify by zxy on 2017/9/14.
 */

public class SqliteContext extends ContextWrapper {
    Log4jUtils log = Log4jUtils.createInstanse(SqliteContext.class);
    private String fileDir = "";

    public SqliteContext(Context base) {
        super(base);

        String status = Environment.getExternalStorageState();

        if (status.equals(Environment.MEDIA_MOUNTED)) {
            //logConfigurator.setUseFileAppender(true);
            fileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hzdongcheng" + File.separator +
                    "data" + File.separator + "app" + File.separator + "database" + File.separator;
        } else {
            fileDir = "/data/data/com.hzdongcheng.parcellocker/database/";
        }
    }

    @Override
    public File getDatabasePath(String name) {
        return null;
    }

    private SQLiteDatabase openDatabase(String name) {
        File dbfile = new File(fileDir + name);

        if (checkDatabase(name) == false) {
            if (!dbfile.exists()) {
                File dirfile = new File(dbfile.getParent());
                if (!dirfile.exists())
                    dirfile.mkdirs();
            }

            try {
                copyDatabase(name);
                //unknown error (code 14): Could not open database
                dbfile.setWritable(true);
            } catch (IOException e) {
                log.error("Error copying database from system assets:" + e.getMessage());
                return null;
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(dbfile, null);
    }

    private boolean checkDatabase(String dbname) {
        SQLiteDatabase checkableDatabase = null;

        try {
            checkableDatabase = SQLiteDatabase.openDatabase(fileDir + dbname, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }

        if (checkableDatabase != null) {
            checkableDatabase.close();
        }

        return checkableDatabase != null;
    }

    private void copyDatabase(String dbname) throws IOException {
        //InputStream myInput = this.getAssets().open(dbname);
        InputStream myInput = getClass().getClassLoader().getResourceAsStream("assets/" + dbname);
        OutputStream myOutput = new FileOutputStream(fileDir + dbname);

        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return openDatabase(name);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return openDatabase(name);
    }

}
