package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamSMModWelcomeInfo;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;

import java.util.Date;

public class SMModWelcomeInfo extends AbstractLocalBusiness {
    public String doBusiness(InParamSMModWelcomeInfo inParam)
            throws DcdzSystemException {
        String result;
        result = callMethod(inParam);

        // /////////////////////////////////////////////////////////////////////////////////////
        DBSContext.sysInfo.UpdateContent = result;

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamSMModWelcomeInfo inParam = (InParamSMModWelcomeInfo) request;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.OperID)
                || StringUtils.isEmpty(inParam.SystemID)
                || StringUtils.isEmpty(inParam.WelcomeInfo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        Date sysDateTime = DateUtils.nowDate();

        // ////////////////////////////////////////////////////////////////////////
        SMSystemInfoDAO sysInfoDAO = DAOFactory.getSMSystemInfoDAO();

        JDBCFieldArray setCols0 = new JDBCFieldArray();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        setCols0.add("UpdateContent", inParam.WelcomeInfo);
        try {
            sysInfoDAO.update(database, setCols0, whereCols0);

            // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
            OPOperatorLog log = new OPOperatorLog();
            log.OperID = inParam.OperID;
            log.FunctionID = inParam.FunctionID;
            log.OccurTime = sysDateTime;
            log.Remark = "";

            CommonDAO.addOperatorLog(database, log);

            result = inParam.WelcomeInfo;
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e.getMessage());
        }
        return result;
    }
}
