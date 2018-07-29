package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBUploadDeviceAlert;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;



/**
 * 上传设备警报信息
 * 
 * @author Shalom
 *
 */
public class MBUploadDeviceAlert extends AbstractRemoteBusiness {
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		// 本地数据已经提交

		return callMethod(request, responder, timeOut);
	}

	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) {
		String result = "";
		InParamMBUploadDeviceAlert inParam = (InParamMBUploadDeviceAlert) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		if (responder != null)
			netProxy.sendRequest(request, responder, timeOut,
					DBSContext.secretKey);
		return result;
	}
}
