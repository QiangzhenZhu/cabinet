package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTPackageDetail;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;
/// <summary>
/// 单个包裹查询
/// </summary>

public class PTPackageDetail extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		if (timeOut == 0)
			timeOut = 20;

		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTPackageDetail inParam = (InParamPTPackageDetail) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		 if (StringUtils.isEmpty(inParam.PostmanID)
		 || StringUtils.isEmpty(inParam.PackageID))
		 throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);
		
		 inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();
		inParam.OccurTime = DateUtils.nowDate();

		// 网络获取
		netProxy.sendRequest(request, responder, timeOut, DBSContext.secretKey);

		return result;
	}
}
