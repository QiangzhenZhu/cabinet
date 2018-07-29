package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamSMModServerCfg;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import java.util.Date;


public class SMModServerCfg extends AbstractLocalBusiness {
	public String doBusiness(InParamSMModServerCfg inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		DBSContext.loadSysInfo();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamSMModServerCfg inParam = (InParamSMModServerCfg) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.SystemID)
				|| StringUtils.isEmpty(inParam.ServerIP)
				|| inParam.ServerPort <= 0
				|| StringUtils.isEmpty(inParam.ServerSSL))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		Date sysDateTime = DateUtils.nowDate();

		// ////////////////////////////////////////////////////////////////////////
		SMSystemInfoDAO sysInfoDAO = DAOFactory.getSMSystemInfoDAO();

		JDBCFieldArray setCols0 = new JDBCFieldArray();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		setCols0.add("ServerIP", inParam.ServerIP);
		setCols0.add("ServerPort", inParam.ServerPort);
		setCols0.checkAdd("ServerSSL", inParam.ServerSSL);

		setCols0.checkAdd("MonServerIP", inParam.MonServerIP);
		setCols0.checkAdd("MonServerPort", inParam.MonServerPort);

		try {
			sysInfoDAO.update(database, setCols0, whereCols0);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}

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