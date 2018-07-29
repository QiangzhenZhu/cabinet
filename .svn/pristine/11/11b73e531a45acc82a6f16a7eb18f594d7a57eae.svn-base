package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;


import java.util.Date;

import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamAPPostmanLogin;
import com.hzdongcheng.bll.basic.dto.OutParamAPPostmanLogin;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class APPostmanLogin extends AbstractLocalBusiness {
    public OutParamAPPostmanLogin doBusiness(InParamAPPostmanLogin inParam)
            throws DcdzSystemException {
        OutParamAPPostmanLogin result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OutParamAPPostmanLogin handleBusiness(IRequest request)
            throws DcdzSystemException {
        InParamAPPostmanLogin inParam = (InParamAPPostmanLogin) request;
        OutParamAPPostmanLogin result = new OutParamAPPostmanLogin();

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PostmanID)
                || StringUtils.isEmpty(inParam.TerminalNo)
                || StringUtils.isEmpty(inParam.RandomCode)
                || StringUtils.isEmpty(inParam.Password)
                || !inParam.RandomCode.equals(DBSContext.randomCode))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        // 2. 调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。

        // 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        Date sysDateTime = DateUtils.nowDate();

        // 4. 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)。

        // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = inParam.PostmanID;
        log.FunctionID = inParam.FunctionID;
        log.OccurTime = sysDateTime;
        log.Remark = "";

        try {
            CommonDAO.addOperatorLog(database, log);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
