package com.hzdongcheng.bll;

import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.bll.proxy.ProxyManager;
import com.hzdongcheng.bll.utils.ObjectUtils;
import com.hzdongcheng.persistent.DBWrapper;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

/**
 * 网络请求业基类
 */
public abstract class AbstractRemoteBusiness {
    protected static Log4jUtils log = Log4jUtils.createInstanse(AbstractRemoteBusiness.class);
    public ProxyManager netProxy = ProxyManager.getInstance();
    protected SQLiteDatabase database = null;
    protected boolean isUseDB = false;

    public String doBusiness(IRequest request, IResponder responder, int timeOut)
            throws DcdzSystemException {
        return callMethod(request, responder, timeOut);
    }

    protected abstract String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException;

    protected String callMethod(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        String result = "";
        try {
            if (isUseDB) {
                database = DBWrapper.getInstance().openWorkDatabase();
                if (database == null)
                    throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER);
                DBWrapper.beginTrans(database);
            }

            // 判断接口是否有TerminalNo
            if (ObjectUtils.HaveField(request, "TerminalNo")) {
                // 设备没有初始化
                if (StringUtils.isEmpty(DBSContext.terminalUid))
                    throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_NOINITDEVICE);

                ObjectUtils.setFieldValue(request, "TerminalNo", DBSContext.terminalUid);
            }

            // 具体业务处理(错误以异常形式抛出)
            result = handleBusiness(request, responder, timeOut);

            if (isUseDB)
                DBWrapper.commit(database);
        } catch (DcdzSystemException ex) // 业务或网络层出错
        {
            if (isUseDB)
                DBWrapper.rollBack(database);

            if (responder != null) {
                FaultResult faultResult = new FaultResult("E"+ex.getErrorCode(),
                        ex.getMessage(), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                responder.fault(faultResult);
            } else {
                log.error("RemoteContext-" + ex.getMessage());
                throw ex;
            }
        } catch (Exception ex) // 其它错误
        {
            log.error("remote business exception ", ex);

            if (isUseDB)
                DBWrapper.rollBack(database);

            if (responder != null) {
                FaultResult faultResult = new FaultResult("E"+DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                        ex.getMessage(), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                responder.fault(faultResult);
            } else {
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_ERROR, ex.getMessage());
            }
        } finally {
            if (isUseDB)
                DBWrapper.getInstance().closeWorkDatabase();
        }
        return result;
    }
}
