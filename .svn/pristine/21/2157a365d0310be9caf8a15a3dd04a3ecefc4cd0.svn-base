package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PAControlParamDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPATerminalCtrlParamMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class PATerminalCtrlParamMod extends AbstractLocalBusiness {
    public String doBusiness(InParamPATerminalCtrlParamMod inParam)
            throws DcdzSystemException {
        String result = callMethod(inParam);

        // ////////////////////////////////////////////////////////////////////////
        DBSContext.loadCtrlParams();

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamPATerminalCtrlParamMod inParam = (InParamPATerminalCtrlParamMod) request;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (inParam.CtrlTypeID <= 0 || StringUtils.isEmpty(inParam.KeyString)
                || StringUtils.isEmpty(inParam.DefaultValue))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        // 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        Date sysDateTime = DateUtils.nowDate();

        // /改控制参数信息
        PAControlParamDAO ctrlParamDAO = DAOFactory.getPAControlParamDAO();

        JDBCFieldArray setCols0 = new JDBCFieldArray();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        setCols0.add("DefaultValue", inParam.DefaultValue);
        whereCols0.add("CtrlTypeID", inParam.CtrlTypeID);
        whereCols0.add("KeyString", inParam.KeyString);

        try {
            ctrlParamDAO.update(database, setCols0, whereCols0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = inParam.OperID;
        log.FunctionID = inParam.FunctionID;
        log.OccurTime = sysDateTime;
        log.Remark = "KeyString=" + inParam.KeyString + ",value="
                + inParam.DefaultValue;

        try {
            CommonDAO.addOperatorLog(database, log);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e.getMessage());
        }

        return result;
    }
}
