package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxStatusMod;
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


public class TBBoxStatusMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBBoxStatusMod inParam) throws DcdzSystemException {
		String result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		// /放到队列里面统一上传
		if (StringUtils.isNotEmpty(result)) {
			SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(inParam, result);
			ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBBoxStatusMod inParam = (InParamTBBoxStatusMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.BoxNo) || StringUtils.isEmpty(inParam.BoxStatus))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		if (StringUtils.isEmpty(inParam.RemoteFlag))
			inParam.RemoteFlag = SysDict.OPER_FLAG_LOCAL;

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// ////////////////////////////////////////////////////////////////////////
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("BoxNo", inParam.BoxNo);
		try {
			TBBox box = boxDAO.find(database, whereCols0);

			// ////////////////////////////////////////////////////////////////////////
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.add("BoxStatus", inParam.BoxStatus);
			whereCols.add("BoxNo", inParam.BoxNo);

			boxDAO.update(database, setCols, whereCols);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag + ",boxNo=" + box.BoxNo + ",oldStatus=" + box.BoxStatus + ",newStatus="
					+ inParam.BoxStatus;

			CommonDAO.addOperatorLog(database, log);

			// 插入数据上传队列
			if (SysDict.TERMINAL_REGISTERFLAG_YES == DBSContext.registerFlag
					&& SysDict.OPER_FLAG_LOCAL == inParam.RemoteFlag) {
				inParam.TradeWaterNo = CommonDAO.buildTradeNo();
				result = CommonDAO.insertUploadDataQueue(database, request);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}
		return result;
	}
}
