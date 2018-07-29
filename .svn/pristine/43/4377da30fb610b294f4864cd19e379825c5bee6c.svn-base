package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamSCSyncManagerLog;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Manager;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.Date;


public class TBOpenBox4Manager extends AbstractLocalBusiness {
	public String doBusiness(InParamTBOpenBox4Manager inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBOpenBox4Manager inParam = (InParamTBOpenBox4Manager) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.BoxNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.RemoteFlag))
			inParam.RemoteFlag = SysDict.OPER_FLAG_LOCAL;

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("BoxNo", inParam.BoxNo);
		try {
			TBBox box = boxDAO.find(database, whereCols0);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag + "," + inParam.BoxNo;

			CommonDAO.addOperatorLog(database, log);

			// ////////////////////////////////////////////////////////////////////////
			HAL.openBox(box.BoxName);
			// /////////////////////////////////////////////////////////////////////////

			// 插入数据上传队列
			if (SysDict.TERMINAL_REGISTERFLAG_YES.equals(DBSContext.registerFlag)) {
				InParamSCSyncManagerLog inParamLog = new InParamSCSyncManagerLog();
				inParamLog.OperID = inParam.OperID;
				inParamLog.TerminalNo = DBSContext.terminalUid;
				inParamLog.FactFunctionID = inParam.FunctionID;
				inParamLog.Remark = inParam.BoxNo;
				inParamLog.OccurTime = sysDateTime;
				inParamLog.TradeWaterNo = CommonDAO.buildTradeNo();

				CommonDAO.insertUploadDataQueue(database, inParamLog);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
