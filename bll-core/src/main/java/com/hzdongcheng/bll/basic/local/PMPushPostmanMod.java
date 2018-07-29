package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPMPushPostmanMod;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;


    public class PMPushPostmanMod extends AbstractLocalBusiness
    {
        public String doBusiness(InParamPMPushPostmanMod inParam) throws DcdzSystemException
        {
            String result;
            result = callMethod(inParam);
            return result;
        }

        @Override
        protected String handleBusiness(IRequest request) throws DcdzSystemException
        {
            InParamPMPushPostmanMod inParam = (InParamPMPushPostmanMod)request;
            String result = "";

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.CompanyID)
                || StringUtils.isEmpty(inParam.PostmanType)
                || StringUtils.isEmpty(inParam.Password))
                throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

            if (StringUtils.isEmpty(inParam.OperID))
                inParam.OperID = "191919";


            //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
//            DateTime sysDateTime = DateUtils.NowDateTime();
//            DateTime sysDate = DateUtils.NowDate();
//
//
//            ///////////////////////////////////////////////////
//            IPMPostmanDAO postmanDAO = daoFactory.IPMPostmanDAO;
//            PMPostman postman = new PMPostman();
//            postman.PostmanID = inParam.PostmanID;
//
//            ///////////////////////////////
//            postmanDAO.Delete(postman, dbWrapper);
//
//            ////////////////////////////////////////////////////////////////
//            postman.PostmanName = StringUtils.IsEmpty(inParam.PostmanName) ? inParam.PostmanID : inParam.PostmanName;
//            postman.CompanyID = inParam.CompanyID;
//            postman.PostmanType = inParam.PostmanType;
//            postman.Password = inParam.Password;
//            postman.InputMobileFlag = "0";
//            postman.PostmanStatus = "0";
//            postman.CreateTime = sysDateTime;
//
//            postmanDAO.Insert(postman, dbWrapper);
//
//            ///////////////////////////////
//            //箱体权限如何控制
//
//
//            // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
//            OPOperatorLog log = new OPOperatorLog();
//            log.OperID = inParam.OperID;
//            log.FunctionID = inParam.FunctionID;
//            log.OccurTime = sysDateTime;
//            log.Remark = inParam.PostmanID;
//
//            CommonDAO.AddOperatorLog(log, dbWrapper);


            return result;
        }
    }
