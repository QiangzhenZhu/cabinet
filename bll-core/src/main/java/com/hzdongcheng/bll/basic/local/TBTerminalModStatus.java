package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalModStatus;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class TBTerminalModStatus extends AbstractLocalBusiness {
	public String doBusiness(InParamTBTerminalModStatus inParam) throws DcdzSystemException {
		String result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		// /放到队列里面统一上传
		if (StringUtils.isNotEmpty(result)) {
			SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(inParam, result);
			ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));
		}

		// ////////////////////////////////////////////////////////////////////////
		DBSContext.terminalStatus = inParam.TerminalStatus;

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBTerminalModStatus inParam = (InParamTBTerminalModStatus) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.TerminalStatus))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		if (StringUtils.isEmpty(inParam.RemoteFlag))
			inParam.RemoteFlag = SysDict.OPER_FLAG_LOCAL;

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 判断是否存在记录
		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = inParam.TerminalNo;
		try {
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("TerminalNo", inParam.TerminalNo);
			terminal = terminalDAO.find(database, whereCols);

			// 修改记录
			JDBCFieldArray setCols = new JDBCFieldArray();
			setCols.add("TerminalStatus", inParam.TerminalStatus);
			setCols.add("LastModifyTime", sysDateTime);
			terminalDAO.update(database,setCols, whereCols);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag + ",terminalNo=" + inParam.TerminalNo + ",status = "
					+ inParam.TerminalStatus;

			CommonDAO.addOperatorLog(database,log);

			// 插入数据上传队列
			if (SysDict.TERMINAL_REGISTERFLAG_YES.equals(DBSContext.registerFlag)
					&& SysDict.OPER_FLAG_LOCAL.equals(inParam.RemoteFlag)) {
				inParam.TradeWaterNo = CommonDAO.buildTradeNo();
				result = CommonDAO.insertUploadDataQueue(database, request);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}
		return result;
	}
}
