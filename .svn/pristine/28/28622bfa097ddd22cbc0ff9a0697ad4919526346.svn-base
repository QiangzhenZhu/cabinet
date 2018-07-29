package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamMBPushTerminalLedMsg;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;



    public class MBPushTerminalLedMsg extends AbstractLocalBusiness
    {
        public String doBusiness(InParamMBPushTerminalLedMsg inParam) throws DcdzSystemException
        {
            String result;
            result = callMethod( inParam);
            return result;
        }

      @Override
        protected String handleBusiness(IRequest request) throws DcdzSystemException
        {
            InParamMBPushTerminalLedMsg inParam = (InParamMBPushTerminalLedMsg)request;
            String result = "";

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.OperID)
                || StringUtils.isEmpty(inParam.LedMsg))
                throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);


            //3.	调用UtilDAO.getCurrentDateTime()获得系统日期时间。
//            DateTime sysDateTime = DateUtils.NowDateTime();
//            DateTime sysDate = DateUtils.NowDate();
//
//            /////////////////////////////////////////////////////
//            if (StringUtils.IsNotEmpty(dbsClient.CtrlParam.ledScreenPort))
//            {
//                byte bScreenID = 0x01;
//
//                //打开串口
//                int ret = DcdzLedCtrlClass.Open(dbsClient.CtrlParam.ledScreenPort);
//                if (ret != 0)
//                {
//                    Log.Error("LED OPEN ERROR - " + ret);
//                    throw new DcdzSystemException(ErrorMessage.ERR_DRIVER_OPEN);
//                }
//
//                //握手
//                ret = DcdzLedCtrlClass.LedHandShake(bScreenID);
//                if (ret != 0)
//                {
//                    Log.Error("LED LedHandShake ERROR - " + ret);
//                    throw new DcdzSystemException(ErrorMessage.ERR_DRIVER_OPEN);
//                }
//
//                //设置屏幕参数接口
//                byte bColorType = 0x01; //单色
//                ret = DcdzLedCtrlClass.LedSetParam(bScreenID, (short)inParam.LedWidth, (short)inParam.LedHeight, bColorType);
//                if (ret != 0)
//                {
//                    Log.Error("LED LedSetParam ERROR - " + ret);
//                    throw new DcdzSystemException(ErrorMessage.ERR_DRIVER_OPEN);
//                }
//                Thread.Sleep(1000);
//
//                //设置内码窗格式
//                LEDScreenWindowArea WindowAreaOne = new LEDScreenWindowArea();
//
//                byte bAreaID = bScreenID; //区 域 号  	默认值 1
//                short sPointXStart = (short)inParam.StartPointX;
//                short sPointYStart = (short)inParam.StartPointY;
//                short sPointWidth = (short)inParam.LedWidth;
//                short sPointHeight = (short)inParam.LedHeight;
//                byte bFontSize = (byte)inParam.FontSize;
//                byte bFontColor = (byte)inParam.FontColor;
//
//                byte[] bDisplayType = new byte[4];
//                bDisplayType[0] = (byte)inParam.DisplayWay;
//                bDisplayType[1] = (byte)inParam.QuitWay;
//                bDisplayType[2] = (byte)inParam.RunSpeed;
//                bDisplayType[3] = (byte)inParam.StopTime;
//
//                String sContent = inParam.LedMsg;
//
//                WindowAreaOne.uAreaID = bAreaID;
//                WindowAreaOne.uAreaMode = 0x0F;
//                WindowAreaOne.uHorizontalStartPoint = sPointXStart;
//                WindowAreaOne.uVerticalStartPoint = sPointYStart;
//                WindowAreaOne.uHorizontalEndPoint = (short)(sPointWidth - 1);
//                WindowAreaOne.uVerticalEndPoint = (short)(sPointHeight - 1);
//                WindowAreaOne.uFontSize = bFontSize;
//                WindowAreaOne.uFontColor = bFontColor;
//                WindowAreaOne.uDisplayStyle = bDisplayType;
//                WindowAreaOne.bContentLongth = 0;
//                WindowAreaOne.sContent = sContent;
//
//                //内码窗区域设置
//                ret = DcdzLedCtrlClass.LedSetOneWindowArea(WindowAreaOne);
//                if (ret != 0)
//                {
//                    Log.Error("LED LedSetOneWindowArea ERROR - " + ret);
//                    throw new DcdzSystemException(ErrorMessage.ERR_DRIVER_OPEN);
//                }
//
//                //握手
//                ret = DcdzLedCtrlClass.LedHandShake(bScreenID);
//                if (ret != 0)
//                {
//                    Log.Error("LED LedHandShake1 ERROR - " + ret);
//                    throw new DcdzSystemException(ErrorMessage.ERR_DRIVER_OPEN);
//                }
//
//                ret = DcdzLedCtrlClass.DoSendOneProgramContent(bAreaID, sContent);
//                if (ret != 0)
//                {
//                    Log.Error("LED DoSendOneProgramContent ERROR - " + ret);
//                    throw new DcdzSystemException(ErrorMessage.ERR_DRIVER_OPEN);
//                }
//                //关闭串口
//                DcdzLedCtrlClass.Close();
//            }
//
//            // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
//            OPOperatorLog log = new OPOperatorLog();
//            log.OperID = inParam.OperID;
//            log.FunctionID = inParam.FunctionID;
//            log.OccurTime = sysDateTime;
//            log.Remark = "";
//
//            CommonDAO.AddOperatorLog(log, dbWrapper);

            return result;
        }
    }
