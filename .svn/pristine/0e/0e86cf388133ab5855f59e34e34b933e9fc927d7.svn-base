package com.hzdongcheng.parcellocker.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTAllocateeBox;
import com.hzdongcheng.bll.basic.dto.InParamPTCheckWithdrawPack;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryRecordQry;
import com.hzdongcheng.bll.basic.dto.InParamPTExpiredPackLocalFilter;
import com.hzdongcheng.bll.basic.dto.InParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.InParamPTMobileBlackList;
import com.hzdongcheng.bll.basic.dto.InParamPTPackIsDelivery;
import com.hzdongcheng.bll.basic.dto.InParamPTPackageDetail;
import com.hzdongcheng.bll.basic.dto.InParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.InParamPTReadPackageQry;
import com.hzdongcheng.bll.basic.dto.InParamPTWithdrawExpiredPack;
import com.hzdongcheng.bll.basic.dto.InParamRMOpenBoxRecord;
import com.hzdongcheng.bll.basic.dto.InParamTBFreeBoxStat;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Delivery;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Recovery;
import com.hzdongcheng.bll.basic.dto.OutParamPTAllocateeBox;
import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTMobileBlackList;
import com.hzdongcheng.bll.basic.dto.OutParamPTPackIsDelivery;
import com.hzdongcheng.bll.basic.dto.OutParamPTPackageDetail;
import com.hzdongcheng.bll.basic.dto.OutParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.OutParamPTReadPackageQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBFreeBoxStat;
import com.hzdongcheng.bll.basic.dto.PostedPackage;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxInfo;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.device.bean.ICallBack;
import com.hzdongcheng.parcellocker.R;
import com.hzdongcheng.parcellocker.model.DeliverModel;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.SoundUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverExpiredFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverHomeFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverLoginFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverOpenedFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverOpeningFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverPackageFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverRecordFragment;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class DeliverViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(DeliverViewmodel.class);
    public DeliverModel model=new DeliverModel();
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future = null;
    private AtomicBoolean onOpen=new AtomicBoolean(false);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        log.info("[UI]-->进入投递员登录界面");
        model.getCurrentFragment().postValue(DeliverLoginFragment.class);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        log.info("[UI]-->退出投递");
        onCleared();
    }
    /**
     * 清理数据
     */
    @Override
    protected void onCleared() {
        model.getUserName().postValue("");
        model.getPassword().postValue("");
        model.getCurrentFragment().postValue(null);
    }

    /*******************************************  扫描枪 ************************************/
    public void getScannerData()
    {
        HAL.setScannerCallBack(new ICallBack() {
            @Override
            public void onMessage(String data, Integer type) {
                if (onOpen.compareAndSet(false, true)) {
                    String pattern = "[0-9a-zA-Z_-]{4,20}$";
                    boolean isMatch = Pattern.matches(pattern, data);
                    if (isMatch) {
                        if (Objects.equals(model.getCurrentFragment().getValue().getSimpleName(), "DeliverPackageFragment")) {
                            model.getPackageID().postValue(data);
                            //checkOrder(data);
                        }
                    }
                    onOpen.set(false);
                }

            }
        });
    }
    public void cancelScanner(){
        HAL.setScannerCallBack(null);

    }
    /*******************************************  读卡器 ************************************/
    public void getCardReaderData()
    {
        HAL.setCardReaderCallBack(new ICallBack() {
            @Override
            public void onMessage(String data, Integer type) {

            }
        });
    }
    public void  cancelCardReader(){
        HAL.setCardReaderCallBack(null);
    }
    /*******************************************  deliver_login ************************************/
    /**
     * 投递员登录
     */
    public void login() {
        InParamPTPostmanLogin inParam = new InParamPTPostmanLogin();
        inParam.PostmanID = model.getUserName().getValue();
        inParam.Password = model.getPassword().getValue();
        inParam.VerifyFlag = SysDict.POSTMAN_VERIFY_PWD;
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult result = (JsonResult) data;
                model.postmanInfo = result.Eval(OutParamPTPostmanLogin.class);
                if (model.postmanInfo.ExpiredCount > 0 ||
                        (model.postmanInfo.ExpiredCount == -1 &&
                                !DBSContext.ctrlParam.recoverySource.equals(SysDict.RECOVERY_DELIVERY_SOURCE_NOTHING))) {
                    //网络下载逾期订单
                    downLoadOverduePackage();
                } else {
                    if (!DBSContext.ctrlParam.orderDeliverySource.equals(SysDict.ORDER_DELIVERY_SOURCE_NO))
                        downloadReadPackageInfo();
                    else
                        initDeliveryPage();
                }
            }

            @Override
            public void fault(Object info) {
                if (info != null) {
                    FaultResult result = (FaultResult) info;
                    log.info("投递员登录失败," + result.getFaultString());
                    model.getErrorTips().postValue(ResourceUtils.getString(result.faultCode));
                }
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 20);
        } catch (DcdzSystemException e) {
            log.error("投递员登录错误 " + e.getErrorTitle());
            model.getErrorTips().postValue(ResourceUtils.getString(e.getErrorCode()));
        }
    }

    /*******************************************  deliver_home  ************************************/
    /**
     * 下载待投递订单
     */
    private void downloadReadPackageInfo() {
        //等待提示框要出现
        InParamPTReadPackageQry inParam = new InParamPTReadPackageQry();
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.PostmanID = model.postmanInfo.PostmanID;
        inParam.DynamicCode = model.postmanInfo.DynamicCode;
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult jsonResult = (JsonResult) data;
                Type type = new TypeToken<List<OutParamPTReadPackageQry>>() {
                }.getType();
                model.readPackageList = jsonResult.Eval(type);
                initDeliveryPage();
            }
            @Override
            public void fault(Object info) {
                log.error("下载待投递包裹失败 " + JsonUtils.toJSONString(info));
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 10);
        } catch (DcdzSystemException e) {
            log.error("下载待投递订单错误 " + e.getErrorTitle());
            model.getCurrentFragment().postValue(DeliverHomeFragment.class);
        }
    }

    /**
     * 初始化投递界面
     */
    public void initDeliveryPage() {
        InParamTBFreeBoxStat inParam = new InParamTBFreeBoxStat();
        try {
            OutParamTBFreeBoxStat freeBoxStat = DBSContext.localContext.doBusiness(inParam);
            model.getBoxUsableNum().postValue(freeBoxStat.SmallBoxNum + freeBoxStat.MedialNum + freeBoxStat.LargeNum );
            model.getSmallBoxUsableNum().postValue(freeBoxStat.SmallBoxNum );
            model.getMiddleBoxUsableNum().postValue(freeBoxStat.MedialNum);
            model.getLargeBoxUsableNum().postValue(freeBoxStat.LargeNum );
            model.getExpirePackageNum().postValue(model.expiredPackQryList.size());
        } catch (Exception e) {
            log.error("获取可用箱门错误 " + e.getMessage());
        }
        //进入投递员主页面
        model.getCurrentFragment().postValue(DeliverHomeFragment.class);
    }

    /**
     * 网络下载逾期订单
     */
    private void downLoadOverduePackage() {
        InParamPTExpiredPackQry inParam = new InParamPTExpiredPackQry();
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.PostmanID = model.postmanInfo.PostmanID;
        inParam.ExpiredDate = model.postmanInfo.ExpiredDate;
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult jsonResult = (JsonResult) data;
                Type type = new TypeToken<List<OutParamPTExpiredPackQry>>() {
                }.getType();
                model.expiredPackQryList = jsonResult.Eval(type);
                if (model.expiredPackQryList.size() > 0 &&
                        SysDict.RECOVERY_DELIVERY_SOURCE_NETWORK.equals(DBSContext.ctrlParam.recoverySource)) {
                    InParamPTExpiredPackLocalFilter inParamPTExpiredPackLocalFilter = new InParamPTExpiredPackLocalFilter();
                    inParamPTExpiredPackLocalFilter.PostmanID = model.postmanInfo.PostmanID;
                    inParamPTExpiredPackLocalFilter.ExpiredPackList = model.expiredPackQryList;
                    model.getExpirePackageNum().postValue(model.expiredPackQryList.size());
                    try {
                        DBSContext.localContext.doBusiness(inParamPTExpiredPackLocalFilter);
                    } catch (DcdzSystemException e) {
                        model.getErrorTips().postValue(e.getErrorTitle());
                    }
//                    if (model.expiredPackQryList.size() > 0) {
//                        model.getCurrentFragment().postValue(DeliverExpiredFragment.class);
//                    } else
                        initDeliveryPage();
                }
            }
            @Override
            public void fault(Object info) {
                log.error("下载逾期包裹失败 " + JsonUtils.toJSONString(info));
                //前往投递员主界面
                model.getCurrentFragment().postValue(DeliverHomeFragment.class);
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 10);
        } catch (DcdzSystemException e) {
            log.error("下载逾期包裹错误 " + e.getErrorTitle());
            //前往投递员主界面
            model.getCurrentFragment().postValue(DeliverHomeFragment.class);
        }
    }
    /*******************************************  deliver_record  ************************************/
    /**
     * 在箱记录查询
     */
    public void getInBoxDelivery() {

        InParamPTDeliveryRecordQry inParam = new InParamPTDeliveryRecordQry();
        inParam.InboxFlag = "1";
        try {
            List<OutParamPTDeliveryRecordQry> outParams=DBSContext.localContext.doBusiness(inParam);
            if (model.expiredPackQryList!=null) {
                for (OutParamPTDeliveryRecordQry outParam : outParams) {
                    for (OutParamPTExpiredPackQry outParamPTExpiredPackQry : model.expiredPackQryList) {
                        if (outParam.PackageID == outParamPTExpiredPackQry.PID)
                            outParam.PackageStatusName = "逾期";
                    }
                }
            }
            model.getOutParamPTDeliveryRecordQrys().postValue(DBSContext.localContext.doBusiness(inParam));
        } catch (DcdzSystemException e) {
            log.error(" 在箱记录查询错误 " + e.getErrorTitle());
        }
        //前往投递记录界面
        model.getCurrentFragment().postValue(DeliverRecordFragment.class);
    }

    /**
     * 逾期开箱验证状态
     */
    public void submitRecoveryRequest(OutParamPTDeliveryRecordQry outParam) {
        String packageID = outParam.PackageID;
        if (StringUtils.isEmpty(packageID)) {
            return;
        }
        InParamPTCheckWithdrawPack inParam = new InParamPTCheckWithdrawPack();
        inParam.PostmanID = model.postmanInfo.PostmanID;
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.PackageID = packageID;
        inParam.ExpiredPackList = model.expiredPackQryList;

        try {
            if (SysDict.PACKAGE_STATUS_TIMEOUT.equals(DBSContext.localContext.doBusiness(inParam).PackageStatus))
                openBox4Recovery(outParam);
        } catch (DcdzSystemException e) {
            log.error("本地验证回收信息错误 " + e.getErrorTitle());
            return;
        }
    }

    /**
     * 回收开箱:包括逾期回收和投递员取回
     */
    public void openBox4Recovery(OutParamPTDeliveryRecordQry outParam) {
        model.outParamPTDeliveryRecordQry=outParam;
        InParamTBOpenBox4Recovery inParam = new InParamTBOpenBox4Recovery();
        inParam.PostmanID = model.postmanInfo.PostmanName;
        inParam.BoxNo = outParam.BoxNo;
        try {
            DBSContext.localContext.doBusiness(inParam);
            Thread.sleep(1000);
            BoxStatus boxStatus = HAL.getBoxStatus(inParam.BoxNo);
            if (boxStatus.getOpenStatus() == 0) {
                DBSContext.localContext.doBusiness(inParam);
                Thread.sleep(1000);
                boxStatus = HAL.getBoxStatus(inParam.BoxNo);
                if (boxStatus.getOpenStatus() == 0) {
                    log.error("回收开箱失败，检测不到开箱");
                    model.getCurrentFragment().setValue(DeliverPackageFragment.class);
                    model.getBoxName().postValue(outParam.BoxNo);
                    SoundUtils.getInstance().play(18);
                    model.getErrorTips().postValue("箱门开箱失败，请再次开箱！");
                    return ;
                }
            }
            model.getBoxName().setValue(inParam.BoxNo);
            model.getOperationType().setValue(SysDict.OPERATION_TYPE_RECYCLER);
            model.getCurrentFragment().setValue(DeliverOpenedFragment.class);
            SoundUtils.getInstance().play(13);
            insertOpenBoxRecord(SysDict.OPENBOX_TYPE_DELIVER);
            future = service.scheduleWithFixedDelay(
                    reioncaptutureRunnable, 500, 500, TimeUnit.MILLISECONDS);
        } catch (DcdzSystemException e) {
            log.error("回收开箱出错误：" + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //确认回收
    public void ensureRecovery() {
        if (future!=null)
            future.cancel(true);
        InParamPTWithdrawExpiredPack inParam = new InParamPTWithdrawExpiredPack();
        inParam.PostmanID = model.postmanInfo.PostmanID;
        inParam.DynamicCode = model.postmanInfo.DynamicCode;
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.PackageID = model.outParamPTDeliveryRecordQry.PackageID;
        inParam.BoxNo = model.outParamPTDeliveryRecordQry.BoxNo;
        try {
            if (SysDict.PACKAGE_STATUS_TIMEOUT.equals(model.outParamPTDeliveryRecordQry.PackageStatus))
                inParam.PackageStatus = SysDict.PACKAGE_STATUS_OUT4POSTMAN;
            else
                inParam.PackageStatus = SysDict.PACKAGE_STATUS_OUTEXPIRED;
            DBSContext.remoteContext.doBusiness(inParam, null, 10);
            getInBoxDelivery();
        } catch (DcdzSystemException e) {
            log.error("确认回收单错误 " + e.getErrorTitle());
        }

    }
    /*******************************************  deliver_package  ************************************/
    /**
     * 初始化订单界面
     */
    public void initPackagePage(int boxSize) {
        model.getCurrentBoxSize().setValue(boxSize);
        model.getPackageID().setValue("");
        model.getCustomerMobile().setValue("");
        model.getErrorTips().setValue("");
        model.getCurrentFragment().postValue(DeliverPackageFragment.class);
    }

    /**
     * 验证订单
     */
    public void checkOrder(String packageId) {
        model.needNetCheck = false;
        if (model.localCheck=checkOrderLocal(packageId)) {
            if (model.needNetCheck)
                checkOrderRemote();
        }
    }

    /**
     * 本地验证订单
     */
    private boolean checkOrderLocal(String packageId) {
        InParamPTPackIsDelivery inParam = new InParamPTPackIsDelivery();
        inParam.PackageID =packageId;
        inParam.ReadyPackList = model.readPackageList;
        inParam.InputMobileFlag = model.postmanInfo.InputMobileFlag;
        try {
            OutParamPTPackIsDelivery outParamPTPackIsDelivery = DBSContext.localContext.doBusiness(inParam);
            if (Objects.equals(SysDict.ORDER_NEED_NETCHCEK_YES, outParamPTPackIsDelivery.NetCheckFlag)) {
                //需要网络验证
                model.needNetCheck = true;
            } else {
                //待投递列表中存在,检查是否允许修改
            }
            return true;
        } catch (DcdzSystemException e) {
            log.error("本地验证订单错误 " + e.getErrorTitle());
            if (e.getErrorCode() == DBSErrorCode.ERR_BUSINESS_PACKDELIVERYD) {
                model.getErrorTips().postValue(ResourceUtils.getString(R.string.error_courier_package_001));
                return false;
            } else
                model.getErrorTips().postValue(ResourceUtils.getString(e.getErrorCode()));
        }
        return false;
    }

    /**
     * 远程验证订单
     */
    private void checkOrderRemote() {
        InParamPTPackageDetail inParam = new InParamPTPackageDetail();
        inParam.PackageID = model.getPackageID().getValue();
        inParam.PostmanID = model.postmanInfo.PostmanName;
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.DynamicCode = model.postmanInfo.DynamicCode;
        inParam.OfBureau = model.postmanInfo.OfBureau;
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult jsonResult = (JsonResult) data;
                OutParamPTPackageDetail packageDetail = jsonResult.Eval(OutParamPTPackageDetail.class);
                if (packageDetail.CustomerMobile != null && packageDetail.CustomerMobile.length() == 11) {
                    model.getCustomerMobile().postValue(packageDetail.CustomerMobile);
                }
                model.localCheck=true;
            }
            @Override
            public void fault(Object info) {
                model.localCheck=false;
                log.error("订单网络校验失败： " + JsonUtils.toJSONString(info));
                model.getErrorTips().postValue(ResourceUtils.getString(info.hashCode()));
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 10);
        } catch (DcdzSystemException e) {
            model.localCheck=false;
            log.error("远程验证订单错误 " + e.getErrorTitle());
            model.getErrorTips().postValue(ResourceUtils.getString(e.getErrorCode()));
        }
    }

    /**
     * 手机号远程验证
     */
    public void checkCustomerMobile() {
        if (!model.localCheck)
            return;
        //远程分配箱门
        if (Objects.equals(SysDict.ORDER_BOX_SOURCE_NETWORK, DBSContext.ctrlParam.boxallocatetype)) {
            doRemoteAllocateeBox();
        } else {
            if (Objects.equals(SysDict.ORDER_TE_SOURCE_LOCAL, DBSContext.ctrlParam.mobileBlackList)) {
                openBox4Delivery("");
            } else {
                InParamPTMobileBlackList inParam = new InParamPTMobileBlackList();
                inParam.CustomerMobile = model.getCustomerMobile().getValue();
                inParam.PostmanID = model.postmanInfo.PostmanID;
                inParam.OccurTime = DateUtils.nowDate();
                IResponder responder = new IResponder() {
                    @Override
                    public void result(Object data) {
                        JsonResult jsonResult = (JsonResult) data;
                        OutParamPTMobileBlackList outParam = jsonResult.Eval(OutParamPTMobileBlackList.class);
                        if (Objects.equals(SysDict.ORDER_TEL_SOURCE_NETWORK, outParam.InBlackList)) {
                            model.getErrorTips().postValue(ResourceUtils.getString(R.string.error_courier_telphone_001));
                        } else {
                            openBox4Delivery("");
                        }
                    }
                    @Override
                    public void fault(Object info) {
                        log.error("手机号网络校验失败： " + JsonUtils.toJSONString(info));
                        model.getErrorTips().postValue(ResourceUtils.getString(info.hashCode()));
                    }
                };
                try {
                    DBSContext.remoteContext.doBusiness(inParam, responder, 10);
                } catch (DcdzSystemException e) {
                    log.error("手机号网络校验错误 " + e.getErrorTitle());
                    model.getErrorTips().postValue(ResourceUtils.getString(e.getErrorCode()));
                }
            }
        }
    }

    /**
     * 远程分配箱门并开箱
     */
    private void doRemoteAllocateeBox() {
        InParamPTAllocateeBox inParam = new InParamPTAllocateeBox();
        inParam.PostmanID = model.postmanInfo.PostmanID;
        inParam.BoxType = model.getCurrentBoxSize().getValue().toString();
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.BoxList = model.postmanInfo.BoxList;
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult jsonResult = (JsonResult) data;
                OutParamPTAllocateeBox outParam = jsonResult.Eval(OutParamPTAllocateeBox.class);
                if ("1".equals(outParam.InBlackList)) {
                    //拒绝投递页面
                    model.getErrorTips().postValue(ResourceUtils.getString(R.string.error_courier_telphone_002));
                } else {
                    ///////////////////////////
                    if (StringUtils.isEmpty(outParam.BoxNo) || "0".equals(outParam.BoxNo)) {
                        outParam.BoxNo = "-1";
                    }
                    openBox4Delivery(outParam.BoxNo);
                }
            }
            @Override
            public void fault(Object info) {
                log.error("远程分配箱门失败： " + JsonUtils.toJSONString(info));
                model.getErrorTips().postValue(ResourceUtils.getString(info.hashCode()));
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 10);
        } catch (DcdzSystemException e) {
            log.error("远程分配箱门错误 " + e.getErrorTitle());
            model.getErrorTips().postValue(ResourceUtils.getString(e.getErrorCode()));
        }
    }

    /*******************************************  deliver_opened  ************************************/
    /**
     * 投递开箱
     *
     * @param boxList
     * @return
     */
    private boolean openBox4Delivery(String boxList) {
        String result = "";
        InParamTBOpenBox4Delivery inParam = new InParamTBOpenBox4Delivery();
        inParam.BoxType = model.getCurrentBoxSize().getValue().toString();
        inParam.PostmanID = model.postmanInfo.PostmanID;
        if (StringUtils.isNotEmpty(boxList))
            inParam.BoxList = boxList;
        else
            inParam.BoxList = model.postmanInfo.BoxList;
        try {
            model.getCurrentFragment().setValue(DeliverOpeningFragment.class);
            //投递开箱
            result = DBSContext.localContext.doBusiness(inParam);
            Thread.sleep(1000);
            BoxStatus boxStatus = HAL.getBoxStatus(result);
            if (boxStatus.getOpenStatus() == 0) {
                result = DBSContext.localContext.doBusiness(inParam);
                Thread.sleep(1000);
                boxStatus = HAL.getBoxStatus(result);
                if (boxStatus.getOpenStatus() == 0) {
                    log.error("投递开箱失败，检测不到开箱");
                    model.getCurrentFragment().setValue(DeliverPackageFragment.class);
                    model.getBoxName().postValue(boxList);
                    SoundUtils.getInstance().play(18);
                    model.getErrorTips().postValue("箱门开箱失败，请再次开箱！");
                    return false;
                }
            }
            model.getBoxName().setValue(result);
            model.getOperationType().setValue(SysDict.OPERATION_TYPE_DELIVER);
            model.getCurrentFragment().setValue(DeliverOpenedFragment.class);
            SoundUtils.getInstance().play(17);
            insertOpenBoxRecord(SysDict.OPENBOX_TYPE_DELIVER);
            future = service.scheduleWithFixedDelay(
                    futureRunnable, 500, 500, TimeUnit.MILLISECONDS);
        } catch (DcdzSystemException e) {
            log.error("投递开箱出错：" + e.getErrorTitle());
            model.getCurrentFragment().setValue(DeliverPackageFragment.class);
            model.getBoxName().postValue(boxList);
            SoundUtils.getInstance().play(18);
            model.getErrorTips().postValue("箱门开箱失败，请再次开箱！");
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 记录开箱记录
     *
     * @param openBoxType
     */
    private void insertOpenBoxRecord(String openBoxType) {
        InParamRMOpenBoxRecord inParam = new InParamRMOpenBoxRecord();
        inParam.OpenBoxUser = model.postmanInfo.PostmanID;
        inParam.OpenBoxType = openBoxType;
        inParam.BoxNo = model.getBoxName().getValue();
        inParam.PackageID = model.getPackageID().getValue();
        inParam.CustomerMobile = model.getCustomerMobile().getValue();
        try {
            DBSContext.localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            log.error("记录开箱记录出错：" +e.getErrorTitle());
        }
    }
    /**
     * 确认投递
     */
    public void ensureDelivery() {
        if (future!=null)
            future.cancel(true);
        if (StringUtils.isEmpty(model.getBoxName().getValue())
                || StringUtils.isEmpty(model.getPackageID().getValue())) {
            return;
        }
        InParamPTDeliveryPackage inParam = new InParamPTDeliveryPackage();
        inParam.PostmanID = model.postmanInfo.PostmanID;
        inParam.DynamicCode = model.postmanInfo.DynamicCode;
        inParam.CompanyID = model.postmanInfo.CompanyID;
        inParam.PackageID = model.getPackageID().getValue();
        inParam.BoxNo = model.getBoxName().getValue();
        inParam.StoredTime = DateUtils.nowDate();
        inParam.CustomerMobile = model.getCustomerMobile().getValue();
        inParam.LeftFlag = SysDict.PACKAGE_LEFT_FLAG_N0;

        if (model.readPackageList != null) {
            for (OutParamPTReadPackageQry readyPack : model.readPackageList) {
                if (readyPack.PID.equals(inParam.PackageID)) {
                    inParam.PosPayFlag = readyPack.FlG;
                }
            }
        }
        try {
            //业务处理
            DBSContext.remoteContext.doBusiness(inParam, null, 20);
            initDeliveryPage();
        } catch (DcdzSystemException e) {
            log.error("确认投递出错：" + e.getMessage());
        }
    }

    /*******************************************  箱门检测  ************************************/
    /**
     * 投递箱门检测
     *
     * @param boxList
     * @return
     */
    public Runnable futureRunnable = new Runnable() {
        @Override
        public void run() {
            String boxName=model.getBoxName().getValue();
            if(!StringUtils.isEmpty(boxName)) {
                try {
                    BoxStatus boxStatus = HAL.getBoxStatus(boxName);
                    if (("0".equals(DBSContext.ctrlParam.articleInspectFlag)&&boxStatus.getOpenStatus()==0)
                            ||("1".equals(DBSContext.ctrlParam.articleInspectFlag)&&boxStatus.getOpenStatus()==0
                                &&boxStatus.getGoodsStatus()==1))
                        ensureDelivery();
                } catch (DcdzSystemException e) {
                    log.error("获取"+boxName+"号箱门状态错误：" + e.getMessage());
                }
            }
        }
    };
    /**
     * 回收箱门检测
     *
     * @param boxList
     * @returnF
     */
    public Runnable reioncaptutureRunnable = new Runnable() {
        @Override
        public void run() {
            String boxName=model.getBoxName().getValue();
            if(!StringUtils.isEmpty(boxName)) {
                try {
                    BoxStatus boxStatus = HAL.getBoxStatus(boxName);
                    if (("0".equals(DBSContext.ctrlParam.articleInspectFlag)&&boxStatus.getOpenStatus()==0)
                            ||("1".equals(DBSContext.ctrlParam.articleInspectFlag)&&boxStatus.getOpenStatus()==0
                            &&boxStatus.getGoodsStatus()==0))
                        ensureRecovery();
                } catch (DcdzSystemException e) {
                    log.error("获取"+boxName+"号箱门状态错误：" + e.getMessage());
                }
            }
        }
    };
}
