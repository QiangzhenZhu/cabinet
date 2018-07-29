package com.hzdongcheng.bll.proxy;

import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.basic.dto.*;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

/**
 * 远程业务接口
 * Created by zhengxy on 2017/9/16.
 */

public interface BusinessProxy {
    /**
     * 投递员登陆
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTPostmanLogin request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 下载待投递订单列表
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTReadPackageQry request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 单个包裹查询
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTPackageDetail request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 投递包裹
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTDeliveryPackage request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 下载逾期包裹单列表
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTExpiredPackQry request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 取回逾期包裹
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTWithdrawExpiredPack request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 本次投递汇总
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTDeliverySum request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 用户取件身份认证
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTVerfiyUser request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 用户取件(不需要支付)
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTPickupPackage request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 管理员取件
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTManagerPickupPack request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 验证手机是否在黑名单列表
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTMobileBlackList request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 投递员注册
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPMPostmanRegister request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 重新获取验证码
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPMReGetCheckCode request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 投递员修改密码
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPMPostmanModPwd request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 投递员绑定卡
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPMPostmanBindCard request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 投递员解绑定卡
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPMPostmanUnBindCard request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 设备签到
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBDeviceSign request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 同步服务器时间(登陆签到，保持心跳)
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamSCSyncServerTime request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 上传设备警报信息
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBUploadDeviceAlert request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 格口故障状态
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamTBFaultStatusMod request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 柜体故障状态
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamTBTerminalModStatus request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;


    /**
     * 设备安装初始化
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBInitialization request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 设备心跳包检测
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBHeartBeat request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 设备格口状态报告
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBReportBoxStatus request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 设备外设状态报告
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBReportPeripheralStatus request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 获取广告列表
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBGetAdvertisePic request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 现场管理员登录
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBSpotAdminLogin request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 申请开箱密码
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamRMRequestOpenBoxKey request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 同步管理端操作日志
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamSCSyncManagerLog request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;


    /**
     * 获取最新软件版本信息
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBGetNewVersion request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;


    /**
     * 发送紧急取件短信
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTSendUrgentSMS request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 重新发送用户取件密码
     *
     * @param request
     * @param responder
     * @param timeOut
     */
    String doBusiness(InParamPTReSentOpenBoxKey request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 请求服务器分配箱子
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTAllocateeBox request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 通知服务器取消投递
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTDeliveryCancel request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 寄存包裹查询
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTDepositQry request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 下载在箱包裹信息
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTDownloadInboxInfo request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 远程查询可用箱门
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamPTRemoteQryFreeBox request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;


    /**
     * 上传开门
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBUploadOpenBox request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 上传开门
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBUploadCloseBox request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 设备激活
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamMBDeviceRegister request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

    /**
     * 设备升级
     *
     * @param request
     * @param responder
     * @param timeOut
     * @param secretKey
     */
    String doBusiness(InParamSMGetUpgradeInfo request, IResponder responder, int timeOut , String secretKey) throws DcdzSystemException;

}
