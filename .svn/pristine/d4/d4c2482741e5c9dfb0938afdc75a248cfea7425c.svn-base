package com.hzdongcheng.bll.basic.remote;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBGetNewVersion;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;


   /**
    * 获取最新软件版本信息
    * @author Shalom
    *
    */
    public class MBGetNewVersion extends AbstractRemoteBusiness
    {
    	@Override
        public  String doBusiness(IRequest request, IResponder responder , int timeOut) throws DcdzSystemException
        {
            return super.doBusiness(request, responder, timeOut);
        }
    	
    	@Override
        protected String handleBusiness(IRequest request, IResponder responder , int timeOut)
        {
            String result = "";
            InParamMBGetNewVersion inParam = (InParamMBGetNewVersion)request;

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.TerminalNo))
                inParam.TerminalNo = DBSContext.terminalUid;

            //查询柜体信息
            inParam.TradeWaterNo = CommonDAO.buildTradeNo();
            
            if (responder != null)
                netProxy.sendRequest(request, responder, timeOut,DBSContext.secretKey);

            return result;
        }
    }

