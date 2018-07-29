package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTIsShowLimitPage;
import com.hzdongcheng.bll.basic.dto.OutParamPTIsShowLimitPage;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;


    public class PTIsShowLimitPage extends AbstractLocalBusiness
    {
        public OutParamPTIsShowLimitPage doBusiness(InParamPTIsShowLimitPage inParam) throws DcdzSystemException
        {
            OutParamPTIsShowLimitPage result;
            result = callMethod( inParam);
            return result;
        }

       @Override
        protected OutParamPTIsShowLimitPage handleBusiness(IRequest request)
        {
            InParamPTIsShowLimitPage inParam = (InParamPTIsShowLimitPage)request;
            OutParamPTIsShowLimitPage result = new OutParamPTIsShowLimitPage();

            result.TakeOutCtrlTime = "0";
            result.TakeOutCtrlWeekend = "0";

            //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
//            DateTime sysDateTime = DateUtils.NowDateTime();
//            DateTime sysDate = DateUtils.NowDate();
//
//            //不控制
//            if ("1" != dbsClient.CtrlParam.takeOutCtrlTime)
//                return result;
//
//            //周末
//            if ("1" != dbsClient.CtrlParam.takeOutCtrlWeekend && (sysDateTime.DayOfWeek == DayOfWeek.Saturday || sysDateTime.DayOfWeek == DayOfWeek.Sunday))
//                return result;
//
//            TimeSpan timeSpan11 = new TimeSpan(getHour(dbsClient.CtrlParam.takeOutTime11), getMin(dbsClient.CtrlParam.takeOutTime11), 0);
//            TimeSpan timeSpan12 = new TimeSpan(getHour(dbsClient.CtrlParam.takeOutTime12), getMin(dbsClient.CtrlParam.takeOutTime12), 0);
//
//            TimeSpan timeSpan13 = new TimeSpan(getHour(dbsClient.CtrlParam.takeOutTime13), getMin(dbsClient.CtrlParam.takeOutTime13), 0);
//            TimeSpan timeSpan14 = new TimeSpan(getHour(dbsClient.CtrlParam.takeOutTime14), getMin(dbsClient.CtrlParam.takeOutTime14), 0);
//
//            TimeSpan nowSpan = sysDateTime.TimeOfDay;
//            if (((nowSpan > timeSpan11 && nowSpan < timeSpan12)) ||
//               ((nowSpan > timeSpan13 && nowSpan < timeSpan14)))
//            {
//                result.TakeOutCtrlTime = "1";
//                result.TakeOutCtrlWeekend = dbsClient.CtrlParam.takeOutCtrlWeekend;
//                result.TakeOutTime11 = dbsClient.CtrlParam.takeOutTime11;
//                result.TakeOutTime12 = dbsClient.CtrlParam.takeOutTime12;
//                result.TakeOutTime13 = dbsClient.CtrlParam.takeOutTime13;
//                result.TakeOutTime14 = dbsClient.CtrlParam.takeOutTime14;
//                result.Urgent1Mobile = dbsClient.CtrlParam.urgent1Mobile;
//                result.Urgent2Mobile = dbsClient.CtrlParam.urgent2Mobile;
//            }

            return result;
        }
    }
