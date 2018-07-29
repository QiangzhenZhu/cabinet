package com.hzdongcheng.persistent.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.hzdongcheng.persistent.ex.DBErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tony.zhongli on 2017/4/24.
 */

public final class DbUtils {
    private final static Log4jUtils log = Log4jUtils.createInstanse(DbUtils.class);

    /**
     *
     * @param database
     * @param instance
     * @param type
     * @return
     * @throws DcdzSystemException
     */
    public static int executeInsert(SQLiteDatabase database, Object instance, Class type) throws DcdzSystemException {
        ContentValues map = new ContentValues();

        Field[] fields = type.getDeclaredFields();
        Object object = null;
        try {
            for (Field f : fields) {
                if (Modifier.isStatic(f.getModifiers())) //过滤掉静态的系统生成的属性
                    continue;

                object = f.get(instance);
                if (object != null) {
                    if (object instanceof java.util.Date)
                        map.put(f.getName(), DateUtils.datetimeToString((java.util.Date) object));
                    else
                        map.put(f.getName(), object.toString());
                } else {
                    map.put(f.getName(), "");
                }
            }

            return (int) database.replaceOrThrow(type.getSimpleName(), null, map);
        } catch (Exception e) {
            log.error("[DBERROR-(" + DBErrorCode.ERR_DATABASE_ADDFAIL + ")]==>[errmsg]:" + e.getMessage());
            throw new DcdzSystemException(DBErrorCode.ERR_DATABASE_ADDFAIL);
        }
    }

    /**
     *
     * @param database
     * @param table
     * @param setCols
     * @param whereCols
     * @return
     * @throws DcdzSystemException
     */
    public static int executeUpdate(SQLiteDatabase database, String table, JDBCFieldArray setCols,@NonNull JDBCFieldArray whereCols) throws DcdzSystemException {
        if (setCols == null || setCols.size() == 0) {
            throw new IllegalArgumentException("Empty values");
        }

        StringBuilder sql = new StringBuilder(128);
        sql.append("UPDATE ");
        sql.append(table);
        sql.append(" SET ");
        sql.append(setCols.toSetSQL());
        sql.append(" WHERE ");
        sql.append(whereCols.toWhereSQL());

        SQLLog.logUpdate(log, table, setCols, whereCols);

        try {
            //bindArgs only byte[], String, Long and Double are supported in bindArgs
            ArrayList bindArgsList = new ArrayList();

            for (int i = 0; i < setCols.size(); i++) {
                if (setCols.get(i).name != null) {
                    bindArgsList.add(setCols.get(i).getValue());
                }
            }

            if (whereCols != null) {
                for (int j = 0; j < whereCols.size(); j++) {
                    if (whereCols.get(j).name != null) {
                        bindArgsList.add(whereCols.get(j).getValue());
                    }
                }
            }

            database.execSQL(sql.toString(), bindArgsList.toArray());
        } catch (SQLException e) {
            log.error("[DBERROR-(" + DBErrorCode.ERR_DATABASE_MODFAIL + ")]==>[errmsg]:" + e.getMessage() + " [sql]:" + sql);
            throw new DcdzSystemException(DBErrorCode.ERR_DATABASE_MODFAIL);
        }

        return 1;
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @return
     * @throws DcdzSystemException
     */
    public static int executeDelete(SQLiteDatabase database, String table, JDBCFieldArray whereCols) throws DcdzSystemException {
        String whereClause = "";
        String[] whereArgs = null;
        int result = 0;

        if (whereCols != null) {
            whereClause = whereCols.toWhereSQL();
            whereArgs = new String[whereCols.size()];
            for (int i = 0; i < whereCols.size(); i++) {
                whereArgs[i] = whereCols.get(i).getStringValue();
            }
        }

        try {
            result = database.delete(table, whereClause, whereArgs);
        } catch (SQLException e) {
            log.error("[DBERROR-(" + DBErrorCode.ERR_DATABASE_DELFAIL + ")]==>[errmsg]:" + e.getMessage());
            throw new DcdzSystemException(DBErrorCode.ERR_DATABASE_DELFAIL);
        }

        return result;
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @return
     * @throws DcdzSystemException
     */
    public static int executeCount(SQLiteDatabase database, String table, JDBCFieldArray whereCols) throws DcdzSystemException {
        String whereClause = "";
        String[] whereArgs = null;
        int result = 0;

        if (whereCols != null) {
            whereClause = whereCols.toWhereSQL();
            whereArgs = new String[whereCols.size()];
            for (int i = 0; i < whereCols.size(); i++) {
                whereArgs[i] = whereCols.get(i).getStringValue();
            }
        }

        StringBuilder sql = new StringBuilder(128);
        sql.append("SELECT COUNT(*) FROM ");
        sql.append(table);
        sql.append(" WHERE ");
        sql.append(whereClause);

        try {
            Cursor cs = database.rawQuery(sql.toString(), whereArgs);
            cs.moveToFirst();

            result = cs.getInt(0);
            cs.close();
        } catch (SQLException e) {
            log.error("[DBERROR-(" + DBErrorCode.ERR_DATABASE_QUERYFAIL + ")]==>[errmsg]:" + e.getMessage());
            throw new DcdzSystemException(DBErrorCode.ERR_DATABASE_QUERYFAIL);
        }

        return result;
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @param type
     * @param <T>
     * @return
     * @throws DcdzSystemException
     */
    public static <T> List<T> executeQuery(SQLiteDatabase database, String table, JDBCFieldArray whereCols, Class type) throws DcdzSystemException {
        return executeQuery(database, table, whereCols, type, null, null);
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @param type
     * @param orderBy
     * @param <T>
     * @return
     * @throws DcdzSystemException
     */
    public static <T> List<T> executeQuery(SQLiteDatabase database, String table, JDBCFieldArray whereCols, Class type, String orderBy) throws DcdzSystemException {
        return executeQuery(database, table, whereCols, type, orderBy, null);
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @param type
     * @param recordBegin
     * @param recordNum
     * @param <T>
     * @return
     * @throws DcdzSystemException
     */
    public static <T> List<T> executeQuery(SQLiteDatabase database, String table, JDBCFieldArray whereCols, Class type, int recordBegin, int recordNum) throws DcdzSystemException {
        if (recordNum <= 0)
            recordNum = 300;
        if (recordBegin <= 0)
            recordBegin = 0;

        String limit = recordBegin + "," + recordNum;

        return executeQuery(database, table, whereCols, type, null, limit);
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @param type
     * @param orderBy
     * @param recordBegin
     * @param recordNum
     * @param <T>
     * @return
     * @throws DcdzSystemException
     */
    public static <T> List<T> executeQuery(SQLiteDatabase database, String table, JDBCFieldArray whereCols, Class type, String orderBy, int recordBegin, int recordNum) throws DcdzSystemException {
        if (recordNum <= 0)
            recordNum = 300;
        if (recordBegin <= 0)
            recordBegin = 0;

        String limit = recordBegin + "," + recordNum;

        return executeQuery(database, table, whereCols, type, orderBy, limit);
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @param type
     * @param orderBy
     * @param limit
     * @param <T>
     * @return
     * @throws DcdzSystemException
     */
    public static <T> List<T> executeQuery(SQLiteDatabase database, String table, JDBCFieldArray whereCols, Class type, String orderBy, String limit) throws DcdzSystemException {
        String whereClause = "";
        String[] whereArgs = null;

        if (whereCols != null) {
            whereClause = whereCols.toWhereSQL();
            /*whereArgs = new String[whereCols.size()];
            for (int i = 0; i< whereCols.size();i++){
                whereArgs[i] = whereCols.get(i).getStringValue();
            }*/
            whereArgs = whereCols.toWhereArgs();
        }

        try {
            Cursor cs = database.query(table, null, whereClause, whereArgs, null, null, orderBy, limit);

            return readEntityListByReader(cs, type);
        } catch (SQLException e) {
            log.error("[DBERROR-(" + DBErrorCode.ERR_DATABASE_QUERYFAIL + ")]==>[errmsg]:" + e.getMessage());
            throw new DcdzSystemException(DBErrorCode.ERR_DATABASE_QUERYFAIL);
        }
    }

    /**
     *
     * @param rs
     * @param type
     * @param <T>
     * @return
     */
    public static <T> List<T> readEntityListByReader(Cursor rs, Class type) {
        List<T> list = new ArrayList<T>();
        try {
            Field[] fs = Class.forName(type.getName()).newInstance().getClass().getFields();
            while (rs.moveToNext()) {
                try {
                    T inst = (T) Class.forName(type.getName()).newInstance();
                    for (Field f : fs) {
                        int index = rs.getColumnIndex(f.getName());
                        if (index < 0)
                            continue;
                        String obj = rs.getString(index);
                        if (obj != null) {
                            if (f.getType() == Date.class)
                                f.set(inst, DateUtils.stringToDateTime(obj));
                            else if (f.getType() == double.class)
                                f.set(inst, Double.parseDouble(obj));
                            else if (f.getType() == int.class)
                                f.set(inst, Integer.parseInt(obj));
                            else if (f.getType() == long.class)
                                f.set(inst, Long.parseLong(obj));
                            else
                                f.set(inst, obj);
                        }
                    }
                    list.add(inst);
                } catch (ClassNotFoundException e) {
                    log.error("Create Instance Failed-" + e.getMessage());
                    break;
                }

            }
        } catch (SQLException | SecurityException | InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            log.error("Create Instance Failed-" + e.getMessage());
        } finally {
            rs.close();
        }

        return list;
    }

    /**
     *
     * @param database
     * @param table
     * @param whereCols
     * @param orderBy
     * @return
     * @throws DcdzSystemException
     */
    public static Cursor executeQuery(SQLiteDatabase database, String table, JDBCFieldArray whereCols, String orderBy) throws DcdzSystemException {
        String whereClause = "";
        String[] whereArgs = null;

        if (whereCols != null) {
            whereClause = whereCols.toWhereSQL();
            whereArgs = whereCols.toWhereArgs();
        }

        try {
            Cursor cs = database.query(table, null, whereClause, whereArgs, null, null, orderBy, null);

            return cs;
        } catch (SQLException e) {
            log.error("[DBERROR-(" + DBErrorCode.ERR_DATABASE_QUERYFAIL + ")]==>[errmsg]:" + e.getMessage());
            throw new DcdzSystemException(DBErrorCode.ERR_DATABASE_QUERYFAIL);
        }
    }
}
