package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTSendUrgentSMS;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;

public class PTSendUrgentSMS extends AbstractRemoteBusiness {
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		isUseDB = true;
		// 本地数据已经提交
		String result = callMethod(request, responder, timeOut);

		// ////////////////////////////////////////////////////////////////////////
		String[] resultList = result.split(",");

		SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, resultList[0]);
		ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));

		return resultList[1];
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTSendUrgentSMS inParam = (InParamPTSendUrgentSMS) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.CustomerMobile) || StringUtils.isEmpty(inParam.UrgentMobile))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (!inParam.UrgentMobile.equals(DBSContext.ctrlParam.urgent1Mobile)
				&& !inParam.UrgentMobile.equals(DBSContext.ctrlParam.urgent2Mobile))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("CustomerMobile", inParam.CustomerMobile);
		try {
			if (inboxPackDAO.isExist(database, whereCols) == 0) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
			}

			inParam.TradeWaterNo = CommonDAO.buildTradeNo();

			// //////////////////////////////////////////////////////////////////////
			inParam.DynamicCode = RandUtils.generateNumber(6);

			// /////////////////插入数据上传队列////////////////////////////////////
			result = CommonDAO.insertUploadDataQueue(database, request);

			result = result + "," + inParam.DynamicCode;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}
		return result;
	}
}
