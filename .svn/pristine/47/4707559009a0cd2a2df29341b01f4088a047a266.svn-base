package com.hzdongcheng.persistent.sequence;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.persistent.DBWrapper;

/**
 * Created by Peace on 2016/12/19.
 */

public abstract class BaseSequence {
    private Object thisLock = new Object();

    /// <summary>
    /// The max key value
    /// </summary>
    protected long maxKey = 0L;

    /// <summary>
    /// The min key value
    /// </summary>
    protected long minKey = 0L;

    /// <summary>
    /// The min key value
    /// </summary>
    protected long currentKey = 0L;

    /// <summary>
    /// The cached key size
    /// </summary>
    protected long cacheSize = 0L;

    /// <summary>
    /// 所有的键值在数据库中的初始化值都为0
    /// </summary>
    public BaseSequence() {
    }

    public void InitValue() throws SQLException {
        DoGetCurrentMaxValue();
        minKey = maxKey - cacheSize + 1;
        currentKey = minKey;
    }


    /// <summary>
    /// 取值方法，提供键的当前值(Temeplate method)
    /// </summary>
    /// <returns></returns>
    public long GetNextKey() throws SQLException {
        synchronized (thisLock) {
            if (currentKey > maxKey) {
                DoGetCurrentMaxValue();
                minKey = maxKey - cacheSize + 1;
                currentKey = minKey;
            }

            return currentKey++;
        }
    }

    /// <summary>
    /// 执行更新查询
    /// 1.独立提交,防止事务失败缓存脏数据
    /// 2.先更新后查询,防止多个服务器读取相同的数据
    /// </summary>
    /// <param name="sequenceName"></param>
    protected void ExecuteSelectForUpdate(String sequenceName) throws SQLException {
        long nMaxValue = 0L;
        long nCacheSize = 0L;

        //String sequenceName = sequenceClass.getSimpleName();

        String updateSQL = "UPDATE PASequence SET SeqValue = SeqValue + CacheSize  WHERE SeqName = " + "'" + sequenceName + "'";
        String qrySQL = "SELECT SeqValue,CacheSize FROM PASequence WHERE SeqName = " + "'" + sequenceName + "'";

        //执行更新
        SQLiteDatabase database = DBWrapper.getInstance().openWorkDatabase();
        try {
            database.beginTransaction();

            database.execSQL(updateSQL);
            //执行查询
            Cursor dt = database.rawQuery(qrySQL, null);

            while (dt.moveToNext()) {
                nMaxValue = dt.getLong(dt.getColumnIndex("SeqValue"));
                nCacheSize = dt.getLong(dt.getColumnIndex("CacheSize"));
            }

            maxKey = nMaxValue;
            cacheSize = nCacheSize;

            database.setTransactionSuccessful();

        } catch (SQLException ex) {
//            Log4jUtils.error("[DBError]" + ex.getCause() + ":" + ex.getMessage());

            throw ex;

        } finally {
            database.endTransaction();
            DBWrapper.getInstance().closeWorkDatabase();
        }

    }

    /**
     * 从数据库提取键的当前值(primitive method)
     *
     * @throws SQLException
     */
    protected abstract void DoGetCurrentMaxValue() throws SQLException;
}
