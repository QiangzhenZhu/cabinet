package com.hzdongcheng.bll;

import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.bll.utils.ObjectUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.DBWrapper;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;


public abstract class AbstractLocalBusiness {
    protected static Log4jUtils log = Log4jUtils.createInstanse(AbstractLocalBusiness.class);

    protected SQLiteDatabase database = null;
    private boolean isUsedDB = true;

    protected <T> T callMethod(IRequest item) throws DcdzSystemException {
        T result = null;

        if (!isUsedDB) {
            result = handleBusiness(item);
            return result;
        }

        try {
            database = DBWrapper.getInstance().openWorkDatabase();
            if (database == null)
                throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER);

            DBWrapper.beginTrans(database);
            // 设置当前登录管理员编号
            if (ObjectUtils.HaveField(item, "OperID")
                    && StringUtils.isEmpty(ObjectUtils.GetFieldStrValue(item, "OperID"))) {
                ObjectUtils.setFieldValue(item, "OperID", DBSContext.currentOperID);
            }

            // 判断接口是否有TerminalNo
            if (ObjectUtils.HaveField(item, "TerminalNo")) {
                if (StringUtils.isEmpty(ObjectUtils.GetFieldStrValue(item, "TerminalNo")))
                    ObjectUtils.setFieldValue(item, "TerminalNo", DBSContext.terminalUid);
            }
            // 实际的业务处理函数调用
            result = handleBusiness(item);

            DBWrapper.commit(database);

        } catch (DcdzSystemException ex1) {
            DBWrapper.rollBack(database);

            throw ex1;
        } catch (Exception ex2) {
            DBWrapper.rollBack(database);
            log.error("[LOCAL]" + ex2.getMessage());
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER);
        } finally {
            DBWrapper.getInstance().closeWorkDatabase();
        }

        return result;
    }

    protected abstract <T> T handleBusiness(IRequest inparam) throws DcdzSystemException;

}
