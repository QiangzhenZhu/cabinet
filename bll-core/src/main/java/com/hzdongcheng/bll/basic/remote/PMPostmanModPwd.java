package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPMPostmanModPwd;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;

public class PMPostmanModPwd extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		if (timeOut == 0)
			timeOut = 20;

		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) {
		InParamPMPostmanModPwd inParam = (InParamPMPostmanModPwd) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		// if (StringUtils.IsEmpty(inParam.PostmanID)
		// || StringUtils.IsEmpty(inParam.Password))
		// throw new DcdzSystemException(ErrorMessage.ERR_SYSTEM_PARAMTER);

		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		if (responder != null)
			netProxy.sendRequest(request, responder, timeOut,
					DBSContext.secretKey);

		return result;
	}
}
