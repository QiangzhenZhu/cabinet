package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalAdd;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class TBTerminalAdd extends AbstractLocalBusiness {
	public String doBusiness(InParamTBTerminalAdd inParam) throws DcdzSystemException {
		String result;

		result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		DBSContext.loadTerminalInfo();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBTerminalAdd inParam = (InParamTBTerminalAdd) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.TerminalNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		// 删掉原来的记录
		JDBCFieldArray wherColsDummy = new JDBCFieldArray();
		TBTerminal terminal = new TBTerminal();
		try {
			terminalDAO.delete(database, wherColsDummy);

			// 加入新增的柜体信息
			terminal.TerminalNo = inParam.TerminalNo;
			terminal.TerminalName = inParam.TerminalName;
			terminal.MainDeskAddress = inParam.MainDeskAddress;
			terminal.BoxNum = inParam.BoxNum;
			terminal.DeskNum = inParam.DeskNum;
			terminal.MBDeviceNo = inParam.MBDeviceNo;
			terminal.DepartmentID = inParam.DepartmentID;
			terminal.OfBureau = inParam.OfBureau;
			terminal.OfSegment = inParam.OfSegment;
			terminal.Location = inParam.Location;
			terminal.Latlon = inParam.Latlon;
			terminal.Remark = inParam.Remark;
			terminal.MacAddr = NetUtils.getMac();

			terminal.CreateTime = sysDateTime;
			terminal.LastModifyTime = sysDateTime;
			terminal.TerminalStatus = SysDict.TERMINAL_STATUS_NORMAL;
			terminal.RegisterFlag = SysDict.TERMINAL_REGISTERFLAG_NO;

			terminalDAO.insert(database, terminal);

			result = terminal.TerminalNo;

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "newNo=" + terminal.TerminalNo;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
