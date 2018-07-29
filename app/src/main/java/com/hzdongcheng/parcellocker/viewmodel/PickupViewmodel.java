package com.hzdongcheng.parcellocker.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.LifecycleObserver;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTPickupPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTVerfiyUser;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Pickup;
import com.hzdongcheng.bll.basic.dto.OutParamPTVerfiyUser;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.parcellocker.model.PickupModel;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.SoundUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverPackageFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupCodeFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupOpenedFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupOpeningFragment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PickupViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(this.getClass());
    public PickupModel pickupModel=new PickupModel();

    /**
     * 继续取件
     */
    public void pickupContinue() {
        pickupModel.getOpenBoxCode().postValue("");
        pickupModel.getInputError().postValue("");
        pickupModel.getCurrentFragment().postValue(PickupCodeFragment.class);
    }

    /**
     * 返回主页
     */
    private void goHome() {

    }

    /**
     * 确认取件
     */
    public void pickupSubmit() {
        pickupModel.getCurrentFragment().postValue(PickupOpenedFragment.class);
        pickupModel.getBoxOpenSuccess().postValue("02");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        log.info("[UI]-->【进入取件界面】");
        pickupModel.getCurrentFragment().postValue(PickupCodeFragment.class);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        log.info("[UI]-->【取件界面销毁】");
        onCleared();
    }
    /**
     * 清理数据
     */
    @Override
    protected void onCleared() {
        pickupModel.getOpenBoxCode().postValue("");
        pickupModel.getInputError().postValue("");
        pickupModel.getBoxOpenSuccess().postValue("");
        pickupModel.outParamPTVerfiyUser = null;
        pickupModel.getCurrentFragment().postValue(null);
    }
    /**
     * 取件密码验证
     */
    public void doPickupCheck() {
        InParamPTVerfiyUser inParam = new InParamPTVerfiyUser();
        inParam.OpenBoxKey = pickupModel.getOpenBoxCode().getValue();

        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult result = (JsonResult) data;
                pickupModel.outParamPTVerfiyUser = result.Eval(OutParamPTVerfiyUser.class);
                if (SysDict.PAY_FLAG_NO.equals(pickupModel.outParamPTVerfiyUser.PosPayFlag)) {
                    //不需要支付
                    if (SysDict.PAY_FLAG_YES.equals(pickupModel.outParamPTVerfiyUser.OverPayFlag)) {
                        //逾期费用
                        overdueCharge();
                    } else {
                        //开箱取件
                        openBox4Pickup();
                    }
                }
            }

            @Override
            public void fault(Object info) {
                if (info != null) {
                    FaultResult faultResult = (FaultResult) info;
                    pickupModel.getInputError().postValue(ResourceUtils.getString(faultResult.faultCode));
                    switch (faultResult.faultCode) {
                        case "E33003":
                            SoundUtils.getInstance().play(23);
                            break;
                        case "E500":
                        case "E501":
                        case "E505":
                        case "E507":
                            SoundUtils.getInstance().play(27);
                            break;
                        default:
                            //24，提货码错误，请重试
                            SoundUtils.getInstance().play(24);
                            break;
                    }
                }
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam, responder, 200);
        } catch (DcdzSystemException e) {
            log.error("用户取件出错，错误信息：" + e.getMessage());
        }
    }

    private void overdueCharge() {
        //TODO 逾期收费
    }

    /**
     * 取件开箱
     */
    private void openBox4Pickup() {
        InParamTBOpenBox4Pickup inParam = new InParamTBOpenBox4Pickup();
        inParam.BoxNo = pickupModel.outParamPTVerfiyUser.BoxNo;
        inParam.PackageID = pickupModel.outParamPTVerfiyUser.PackageID;
        //TODO 取件拍照
        try {
            pickupModel.getCurrentFragment().postValue(PickupOpeningFragment.class);
            DBSContext.localContext.doBusiness(inParam);
            Thread.sleep(1000);
            BoxStatus boxStatus = HAL.getBoxStatus(inParam.BoxNo);
            if (boxStatus.getOpenStatus() == 0) {
                DBSContext.localContext.doBusiness(inParam);
                Thread.sleep(1000);
                boxStatus = HAL.getBoxStatus(inParam.BoxNo);
                if (boxStatus.getOpenStatus() == 0) {
                    log.error("取件开箱失败，检测不到开箱");
                    pickupModel.getCurrentFragment().postValue(PickupCodeFragment.class);
                    pickupModel.getInputError().postValue("箱门开箱失败，请再次开箱！");
                    return ;
                }
            }
            // 取件完成
            InParamPTPickupPackage inParamPTPickupPackage = new InParamPTPickupPackage();
            inParamPTPickupPackage.PackageID = pickupModel.outParamPTVerfiyUser.PackageID;
            inParamPTPickupPackage.CustomerMobile = pickupModel.outParamPTVerfiyUser.CustomerTel;
            inParamPTPickupPackage.BoxNo = pickupModel.outParamPTVerfiyUser.BoxNo;
            inParamPTPickupPackage.OpenBoxKey = pickupModel.getOpenBoxCode().getValue();
            inParamPTPickupPackage.TakedWay = pickupModel.pickupWay;
            inParamPTPickupPackage.OverdueCost =pickupModel.outParamPTVerfiyUser.ExpiredAmt;
            DBSContext.remoteContext.doBusiness(inParamPTPickupPackage, null, 20);
            SoundUtils.getInstance().play(8);
            pickupModel.getBoxOpenSuccess().setValue(inParam.BoxNo);
            pickupModel.getCurrentFragment().postValue(PickupOpenedFragment.class);
        } catch (DcdzSystemException e) {
            pickupModel.getCurrentFragment().postValue(PickupCodeFragment.class);
            log.error("取件开箱失败，错误信息：" + e.getMessage());
            pickupModel.getInputError().postValue(ResourceUtils.getString(e.getErrorCode()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
