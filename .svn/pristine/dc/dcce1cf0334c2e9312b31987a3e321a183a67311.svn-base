package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTRemoteQryFreeBox;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;

public class PTRemoteQryFreeBox extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		if (timeOut == 0)
			timeOut = 20;

		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTRemoteQryFreeBox inParam = (InParamPTRemoteQryFreeBox) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		// if (StringUtils.IsEmpty(inParam.PostmanID))
		// throw new DcdzSystemException(ErrorMessage.ERR_SYSTEM_PARAMTER);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		// 网络获取
		netProxy.sendRequest(request, responder, timeOut, DBSContext.secretKey);

		return result;
	}

}
