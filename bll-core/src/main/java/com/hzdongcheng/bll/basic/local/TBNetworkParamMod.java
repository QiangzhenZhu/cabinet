package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBNetworkParamMod;
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

public class TBNetworkParamMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBNetworkParamMod inParam)
			throws DcdzSystemException {
		String result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		DBSContext.loadSysInfo();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBNetworkParamMod inParam = (InParamTBNetworkParamMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.MonServerIP)
				|| inParam.MonServerPort == 0)
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		SMSystemInfoDAO sysInfoDAO = DAOFactory.getSMSystemInfoDAO();

		JDBCFieldArray setCols0 = new JDBCFieldArray();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		setCols0.checkAdd("SystemID", inParam.SystemID);
		setCols0.checkAdd("InitPasswd", inParam.InitPasswd);
		setCols0.checkAdd("ServerIP", inParam.ServerIP);
		setCols0.checkAdd("ServerPort", inParam.ServerPort);
		setCols0.checkAdd("ServerSSL", inParam.ServerSSL);
		setCols0.checkAdd("InitPasswd", inParam.InitPasswd);
		setCols0.checkAdd("SoftwareVersion", inParam.SoftwareVersion);

		setCols0.add("MonServerIP", inParam.MonServerIP);
		setCols0.add("MonServerPort", inParam.MonServerPort);

		try {
			sysInfoDAO.update(database, setCols0, whereCols0);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "";

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
