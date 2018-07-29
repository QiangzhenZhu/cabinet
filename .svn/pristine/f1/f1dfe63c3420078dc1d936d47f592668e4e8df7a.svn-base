package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTSendCamaraInfo;
import com.hzdongcheng.bll.basic.dto.OutParamPTSendCamaraInfo;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;


    public class PTSendCamaraInfo extends AbstractLocalBusiness
    {
        public OutParamPTSendCamaraInfo doBusiness(InParamPTSendCamaraInfo inParam) throws DcdzSystemException
        {
            OutParamPTSendCamaraInfo result;
            result = callMethod( inParam);
            return result;
        }

       @Override
        protected OutParamPTSendCamaraInfo handleBusiness(IRequest request) throws DcdzSystemException
        {
            InParamPTSendCamaraInfo inParam = (InParamPTSendCamaraInfo)request;
            OutParamPTSendCamaraInfo result = new OutParamPTSendCamaraInfo();

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.TerminalNo))
                throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);


            //2.	调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。


           


            return result;
        }
    }
