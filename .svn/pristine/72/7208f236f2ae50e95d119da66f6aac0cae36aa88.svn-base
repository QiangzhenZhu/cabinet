package com.hzdongcheng.bll.proxy;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.*;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonPacket;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.utils.HttpUtils;
import com.hzdongcheng.bll.websocket.SocketClient;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

/**
 * 与服务端的业务代理
 * Created by zhengxy on 2017/9/16.
 */

public class Proxy4Dcdzsoft implements BusinessProxy {
    private Log4jUtils log = Log4jUtils.createInstanse(Proxy4Dcdzsoft.class);

    /**
     * 投递员登陆
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTPostmanLogin request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 下载待投递订单列表
     *
     * @param request
     * @param responder
     */
    public String doBusiness(InParamPTReadPackageQry request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 单个包裹查询
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTPackageDetail request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 投递包裹
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTDeliveryPackage request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 下载逾期包裹单列表
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTExpiredPackQry request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 取回逾期包裹
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTWithdrawExpiredPack request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 本次投递汇总
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTDeliverySum request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);

        return "";
    }

    /**
     * 用户取件身份认证
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTVerfiyUser request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);

        return "";
    }

    /**
     * 用户取件(不需要支付)
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTPickupPackage request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 管理员取件
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTManagerPickupPack request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 验证手机是否在黑名单列表
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTMobileBlackList request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 投递员注册
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPMPostmanRegister request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 重新获取验证码
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPMReGetCheckCode request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 投递员修改密码
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPMPostmanModPwd request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 投递员解绑定卡
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPMPostmanBindCard request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 投递员绑定卡
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPMPostmanUnBindCard request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 设备签到
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBDeviceSign request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {

        SocketClient.sendData(request, responder, "", secretKey);//监控服务器

        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);//业务服务器
        return "";
    }

    public String doBusiness(InParamSCSyncServerTime request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 上传设备警报信息
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBUploadDeviceAlert request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 格口故障状态
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamTBFaultStatusMod request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return SysDict.CONTINUE_SEND_YES;
    }

    /**
     * 柜体故障状态
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamTBTerminalModStatus request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return SysDict.CONTINUE_SEND_YES;
    }

    /**
     * 设备安装初始化
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBInitialization request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_UNSUPPORT);
    }

    /**
     * 设备心跳包检测
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBHeartBeat request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        OutParamMBHeartBeat outParam = new OutParamMBHeartBeat();
        outParam.BeatInterval = 60 * 24;
        SocketClient.sendData(request, responder, "", secretKey);

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(outParam));
        }

        return "";
    }

    /**
     * 设备格口状态报告(对账)
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBReportBoxStatus request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        String result = "";

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(result));
        }

        return "";
    }

    /**
     * 设备外设状态报告
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBReportPeripheralStatus request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        String result = "";

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(result));
        }

        return "";
    }

    /**
     * 获取广告列表
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBGetAdvertisePic request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_UNSUPPORT);
    }

    /**
     * 现场管理员登录
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBSpotAdminLogin request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);

        return "";
    }

    /**
     * 申请开箱密码
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamRMRequestOpenBoxKey request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 同步管理端操作日志
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamSCSyncManagerLog request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 获取最新软件版本信息
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamMBGetNewVersion request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }


    /**
     * 发送紧急取件短信
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTSendUrgentSMS request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        return SysDict.CONTINUE_SEND_YES;
    }


    /**
     * 重新发送用户取件密码
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    public String doBusiness(InParamPTReSentOpenBoxKey request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     * @return
     * @throws DcdzSystemException
     */
    public String doBusiness(InParamPTAllocateeBox request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_UNSUPPORT);
    }

    public String doBusiness(InParamPTDeliveryCancel request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        String result = "";

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(result));
        }

        return result;
    }

    /**
     * 寄存包裹查询
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    public String doBusiness(InParamPTDepositQry request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        String result = "";

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(result));
        }

        return result;
    }

    /**
     * 下载在箱包裹信息
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    public String doBusiness(InParamPTDownloadInboxInfo request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        String result = "";

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(result));
        }

        return result;
    }

    /**
     * 远程查询可用箱门
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    public String doBusiness(InParamPTRemoteQryFreeBox request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        String result = "";

        //打包返回
        if (responder != null) {
            responder.result(PacketUtils.buildLocalJsonResult(result));
        }

        return result;
    }

    /**
     * 上传开门状态
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    @Override
    public String doBusiness(InParamMBUploadOpenBox request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 上传关门状态
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    @Override
    public String doBusiness(InParamMBUploadCloseBox request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    /**
     * 设备激活
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    @Override
    public String doBusiness(InParamMBDeviceRegister request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.InitPasswd, request, responder, timeOut, secretKey);
        return "";
    }
    /**
     * 设备升级
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *  @param secretKey
     */
    @Override
    public String doBusiness(InParamSMGetUpgradeInfo request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        handleBusiness(request.TerminalNo, request, responder, timeOut, secretKey);
        return "";
    }

    private boolean handleBusiness(String terminalNo, IRequest request, IResponder responder, int timeOut, String secretKey) throws DcdzSystemException {
        boolean isOK = false;

        JsonPacket jsonPacket = PacketUtils.CreateRequestPacket(request, terminalNo, "");
        String body = PacketUtils.serializeObject(request);

        jsonPacket.body = body;
        jsonPacket._Sign = secretKey; // md5(m_sSecretKey + body)

        String content = PacketUtils.serializeObject(jsonPacket);

        String url = "http://" + DBSContext.serverHost + ":" + DBSContext.serverPort + "/terminalservice";
        log.info("request->:url=" + url + ",content=" + content);
        if ("InParamMBDeviceRegister".equals(jsonPacket._ServiceName)) {
            InParamMBDeviceRegister inParam = (InParamMBDeviceRegister) request;
            url = "http://" + inParam.ServerUrl + "/terminalservice";
        }
        String action = "";
        if ("InParamPTPackageDetail".equals(jsonPacket._ServiceName)) {
            action = "InParamPTPackageDetail";
        }

        String responseMsg = HttpUtils.post(url, content, terminalNo, action);
        JsonPacket pack = PacketUtils.unPack(responseMsg);
        if (pack != null) {
            JsonResult jsonResult = PacketUtils.deserializeJsonResult(pack.body);

            if (jsonResult != null) {
                if (jsonResult.success) {

                    responder.result(jsonResult);

                    isOK = true;
                } else //异常处理
                {
                    FaultResult faultResult = new FaultResult(jsonResult.msg, jsonResult.msg,
                            FaultResult.FAULT_RESULT_TYPE_REMOTE);

                    responder.fault(faultResult);
                }
            } else {
                log.error("[Network Error]:<JsonResult_Error>" + responseMsg);

                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_ERROR, "<JsonResult_Error>");
            }
        } else {
            log.error("[Network Error]:<JsonResult_Error>" + responseMsg);
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_ERROR, "<JsonPacket_Error>");
        }
        return isOK;
    }
}
