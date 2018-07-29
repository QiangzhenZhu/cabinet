package com.hzdongcheng.persistent.db;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

/**
 * Created by tony.zhongli on 2017/4/24.
 */

public class SQLLog {
    private static boolean isDebugEnabled = true;
    protected SQLLog() {
    }

    public static void logInsert(Log4jUtils log, Object dto){
        if(isDebugEnabled)
            log.debug("[insert sql]" + dto.toString());
    }

    public static void logUpdate(Log4jUtils log,String tableName,JDBCFieldArray setCols,JDBCFieldArray whereCols)
    {
        if(isDebugEnabled){
            String sql = "UPDATE " + tableName + " SET ";
            sql = sql + setCols.toLogSetSQL();
            if (whereCols != null)
                sql = sql + " WHERE 1=1 " + whereCols.toLogWhereSQL();

            log.debug("[update sql]" + sql);
        }
    }

    public static void logDelete(Log4jUtils log,String tableName,JDBCFieldArray whereCols)
    {
        if(isDebugEnabled){
            String sql = "DELETE FROM " + tableName + " WHERE 1=1 ";
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[delete sql]" + sql);
        }
    }

    public static void logIsExist(Log4jUtils log,String tableName,JDBCFieldArray whereCols)
    {
        if(isDebugEnabled){
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE 1=1 ";
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[isExist sql]" + sql);
        }
    }

    public static void logSelectRowSet(Log4jUtils log,String sql,JDBCFieldArray whereCols)
    {
        if(isDebugEnabled){
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[RowSet sql]" + sql);
        }
    }

    public static void logSelectFunction(Log4jUtils log, String sql,JDBCFieldArray whereCols) {
        if (isDebugEnabled) {
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[selectFunctions sql]" + sql);
        }
    }
}
