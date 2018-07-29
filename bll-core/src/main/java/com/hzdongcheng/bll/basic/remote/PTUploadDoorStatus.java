package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTUploadDoorStatus;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;

/**
 * 用户取件关门状态上传
 **/
public class PTUploadDoorStatus extends AbstractRemoteBusiness {
	
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) {
		String result = "";
		InParamPTUploadDoorStatus inParam = (InParamPTUploadDoorStatus) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		if (responder != null)
			netProxy.sendRequest(request, responder, timeOut,
					DBSContext.secretKey);

		return result;
	}
}
