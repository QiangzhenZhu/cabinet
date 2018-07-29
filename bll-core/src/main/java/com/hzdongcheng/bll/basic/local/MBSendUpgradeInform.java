package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBSendUpgradeInform;
import com.hzdongcheng.bll.basic.dto.InParamSMGetUpgradeInfo;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.TimingTask;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.dto.OPOperatorLog;

import java.util.Date;

public class MBSendUpgradeInform  extends AbstractLocalBusiness {
    public String doBusiness(InParamMBSendUpgradeInform inParam) throws DcdzSystemException {
        String result;
        result = callMethod(inParam);

        // ////////////////////////////////////////////////////////////////////////
        DBSContext.loadSysInfo();

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamMBSendUpgradeInform inParam = (InParamMBSendUpgradeInform) request;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.OperID))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        TimingTask.Upgradesoftware("1");
        TimingTask.Upgradesoftware("2");
        Date sysDateTime = DateUtils.nowDate();
        // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = inParam.OperID;
        log.FunctionID = inParam.FunctionID;
        log.OccurTime = sysDateTime;
        log.Remark = StringUtils.reflectionToString(inParam);

        try {
            CommonDAO.addOperatorLog(database, log);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e.getMessage());
        }

        return result;
    }
}
