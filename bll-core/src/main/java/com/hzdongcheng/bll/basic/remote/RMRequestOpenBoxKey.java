package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamRMRequestOpenBoxKey;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.RMAppealLogDAO;
import com.hzdongcheng.persistent.dto.RMAppealLog;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;

public class RMRequestOpenBoxKey extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut)
			throws DcdzSystemException {
		isUseDB = true;
		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) throws DcdzSystemException {
		String result = "";
		InParamRMRequestOpenBoxKey inParam = (InParamRMRequestOpenBoxKey) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.AppealUser)
				|| StringUtils.isEmpty(inParam.AppealType)
				|| StringUtils.isEmpty(inParam.BoxNo)
				|| StringUtils.isEmpty(inParam.PackageID)
				|| StringUtils.isEmpty(inParam.CustomerMobile)
				|| DateUtils.isNull(inParam.StoredTime))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		// ////////////////////////////////////////////////////////////////////////
		RMAppealLogDAO appealLogDAO = DAOFactory.getRMAppealLogDAO();
		RMAppealLog appealLog = new RMAppealLog();
		appealLog.AppealNo = StringUtils.createUUID();
		appealLog.AppealUser = inParam.AppealUser;
		appealLog.AppealType = inParam.AppealType;
		appealLog.BoxNo = inParam.BoxNo;
		appealLog.PackageID = inParam.PackageID;
		appealLog.CustomerMobile = inParam.CustomerMobile;
		appealLog.AppealTime = DateUtils.nowDate();
		appealLog.LastModifyTime = appealLog.AppealTime;
		appealLog.AppealStatus = SysDict.APPEAL_STATUS_REQUESTKEY;
		appealLog.TerminalNo = DBSContext.terminalUid;

		try {
			appealLogDAO.insert(database, appealLog);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		// ////////////////////////////////////////////////////////////////////////
		inParam.AppealNo = appealLog.AppealNo;
		inParam.TerminalNo = DBSContext.terminalUid;

		// ////////////////////////////////////////////////////////////////////////
		if (responder != null)
			netProxy.sendRequest(request, responder, 15, DBSContext.secretKey);

		return result;
	}
}
