package com.hzdongcheng.bll.basic;


import com.hzdongcheng.bll.basic.local.TBFaultStatusMod;
import com.hzdongcheng.bll.basic.local.TBTerminalModStatus;
import com.hzdongcheng.bll.basic.remote.MBDeviceRegister;
import com.hzdongcheng.bll.basic.remote.MBUploadCloseBox;
import com.hzdongcheng.bll.basic.remote.MBUploadOpenBox;
import com.hzdongcheng.bll.basic.remote.SMGetUpgradeInfo;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.basic.dto.*;
import com.hzdongcheng.bll.basic.remote.MBDeviceSign;
import com.hzdongcheng.bll.basic.remote.MBGetAdvertisePic;
import com.hzdongcheng.bll.basic.remote.MBGetNewVersion;
import com.hzdongcheng.bll.basic.remote.MBHeartBeat;
import com.hzdongcheng.bll.basic.remote.MBInitialization;
import com.hzdongcheng.bll.basic.remote.MBReportBoxStatus;
import com.hzdongcheng.bll.basic.remote.MBReportPeripheralStatus;
import com.hzdongcheng.bll.basic.remote.MBSpotAdminLogin;
import com.hzdongcheng.bll.basic.remote.MBUploadDeviceAlert;
import com.hzdongcheng.bll.basic.remote.PMPostmanBindCard;
import com.hzdongcheng.bll.basic.remote.PMPostmanModPwd;
import com.hzdongcheng.bll.basic.remote.PMPostmanRegister;
import com.hzdongcheng.bll.basic.remote.PMPostmanUnBindCard;
import com.hzdongcheng.bll.basic.remote.PMReGetCheckCode;
import com.hzdongcheng.bll.basic.remote.PTAllocateeBox;
import com.hzdongcheng.bll.basic.remote.PTDeliveryCancel;
import com.hzdongcheng.bll.basic.remote.PTDeliveryPackage;
import com.hzdongcheng.bll.basic.remote.PTDeliverySum;
import com.hzdongcheng.bll.basic.remote.PTDepositQry;
import com.hzdongcheng.bll.basic.remote.PTDownloadInboxInfo;
import com.hzdongcheng.bll.basic.remote.PTExpiredPackQry;
import com.hzdongcheng.bll.basic.remote.PTManagerPickupPack;
import com.hzdongcheng.bll.basic.remote.PTMobileBlackList;
import com.hzdongcheng.bll.basic.remote.PTPackageDetail;
import com.hzdongcheng.bll.basic.remote.PTPickupPackage;
import com.hzdongcheng.bll.basic.remote.PTPostmanLogin;
import com.hzdongcheng.bll.basic.remote.PTReSentOpenBoxKey;
import com.hzdongcheng.bll.basic.remote.PTReadPackageQry;
import com.hzdongcheng.bll.basic.remote.PTRemoteQryFreeBox;
import com.hzdongcheng.bll.basic.remote.PTSendUrgentSMS;
import com.hzdongcheng.bll.basic.remote.PTVerfiyUser;
import com.hzdongcheng.bll.basic.remote.PTWithdrawExpiredPack;
import com.hzdongcheng.bll.basic.remote.RMRequestOpenBoxKey;
import com.hzdongcheng.bll.basic.remote.SCSyncManagerLog;
import com.hzdongcheng.bll.basic.remote.SCSyncServerTime;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

/**
 * Created by zhengxy on 2017/9/16.
 */

public class RemoteContext {


    /**
     * 投递员登录
     *
     *  @param request
     *  @param responder
     */
    public String doBusiness(InParamPTPostmanLogin request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTPostmanLogin().doBusiness(request,responder, timeOut);
    }

    /**
     * 下载待投递订单列表
     *
     *  @param request
     *  @param responder
     */
    public String doBusiness(InParamPTReadPackageQry request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTReadPackageQry().doBusiness(request,responder, timeOut);
    }

    /**
     * 单个包裹查询
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTPackageDetail request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTPackageDetail().doBusiness(request,responder, timeOut);
    }

    /**
     * 投递包裹
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTDeliveryPackage request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTDeliveryPackage().doBusiness(request,responder, timeOut);
    }

    /**
     * 下载逾期包裹单列表
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTExpiredPackQry request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTExpiredPackQry().doBusiness(request,responder, timeOut);
    }

    /**
     * 取回逾期包裹
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTWithdrawExpiredPack request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTWithdrawExpiredPack().doBusiness(request,responder, timeOut);
    }

    /**
     * 本次投递汇总
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTDeliverySum request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTDeliverySum().doBusiness(request,responder, timeOut);
    }

    /**
     * 用户取件身份认证
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTVerfiyUser request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTVerfiyUser().doBusiness(request,responder, timeOut);
    }

    /**
     * 用户取件(不需要支付)
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTPickupPackage request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTPickupPackage().doBusiness(request,responder, timeOut);
    }

    /**
     * 管理员取件
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTManagerPickupPack request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTManagerPickupPack().doBusiness(request,responder, timeOut);
    }

    /**
     * 验证手机是否在黑名单列表
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTMobileBlackList request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTMobileBlackList().doBusiness(request,responder, timeOut);
    }

    /**
     * 投递员注册
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPMPostmanRegister request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PMPostmanRegister().doBusiness(request,responder, timeOut);
    }

    /**
     * 重新获取验证码
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPMReGetCheckCode request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PMReGetCheckCode().doBusiness(request,responder, timeOut);
    }

    /**
     * 投递员修改密码
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPMPostmanModPwd request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PMPostmanModPwd().doBusiness(request,responder, timeOut);
    }

    /**
     * 投递员解绑定卡
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPMPostmanBindCard request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PMPostmanBindCard().doBusiness(request,responder, timeOut);
    }

    /**
     * 投递员绑定卡
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPMPostmanUnBindCard request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PMPostmanUnBindCard().doBusiness(request,responder, timeOut);
    }

    /**
     * 设备签到
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBDeviceSign request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBDeviceSign().doBusiness(request,responder, timeOut);
    }

    public String doBusiness(InParamSCSyncServerTime request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new SCSyncServerTime().doBusiness(request,responder, timeOut);
    }

    /**
     * 上传设备警报信息
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBUploadDeviceAlert request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBUploadDeviceAlert().doBusiness(request,responder, timeOut);
    }

    /**
     * 格口故障状态
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamTBFaultStatusMod request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new TBFaultStatusMod().doBusiness(request);
    }

    /**
     * 柜体故障状态
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamTBTerminalModStatus request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new TBTerminalModStatus().doBusiness(request);
    }

    /**
     * 设备安装初始化
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBInitialization request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBInitialization().doBusiness(request,responder, timeOut);
    }

    /**
     * 设备心跳包检测
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBHeartBeat request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBHeartBeat().doBusiness(request,responder, timeOut);
    }

    /**
     * 设备格口状态报告(对账)
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBReportBoxStatus request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBReportBoxStatus().doBusiness(request,responder, timeOut);
    }

    /**
     * 设备外设状态报告
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBReportPeripheralStatus request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBReportPeripheralStatus().doBusiness(request,responder, timeOut);

    }

    /**
     * 获取广告列表
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBGetAdvertisePic request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBGetAdvertisePic().doBusiness(request,responder, timeOut);
    }

    /**
     * 现场管理员登录
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBSpotAdminLogin request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBSpotAdminLogin().doBusiness(request,responder, timeOut);
    }

    /**
     * 申请开箱密码
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamRMRequestOpenBoxKey request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new RMRequestOpenBoxKey().doBusiness(request,responder, timeOut);
    }

    /**
     * 同步管理端操作日志
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamSCSyncManagerLog request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new SCSyncManagerLog().doBusiness(request,responder, timeOut);
    }

    /**
     * 获取最新软件版本信息
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamMBGetNewVersion request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBGetNewVersion().doBusiness(request,responder, timeOut);
    }


    /**
     * 发送紧急取件短信
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTSendUrgentSMS request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTSendUrgentSMS().doBusiness(request,responder, timeOut);
    }


    /**
     * 重新发送用户取件密码
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     */
    public String doBusiness(InParamPTReSentOpenBoxKey request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTReSentOpenBoxKey().doBusiness(request,responder, timeOut);
    }

    /**
     *
     * @param request
     * @param responder
     * @param timeOut
     *
     * @return
     * @throws DcdzSystemException
     */
    public String doBusiness(InParamPTAllocateeBox request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTAllocateeBox().doBusiness(request,responder, timeOut);
    }

    public String doBusiness(InParamPTDeliveryCancel request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTDeliveryCancel().doBusiness(request,responder, timeOut);
    }

    /**
     * 寄存包裹查询
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamPTDepositQry request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTDepositQry().doBusiness(request,responder, timeOut);
    }

    /**
     * 下载在箱包裹信息
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamPTDownloadInboxInfo request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTDownloadInboxInfo().doBusiness(request,responder, timeOut);
    }

    /**
     * 远程查询可用箱门
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamPTRemoteQryFreeBox request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new PTRemoteQryFreeBox().doBusiness(request,responder, timeOut);
    }
    /**
     * 开门上传
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamMBUploadOpenBox request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBUploadOpenBox().doBusiness(request,responder, timeOut);
    }

    /**
     * 关门上传
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamMBUploadCloseBox request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBUploadCloseBox().doBusiness(request,responder, timeOut);
    }
    /**
     * 设备激活
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamMBDeviceRegister request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new MBDeviceRegister().doBusiness(request,responder, timeOut);
    }

    /**
     * 设备升级
     *
     *  @param request
     *  @param responder
     *  @param timeOut
     *
     */
    public String doBusiness(InParamSMGetUpgradeInfo request, IResponder responder, int timeOut) throws DcdzSystemException
    {
        return new SMGetUpgradeInfo().doBusiness(request,responder, timeOut);
    }
}
