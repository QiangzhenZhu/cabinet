package com.hzdongcheng.persistent.ex;

import com.hzdongcheng.components.toolkits.exception.error.ErrorCode;
import com.hzdongcheng.components.toolkits.exception.error.ErrorTitle;

/**
 * Created by zhengxy on 2017/9/14.
 */

public final class DBErrorCode extends ErrorCode{
    public static final int SUCCESS = 0;
    public static final int ERR_DB_DATABASELAYER = 30001; // 数据持久层错误,请联系系统管理员
    public static final int ERR_DB_NORECORD = 30002;// 数据库记录不存在
    public static final int ERR_DB_ORACLERESOURCEBUSY = 30003; // 数据库资源忙
    public static final int ERR_DB_OPERATEDBERROR = 30004; // 操作数据库失败"
    public static final int ERR_DB_EXECUTEPROCFAIL = 30005; // 执行存储过程失败"
    public static final int ERR_DB_GETCONNECTIONFAIL = 30006; // 获取数据库连接失败"
    public static final int ERR_DB_TRAVERSEROWSETFAIL = 30007; // 遍历结果集失败"
    public static final int ERR_DATABASE_ADDFAIL = 30008; //插入数据失败
    public static final int ERR_DATABASE_MODFAIL = 30009; //更新数据失败
    public static final int ERR_DATABASE_DELFAIL = 30010; //删除数据失败
    public static final int ERR_DATABASE_QUERYFAIL = 30011; //查询数据失败

    static {
        ErrorTitle.putErrorTitle(SUCCESS, "Execute successfully.");
        ErrorTitle.putErrorTitle(ERR_DB_DATABASELAYER, "Persistent layer error.");
        ErrorTitle.putErrorTitle(ERR_DB_NORECORD, "No record.");
        ErrorTitle.putErrorTitle(ERR_DB_ORACLERESOURCEBUSY, "Resource busy.");
        ErrorTitle.putErrorTitle(ERR_DB_OPERATEDBERROR, "Operate database error.");
        ErrorTitle.putErrorTitle(ERR_DB_EXECUTEPROCFAIL, "Execute procedure fail.");
        ErrorTitle.putErrorTitle(ERR_DB_GETCONNECTIONFAIL, "Get connection fail.");
        ErrorTitle.putErrorTitle(ERR_DB_TRAVERSEROWSETFAIL, "Ergodic row set fail.");
        ErrorTitle.putErrorTitle(ERR_DATABASE_ADDFAIL, "Insert fail.");
        ErrorTitle.putErrorTitle(ERR_DATABASE_MODFAIL, "Update fail.");
        ErrorTitle.putErrorTitle(ERR_DATABASE_DELFAIL, "Delete fail.");
        ErrorTitle.putErrorTitle(ERR_DATABASE_QUERYFAIL, "Query fail.");
    }
}
