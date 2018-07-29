package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliverySum;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;

public class PTDeliverySum extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		isUseDB = true;
		// 本地数据已经提交
		String result = callMethod(request, responder, timeOut);

		// ////////////////////////////////////////////////////////////////////////
		if (StringUtils.isNotEmpty(result)) {
			SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
			ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));
		}

		return result;
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		InParamPTDeliverySum inParam = (InParamPTDeliverySum) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID) || StringUtils.isEmpty(inParam.OrderList))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();
		inParam.OccurTime = DateUtils.nowDate();

		// 插入数据上传队列
		try {
			result = CommonDAO.insertUploadDataQueue(database, request);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}

		return result;
	}
}
