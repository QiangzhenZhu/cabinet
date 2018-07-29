package com.hzdongcheng.bll.basic;

import com.hzdongcheng.bll.basic.dto.InParamSMModServerCfg;
import com.hzdongcheng.bll.basic.local.APStoreInOpenBox;
import com.hzdongcheng.bll.basic.local.APTakeOutOpenBox;
import com.hzdongcheng.bll.basic.local.MBSendUpgradeInform;
import com.hzdongcheng.bll.basic.local.SMModServerCfg;
import com.hzdongcheng.persistent.dto.PADictionary;
import com.hzdongcheng.persistent.dto.PATerminalCtrlParam;
import com.hzdongcheng.persistent.dto.TBBox4Detect;
import com.hzdongcheng.persistent.dto.TBBox4Open;
import com.hzdongcheng.persistent.dto.TBDesk;
import com.hzdongcheng.bll.basic.dto.*;
import com.hzdongcheng.bll.basic.local.APCustomerAppOpenBox;
import com.hzdongcheng.bll.basic.local.APPostmanAppTakeout;
import com.hzdongcheng.bll.basic.local.APPostmanLogin;
import com.hzdongcheng.bll.basic.local.MBDeliverInboxPac;
import com.hzdongcheng.bll.basic.local.MBModRegisterFlag;
import com.hzdongcheng.bll.basic.local.MBPushTerminalLedMsg;
import com.hzdongcheng.bll.basic.local.OPDelSpotOperator;
import com.hzdongcheng.bll.basic.local.OPModSpotOperPwd;
import com.hzdongcheng.bll.basic.local.OPOperLogQry;
import com.hzdongcheng.bll.basic.local.OPOperLogQryCount;
import com.hzdongcheng.bll.basic.local.OPOperLogin;
import com.hzdongcheng.bll.basic.local.OPPushSpotOperator;
import com.hzdongcheng.bll.basic.local.PAControlParamQry;
import com.hzdongcheng.bll.basic.local.PADictionaryQry;
import com.hzdongcheng.bll.basic.local.PATerminalCtrlParamMod;
import com.hzdongcheng.bll.basic.local.PTBoxIsUsed;
import com.hzdongcheng.bll.basic.local.PTCheckWithdrawPack;
import com.hzdongcheng.bll.basic.local.PTDelPackage;
import com.hzdongcheng.bll.basic.local.PTDeliveryRecordQry;
import com.hzdongcheng.bll.basic.local.PTDeliveryRecordQryCount;
import com.hzdongcheng.bll.basic.local.PTExpiredPackLocalFilter;
import com.hzdongcheng.bll.basic.local.PTIsShowLimitPage;
import com.hzdongcheng.bll.basic.local.PTLockUser4WrongPwd;
import com.hzdongcheng.bll.basic.local.PTModExpiredTime;
import com.hzdongcheng.bll.basic.local.PTModPackageInfo;
import com.hzdongcheng.bll.basic.local.PTModPackageStatus;
import com.hzdongcheng.bll.basic.local.PTOneDeliveryRecordQry;
import com.hzdongcheng.bll.basic.local.PTPackIsDelivery;
import com.hzdongcheng.bll.basic.local.PTPackLocalFilter;
import com.hzdongcheng.bll.basic.local.PTPushSubscribeOrder;
import com.hzdongcheng.bll.basic.local.PTResetOpenBoxKey;
import com.hzdongcheng.bll.basic.local.PTSendCamaraInfo;
import com.hzdongcheng.bll.basic.local.PTSetBoxFault;
import com.hzdongcheng.bll.basic.local.PTTakeoutTimeQry;
import com.hzdongcheng.bll.basic.local.RMCheckCustomerRequest;
import com.hzdongcheng.bll.basic.local.RMCheckPostmanRequest;
import com.hzdongcheng.bll.basic.local.RMOpenBoxRecord;
import com.hzdongcheng.bll.basic.local.RMRemoteOpenBox;
import com.hzdongcheng.bll.basic.local.RMWriteLocalOpenBoxKey;
import com.hzdongcheng.bll.basic.local.SCDelUploadDataQueue;
import com.hzdongcheng.bll.basic.local.SCModUploadDataQueue;
import com.hzdongcheng.bll.basic.local.SCSyncLocalTime;
import com.hzdongcheng.bll.basic.local.SCWriteOpenBoxKey;
import com.hzdongcheng.bll.basic.local.SCWriteServerTime;
import com.hzdongcheng.bll.basic.local.SMModSignPwd;
import com.hzdongcheng.bll.basic.local.SMModWelcomeInfo;
import com.hzdongcheng.bll.basic.local.SMSysInfoQry;
import com.hzdongcheng.bll.basic.local.TBAutoLockRevert;
import com.hzdongcheng.bll.basic.local.TBBoxHeightQry;
import com.hzdongcheng.bll.basic.local.TBBoxHeightSet;
import com.hzdongcheng.bll.basic.local.TBBoxNameMod;
import com.hzdongcheng.bll.basic.local.TBBoxQry;
import com.hzdongcheng.bll.basic.local.TBBoxQry4Detect;
import com.hzdongcheng.bll.basic.local.TBBoxQry4Open;
import com.hzdongcheng.bll.basic.local.TBBoxQryCount;
import com.hzdongcheng.bll.basic.local.TBBoxStatusMod;
import com.hzdongcheng.bll.basic.local.TBBoxTypeMod;
import com.hzdongcheng.bll.basic.local.TBCopyBoxType;
import com.hzdongcheng.bll.basic.local.TBDeskAdd;
import com.hzdongcheng.bll.basic.local.TBDeskDel;
import com.hzdongcheng.bll.basic.local.TBDeskMod;
import com.hzdongcheng.bll.basic.local.TBDeskQry;
import com.hzdongcheng.bll.basic.local.TBFaultStatusMod;
import com.hzdongcheng.bll.basic.local.TBFreeBoxStat;
import com.hzdongcheng.bll.basic.local.TBIsExistMasterType;
import com.hzdongcheng.bll.basic.local.TBLockStatusMod;
import com.hzdongcheng.bll.basic.local.TBNetworkParamMod;
import com.hzdongcheng.bll.basic.local.TBNetworkParamQry;
import com.hzdongcheng.bll.basic.local.TBOpenBox4Delivery;
import com.hzdongcheng.bll.basic.local.TBOpenBox4Deposit;
import com.hzdongcheng.bll.basic.local.TBOpenBox4Manager;
import com.hzdongcheng.bll.basic.local.TBOpenBox4Pickup;
import com.hzdongcheng.bll.basic.local.TBOpenBox4Recovery;
import com.hzdongcheng.bll.basic.local.TBOpenMaintainKey;
import com.hzdongcheng.bll.basic.local.TBPeripheralsMod;
import com.hzdongcheng.bll.basic.local.TBPeripheralsQry;
import com.hzdongcheng.bll.basic.local.TBReOpenBox4Cancel;
import com.hzdongcheng.bll.basic.local.TBReOpenBox4Pickup;
import com.hzdongcheng.bll.basic.local.TBReOpenBox4Postman;
import com.hzdongcheng.bll.basic.local.TBTerminalAdd;
import com.hzdongcheng.bll.basic.local.TBTerminalDetail;
import com.hzdongcheng.bll.basic.local.TBTerminalMod;
import com.hzdongcheng.bll.basic.local.TBTerminalModName;
import com.hzdongcheng.bll.basic.local.TBTerminalModStatus;
import com.hzdongcheng.bll.basic.local.TBTerminalParamMod;
import com.hzdongcheng.bll.basic.local.TBTerminalParamQry;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.List;

/**
 * Created by zhengxy on 2017/9/16.
 */

public class LocalContext {
    //#region 系统相关

    /**
     * 修改服务器连接设置
     *
     * @param in
     */
    public String doBusiness(InParamSMModServerCfg in) throws DcdzSystemException {
        return (new SMModServerCfg()).doBusiness(in);
    }

    /**
     * 修改签到密码
     *
     * @param in
     */
    public String doBusiness(InParamSMModSignPwd in) throws DcdzSystemException {
        return (new SMModSignPwd()).doBusiness(in);
    }

    /**
     * 修改欢迎词
     *
     * @param in
     */
    public String doBusiness(InParamSMModWelcomeInfo in) throws DcdzSystemException {
        return (new SMModWelcomeInfo()).doBusiness(in);
    }

    /**
     * 系统配置查询
     *
     * @param in
     */
    public OutParamSMSysInfoQry doBusiness(InParamSMSysInfoQry in) throws DcdzSystemException {
        return (new SMSysInfoQry()).doBusiness(in);
    }

    /**
     * 同步本地时间
     *
     * @param in
     */
    public String doBusiness(InParamSCSyncLocalTime in) throws DcdzSystemException {
        return (new SCSyncLocalTime()).doBusiness(in);
    }

    //#endregion

    //#region 管理员相关

    /**
     * 查询控制参数
     *
     * @param in
     */
    public List<PATerminalCtrlParam> doBusiness(InParamPAControlParamQry in) throws DcdzSystemException {
        return (new PAControlParamQry()).doBusiness(in);
    }

    /**
     * 查询系统字典
     *
     * @param in
     */
    public List<PADictionary> doBusiness(InParamPADictionaryQry in) throws DcdzSystemException {
        return (new PADictionaryQry()).doBusiness(in);
    }

    /**
     * 修改设备端控制参数信息
     *
     * @param in
     */
    public String doBusiness(InParamPATerminalCtrlParamMod in) throws DcdzSystemException {
        return (new PATerminalCtrlParamMod()).doBusiness(in);
    }

    /**
     * 管理员登陆
     *
     * @param in
     */
    public String doBusiness(InParamOPOperLogin in) throws DcdzSystemException {
        return (new OPOperLogin()).doBusiness(in);
    }

    /**
     * 管理员日志查询
     *
     * @param in
     */
    public List<OutParamOPOperLogQry> doBusiness(InParamOPOperLogQry in) throws DcdzSystemException {
        return (new OPOperLogQry()).doBusiness(in);
    }

    /**
     * 管理员日志查询数量
     *
     * @param in
     */
    public int doBusiness(InParamOPOperLogQryCount in) throws DcdzSystemException {
        return (new OPOperLogQryCount()).doBusiness(in);
    }

    /**
     * 推送现场管理员
     *
     * @param in
     */
    public String doBusiness(InParamOPPushSpotOperator in) throws DcdzSystemException {
        return (new OPPushSpotOperator()).doBusiness(in);
    }

    /**
     * 修改现场管理员密码
     *
     * @param in
     */
    public String doBusiness(InParamOPModSpotOperPwd in) throws DcdzSystemException {
        return (new OPModSpotOperPwd()).doBusiness(in);
    }

    /**
     * 删除现场管理员
     *
     * @param in
     */
    public String doBusiness(InParamOPDelSpotOperator in) throws DcdzSystemException {
        return (new OPDelSpotOperator()).doBusiness(in);
    }

    //#endregion

    //#region  柜体箱体

    /**
     * 柜体信息修改
     *
     * @param in
     */
    public String doBusiness(InParamTBTerminalMod in) throws DcdzSystemException {
        return (new TBTerminalMod()).doBusiness(in);
    }

    /**
     * 单个柜体信息查询
     *
     * @param in
     */
    public OutParamTBTerminalDetail doBusiness(InParamTBTerminalDetail in) throws DcdzSystemException {
        return (new TBTerminalDetail()).doBusiness(in);
    }

    /**
     * 柜体状态修改
     *
     * @param in
     */
    public String doBusiness(InParamTBTerminalModStatus in) throws DcdzSystemException {
        return (new TBTerminalModStatus()).doBusiness(in);
    }

    /**
     * 单个柜体信息查询
     *
     * @param in
     */
    public String doBusiness(InParamTBTerminalAdd in) throws DcdzSystemException {
        return (new TBTerminalAdd()).doBusiness(in);
    }


    /**
     * 柜体名称修改
     *
     * @param in
     */
    public String doBusiness(InParamTBTerminalModName in) throws DcdzSystemException {
        return (new TBTerminalModName()).doBusiness(in);
    }


    /**
     * 柜体监控参数修改
     *
     * @param in
     */
    public String doBusiness(InParamTBTerminalParamMod in) throws DcdzSystemException {
        return (new TBTerminalParamMod()).doBusiness(in);
    }

    /**
     * 柜体监控参数查询
     *
     * @param in
     */
    public OutParamTBTerminalParamQry doBusiness(InParamTBTerminalParamQry in) throws DcdzSystemException {
        return (new TBTerminalParamQry()).doBusiness(in);
    }

    /**
     * 柜体网络参数修改
     *
     * @param in
     */
    public String doBusiness(InParamTBNetworkParamMod in) throws DcdzSystemException {
        return (new TBNetworkParamMod()).doBusiness(in);
    }

    /**
     * 柜体网络参数查询
     *
     * @param in
     */
    public OutParamTBNetworkParamQry doBusiness(InParamTBNetworkParamQry in) throws DcdzSystemException {
        return (new TBNetworkParamQry()).doBusiness(in);
    }

    /**
     * 柜体外设配置修改
     *
     * @param in
     */
    public String doBusiness(InParamTBPeripheralsMod in) throws DcdzSystemException {
        return (new TBPeripheralsMod()).doBusiness(in);
    }

    /**
     * 柜体外设配置查询
     *
     * @param in
     */
    public OutParamTBPeripheralsQry doBusiness(InParamTBPeripheralsQry in) throws DcdzSystemException {
        return (new TBPeripheralsQry()).doBusiness(in);
    }

    /**
     * 扩展柜增加
     *
     * @param in
     */
    public String doBusiness(InParamTBDeskAdd in) throws DcdzSystemException {
        return (new TBDeskAdd()).doBusiness(in);
    }

    /**
     * 扩展柜修改
     *
     * @param in
     */
    public String doBusiness(InParamTBDeskMod in) throws DcdzSystemException {
        return (new TBDeskMod()).doBusiness(in);
    }

    /**
     * 扩展柜删除
     *
     * @param in
     */
    public String doBusiness(InParamTBDeskDel in) throws DcdzSystemException {
        return (new TBDeskDel()).doBusiness(in);
    }

    /**
     * 扩展柜查询
     *
     * @param in
     */
    public List<TBDesk> doBusiness(InParamTBDeskQry in) throws DcdzSystemException {
        return (new TBDeskQry()).doBusiness(in);
    }

    /**
     * 箱体类型修改
     *
     * @param in
     */
    public String doBusiness(InParamTBBoxTypeMod in) throws DcdzSystemException {
        return (new TBBoxTypeMod()).doBusiness(in);
    }

    /**
     * 箱体状态修改
     *
     * @param in
     */
    public String doBusiness(InParamTBBoxStatusMod in) throws DcdzSystemException {
        return (new TBBoxStatusMod()).doBusiness(in);
    }

    /**
     * 箱体锁定状态维护
     *
     * @param in
     */
    public String doBusiness(InParamTBLockStatusMod in) throws DcdzSystemException {
        return (new TBLockStatusMod()).doBusiness(in);
    }

    /**
     * 箱体故障状态维护
     *
     * @param in
     */
    public String doBusiness(InParamTBFaultStatusMod in) throws DcdzSystemException {
        return (new TBFaultStatusMod()).doBusiness(in);
    }

    /**
     * 箱体显示名称修改
     *
     * @param in
     */
    public String doBusiness(InParamTBBoxNameMod in) throws DcdzSystemException {
        return (new TBBoxNameMod()).doBusiness(in);
    }

    /**
     * 箱体信息查询
     *
     * @param in
     */
    public List<OutParamTBBoxQry> doBusiness(InParamTBBoxQry in) throws DcdzSystemException {
        return (new TBBoxQry()).doBusiness(in);
    }

    /**
     * 箱体信息查询数量
     *
     * @param in
     */
    public int doBusiness(InParamTBBoxQryCount in) throws DcdzSystemException {
        return (new TBBoxQryCount()).doBusiness(in);
    }

    /**
     * 可用箱体信息统计
     *
     * @param in
     */
    public OutParamTBFreeBoxStat doBusiness(InParamTBFreeBoxStat in) throws DcdzSystemException {
        return (new TBFreeBoxStat()).doBusiness(in);
    }

    /**
     * 设备检测箱体查询
     *
     * @param in
     */
    public List<TBBox4Detect> doBusiness(InParamTBBoxQry4Detect in) throws DcdzSystemException {
        return (new TBBoxQry4Detect()).doBusiness(in);
    }

    /**
     * 管理开箱箱体查询
     *
     * @param in
     */
    public List<TBBox4Open> doBusiness(InParamTBBoxQry4Open in) throws DcdzSystemException {
        return (new TBBoxQry4Open()).doBusiness(in);
    }

    /**
     * 箱门是否已经占用
     *
     * @param in
     */
    public String doBusiness(InParamPTBoxIsUsed in) throws DcdzSystemException {
        return (new PTBoxIsUsed()).doBusiness(in);
    }

    /**
     * 打开副柜维修条
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenMaintainKey in) throws DcdzSystemException {
        return (new TBOpenMaintainKey()).doBusiness(in);
    }

    /**
     * 复制箱体类型
     *
     * @param in
     */
    public String doBusiness(InParamTBCopyBoxType in) throws DcdzSystemException {
        return (new TBCopyBoxType()).doBusiness(in);
    }

    /**
     * 是否存在主控箱
     *
     * @param in
     */
    public int doBusiness(InParamTBIsExistMasterType in) throws DcdzSystemException {
        return (new TBIsExistMasterType()).doBusiness(in);
    }

    /**
     * 箱体高度设置
     *
     * @param in
     */
    public String doBusiness(InParamTBBoxHeightSet in) throws DcdzSystemException {
        return (new TBBoxHeightSet()).doBusiness(in);
    }

    /**
     * 箱体高度查询
     *
     * @param in
     */
    public OutParamTBBoxHeightQry doBusiness(InParamTBBoxHeightQry in) throws DcdzSystemException {
        return (new TBBoxHeightQry()).doBusiness(in);
    }

    //#endregion

    //#region 投递相关本地业务

    /**
     * 本地验证包裹是否已经投递
     *
     * @param in
     */
    public OutParamPTPackIsDelivery doBusiness(InParamPTPackIsDelivery in) throws DcdzSystemException {
        return (new PTPackIsDelivery()).doBusiness(in);
    }

    /**
     * 过滤服务器下载到的在箱包裹信息
     *
     * @param in
     */
    public String doBusiness(InParamPTPackLocalFilter in) throws DcdzSystemException {
        return (new PTPackLocalFilter()).doBusiness(in);
    }

    /**
     * 本地验证逾期件是否允许取回
     *
     * @param in
     */
    public OutParamPTCheckWithdrawPack doBusiness(InParamPTCheckWithdrawPack in) throws DcdzSystemException {
        return (new PTCheckWithdrawPack()).doBusiness(in);
    }

    /**
     * 逾期包裹本地过滤
     *
     * @param in
     */
    public String doBusiness(InParamPTExpiredPackLocalFilter in) throws DcdzSystemException {
        return (new PTExpiredPackLocalFilter()).doBusiness(in);
    }

    /**
     * 逾期包裹本地过滤
     *
     * @param in
     */
    /*public String doBusiness(InParamPTPreDeliveryHandle in) throws DcdzSystemException {
        return (new PTPreDeliveryHandle()).doBusiness(in);
    }*/

    /**
     * 投递员设置箱门故障
     *
     * @param in
     */
    public String doBusiness(InParamPTSetBoxFault in) throws DcdzSystemException {
        return (new PTSetBoxFault()).doBusiness(in);
    }

    /**
     * 取件输错密码锁定
     *
     * @param in
     */
    public String doBusiness(InParamPTLockUser4WrongPwd in) throws DcdzSystemException {
        return (new PTLockUser4WrongPwd()).doBusiness(in);
    }

    /**
     * 投递记录查询
     *
     * @param in
     */
    public List<OutParamPTDeliveryRecordQry> doBusiness(InParamPTDeliveryRecordQry in) throws DcdzSystemException {
        return (new PTDeliveryRecordQry()).doBusiness(in);
    }

    /**
     * 投递记录查询数量
     *
     * @param in
     */
    public int doBusiness(InParamPTDeliveryRecordQryCount in) throws DcdzSystemException {
        return (new PTDeliveryRecordQryCount()).doBusiness(in);
    }

    /**
     * 单个投递记录查询
     *
     * @param in
     */
    public OutParamPTOneDeliveryRecordQry doBusiness(InParamPTOneDeliveryRecordQry in) throws DcdzSystemException {
        return (new PTOneDeliveryRecordQry()).doBusiness(in);
    }

    /**
     * 发送拍照信息给监控设备
     *
     * @param in
     */
    public OutParamPTSendCamaraInfo doBusiness(InParamPTSendCamaraInfo in) throws DcdzSystemException {
        return (new PTSendCamaraInfo()).doBusiness(in);
    }

    /**
     * 插入上传对账文件日志
     *
     * @param in
     */
    /*public String doBusiness(InParamMBInsertUploadedInboxLog in) throws DcdzSystemException {
        return (new MBInsertUploadedInboxLog()).doBusiness(in);
    }*/

    /**
     * 是否已经上传对账文件
     *
     * @param in
     */
   /* public String doBusiness(InParamMBIsUploadedInboxLog in) throws DcdzSystemException {
        return (new MBIsUploadedInboxLog()).doBusiness(in);
    }*/

    //#endregion

    //#region 开箱业务

    /**
     * 管理员开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenBox4Manager in) throws DcdzSystemException {
        return (new TBOpenBox4Manager()).doBusiness(in);
    }

    /**
     * 投递开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenBox4Delivery in) throws DcdzSystemException {
        return (new TBOpenBox4Delivery()).doBusiness(in);
    }

    /**
     * 寄存开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenBox4Deposit in) throws DcdzSystemException {
        return (new TBOpenBox4Deposit()).doBusiness(in);
    }

    /**
     * 回收开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenBox4Recovery in) throws DcdzSystemException {
        return (new TBOpenBox4Recovery()).doBusiness(in);
    }

    /**
     * 取件开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBOpenBox4Pickup in) throws DcdzSystemException {
        return (new TBOpenBox4Pickup()).doBusiness(in);
    }


    /**
     * 投递员再次开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBReOpenBox4Postman in) throws DcdzSystemException {
        return (new TBReOpenBox4Postman()).doBusiness(in);
    }

    /**
     * 投递员取消投递再次开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBReOpenBox4Cancel in) throws DcdzSystemException {
        return (new TBReOpenBox4Cancel()).doBusiness(in);
    }

    /**
     * 取件重新开箱
     *
     * @param in
     */
    public String doBusiness(InParamTBReOpenBox4Pickup in) throws DcdzSystemException {
        return (new TBReOpenBox4Pickup()).doBusiness(in);
    }


    //#endregion


    /////////////////收到服务端响应消息的业务处理(客户发起的数据同步业务)

    /**
     * 用户开箱密码入库
     *
     * @param in
     */
    public String doBusiness(InParamSCWriteOpenBoxKey in) throws DcdzSystemException {
        return (new SCWriteOpenBoxKey()).doBusiness(in);
    }

    /**
     * 业务回传同步交易时间
     *
     * @param in
     */
    public String doBusiness(InParamSCWriteServerTime in) throws DcdzSystemException {
        return (new SCWriteServerTime()).doBusiness(in);
    }

    /**
     * 清除数据上传队列
     *
     * @param in
     */
    public String doBusiness(InParamSCDelUploadDataQueue in) throws DcdzSystemException {
        return (new SCDelUploadDataQueue()).doBusiness(in);
    }

    /**
     * 修改数据上传队列
     *
     * @param in
     */
    public String doBusiness(InParamSCModUploadDataQueue in) throws DcdzSystemException {
        return (new SCModUploadDataQueue()).doBusiness(in);
    }

    //#endregion

    /////////////////////收到服务端推送消息的业务处理

    /**
     * 删除订单
     *
     * @param in
     */
    public String doBusiness(InParamPTDelPackage in) throws DcdzSystemException {
        return (new PTDelPackage()).doBusiness(in);
    }

    /**
     * 修改订单超时时间
     *
     * @param in
     */
    public String doBusiness(InParamPTModExpiredTime in) throws DcdzSystemException {
        return (new PTModExpiredTime()).doBusiness(in);
    }

    /**
     * 修改订单状态
     *
     * @param in
     */
    public String doBusiness(InParamPTModPackageStatus in) throws DcdzSystemException {
        return (new PTModPackageStatus()).doBusiness(in);
    }

    /**
     * 修改订单公司编号和取件密码
     *
     * @param in
     */
    public String doBusiness(InParamPTModPackageInfo in) throws DcdzSystemException {
        return (new PTModPackageInfo()).doBusiness(in);
    }

    /**
     * 重置提货码
     *
     * @param in
     */
    public String doBusiness(InParamPTResetOpenBoxKey in) throws DcdzSystemException {
        return (new PTResetOpenBoxKey()).doBusiness(in);
    }

    /**
     * 推送预约订单
     *
     * @param in
     */
    public String doBusiness(InParamPTPushSubscribeOrder in) throws DcdzSystemException {
        return (new PTPushSubscribeOrder()).doBusiness(in);
    }

    /**
     * 用户APP开箱
     *
     * @param in
     */
    /*public String doBusiness(InParamAPManagerAppOpenBox in) throws DcdzSystemException {
        return (new APManagerAppOpenBox()).doBusiness(in);
    }*/

    /**
     * 管理员APP开箱
     *
     * @param in
     */
    public String doBusiness(InParamAPCustomerAppOpenBox in) throws DcdzSystemException {
        return (new APCustomerAppOpenBox()).doBusiness(in);
    }

    /**
     * 投递员APP取回
     *
     * @param in
     */
    public String doBusiness(InParamAPPostmanAppTakeout in) throws DcdzSystemException {
        return (new APPostmanAppTakeout()).doBusiness(in);
    }

    /**
     * 投递员APP投递开箱
     *
     * @param in
     */
    /*public String doBusiness(InParamAPPostmanAppOpenBox in) throws DcdzSystemException {
        return (new APPostmanAppOpenBox()).doBusiness(in);
    }*/

    /**
     * 投递员APP确认投递
     *
     * @param in
     */
    /*public String doBusiness(InParamAPPostmanDeliveryPackage in) throws DcdzSystemException {
        return (new APPostmanDeliveryPackage()).doBusiness(in);
    }*/

    /**
     * 投递员APP取消投递
     *
     * @param in
     */
    /*public String doBusiness(InParamAPPostmanDeliveryCancel in) throws DcdzSystemException {
        return (new APPostmanDeliveryCancel()).doBusiness(in);
    }*/

    /**
     * 推送LED消息
     *
     * @param in
     */
    public String doBusiness(InParamMBPushTerminalLedMsg in) throws DcdzSystemException {
        return (new MBPushTerminalLedMsg()).doBusiness(in);
    }

    /**
     * 再次投递在箱包裹(运营)
     *
     * @param in
     */
    public String doBusiness(InParamMBDeliverInboxPac in) throws DcdzSystemException {
        return (new MBDeliverInboxPac()).doBusiness(in);
    }

    /**
     * 恢复自动锁定格口状态
     *
     * @param in
     */
    public String doBusiness(InParamTBAutoLockRevert in) throws DcdzSystemException {
        return (new TBAutoLockRevert()).doBusiness(in);
    }

    /**
     * 管理员远程重启工控
     *
     * @param in
     */
    /*public String doBusiness(InParamAPRemoteRestartPC in) throws DcdzSystemException {
        return (new APRemoteRestartPC()).doBusiness(in);
    }*/
    //#endregion

    ///////////////////////////运营相关业务

    /**
     * 修改设备注册注册标志
     *
     * @param in
     */
    public String doBusiness(InParamMBModRegisterFlag in) throws DcdzSystemException {
        return (new MBModRegisterFlag()).doBusiness(in);
    }


    /**
     * 服务器推送登录
     *
     * @param in
     */
    public OutParamAPPostmanLogin doBusiness(InParamAPPostmanLogin in) throws DcdzSystemException {
        return (new APPostmanLogin()).doBusiness(in);
    }

    /**
     * 服务器推送升级
     *
     * @param in
     */
    public String doBusiness(InParamMBSendUpgradeInform in) throws DcdzSystemException {
        return (new MBSendUpgradeInform()).doBusiness(in);
    }
    ///////////////////////远程求助

    /**
     * 本地验证用户请求的合法性
     *
     * @param in
     */
    public OutParamRMCheckCustomerRequest doBusiness(InParamRMCheckCustomerRequest in) throws DcdzSystemException {
        return (new RMCheckCustomerRequest()).doBusiness(in);
    }

    /**
     * 本地验证投递员请求的合法性
     *
     * @param in
     */
    public OutParamRMCheckPostmanRequest doBusiness(InParamRMCheckPostmanRequest in) throws DcdzSystemException {
        return (new RMCheckPostmanRequest()).doBusiness(in);
    }

    /**
     * 记录开箱日志流水
     *
     * @param in
     */
    public String doBusiness(InParamRMOpenBoxRecord in) throws DcdzSystemException {
        return (new RMOpenBoxRecord()).doBusiness(in);
    }

    /**
     * 写入本地开箱密码
     *
     * @param in
     */
    public String doBusiness(InParamRMWriteLocalOpenBoxKey in) throws DcdzSystemException {
        return (new RMWriteLocalOpenBoxKey()).doBusiness(in);
    }

    /**
     * 远程求助开箱
     *
     * @param in
     */
    public String doBusiness(InParamRMRemoteOpenBox in) throws DcdzSystemException {
        return (new RMRemoteOpenBox()).doBusiness(in);
    }

    /**
     * 是否显示取件控制界面
     *
     * @param in
     */
    public OutParamPTIsShowLimitPage doBusiness(InParamPTIsShowLimitPage in) throws DcdzSystemException {
        return (new PTIsShowLimitPage()).doBusiness(in);
    }

    /**
     * 取件时间查询
     *
     * @param in
     */
    public OutParamPTTakeoutTimeQry doBusiness(InParamPTTakeoutTimeQry in) throws DcdzSystemException {
        return (new PTTakeoutTimeQry()).doBusiness(in);
    }

    /**
     * 存放推送开箱
     *
     * @param in
     */
    public Boolean doBusiness(InParamAPStoreInOpenBox in) throws DcdzSystemException {
        return (new APStoreInOpenBox()).doBusiness(in);
    }

    /**
     * 取回推送开箱
     *
     * @param in
     */
    public Boolean doBusiness(InParamAPTakeOutOpenBox in) throws DcdzSystemException {
        return (new APTakeOutOpenBox()).doBusiness(in);
    }

    //#endregion
}
