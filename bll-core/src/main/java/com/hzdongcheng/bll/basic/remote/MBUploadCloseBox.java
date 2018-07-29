package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBUploadCloseBox;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class MBUploadCloseBox extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        return super.doBusiness(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) {
        String result = "";
        InParamMBUploadCloseBox inParam = (InParamMBUploadCloseBox) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.TerminalNo))
            inParam.TerminalNo = DBSContext.terminalUid;

        if (responder != null)
            netProxy.sendRequest(request, responder, timeOut,
                    DBSContext.secretKey);

        return result;
    }
}
