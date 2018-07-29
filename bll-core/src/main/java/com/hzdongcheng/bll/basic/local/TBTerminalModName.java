package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalModName;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class TBTerminalModName extends AbstractLocalBusiness {
	public String doBusiness(InParamTBTerminalModName inParam)
			throws DcdzSystemException {
		String result;

		result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////////////////////
		DBSContext.terminalName = inParam.TerminalName;
		DBSContext.location = inParam.Location;
		if (StringUtils.isNotEmpty(inParam.MBDeviceNo))
			DBSContext.terminalUid = inParam.MBDeviceNo;
		// ///////////////////////////////////////////////////////////////////////////////////////

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBTerminalModName inParam = (InParamTBTerminalModName) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.TerminalNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		JDBCFieldArray setCols1 = new JDBCFieldArray();
		JDBCFieldArray whereCols1 = new JDBCFieldArray();

		setCols1.add("TerminalName", inParam.TerminalName);
		setCols1.checkAdd("MBDeviceNo", inParam.MBDeviceNo);
		setCols1.add("OfBureau", inParam.OfBureau);
		setCols1.add("OfSegment", inParam.OfSegment);
		setCols1.add("Location", inParam.Location);
		setCols1.add("Latlon", inParam.Latlon);

		whereCols1.add("TerminalNo", inParam.TerminalNo);
		try {
			terminalDAO.update(database, setCols1, whereCols1);

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
