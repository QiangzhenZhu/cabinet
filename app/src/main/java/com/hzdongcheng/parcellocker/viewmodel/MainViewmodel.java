package com.hzdongcheng.parcellocker.viewmodel;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBDeviceRegister;
import com.hzdongcheng.bll.basic.dto.InParamTBNetworkParamMod;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalAdd;
import com.hzdongcheng.bll.basic.dto.OutParamMBDeviceRegister;
import com.hzdongcheng.bll.basic.dto.OutParamPTMobileBlackList;
import com.hzdongcheng.bll.basic.dto.OutParamPTPackageDetail;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.monitors.TimingTask;
import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.bll.websocket.SocketClient;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.parcellocker.DBSApplication;
import com.hzdongcheng.parcellocker.model.MainModel;
import com.hzdongcheng.parcellocker.utils.ResourceUtils;
import com.hzdongcheng.parcellocker.utils.SoundUtils;
import com.hzdongcheng.parcellocker.utils.WrapperFragment;
import com.hzdongcheng.parcellocker.views.deliver.DeliverHolderFragment;
import com.hzdongcheng.parcellocker.views.manage.ManageHolderFragment;
import com.hzdongcheng.parcellocker.views.navigate.NavigateHomeFragment;
import com.hzdongcheng.parcellocker.views.navigate.NavigateLoaderFragment;
import com.hzdongcheng.parcellocker.views.navigate.NavigateRegisterFragment;
import com.hzdongcheng.parcellocker.views.pickup.PickupHolderFragment;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class MainViewmodel extends ViewModel implements LifecycleObserver {
    private Log4jUtils log = Log4jUtils.createInstanse(MainViewmodel.class);
    public MainModel mainModel=new MainModel();

    /**
     * Lifecycle.Event 运行在主线程
     *
     * @param owner
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        log.debug("[model] -->界面onCreate通知" + owner.toString());
        mainModel.getCurrentFragment().postValue(NavigateLoaderFragment.class);
        if (AsyncTask.Status.PENDING.equals(loadAsyncTask.getStatus()))
            loadAsyncTask.execute("");
    }

    private LoadAsyncTask loadAsyncTask = new LoadAsyncTask();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume(LifecycleOwner owner) {
        log.debug("[model] -->主界面加载成功");

    }


    public void performPickup() {
        if (mainModel.getCurrentFragment().getValue() == NavigateHomeFragment.class)
            mainModel.getCurrentFragment().postValue(PickupHolderFragment.class);
    }

    public void performDelivery() {

        if (mainModel.getCurrentFragment().getValue() == NavigateHomeFragment.class)
            mainModel.getCurrentFragment().postValue(DeliverHolderFragment.class);
    }

    public void performQuery() {
        if (mainModel.getCurrentFragment().getValue() == NavigateHomeFragment.class)
            mainModel.getCurrentFragment().postValue(PickupHolderFragment.class);
    }

    public void performManager() {
        if (mainModel.getCurrentFragment().getValue() == NavigateHomeFragment.class)
            mainModel.getCurrentFragment().postValue(ManageHolderFragment.class);
    }

    public void deviceRegister(){

        final InParamMBDeviceRegister inParam = new InParamMBDeviceRegister();
        inParam.SignMac= NetUtils.getMac();
        inParam.SoftwareVersion= mainModel.getVersion().getValue();
        inParam.InitPasswd=mainModel.getInitPasswd().getValue();
        inParam.ServerUrl=mainModel.getServerUrl().getValue();
        IResponder responder = new IResponder() {
            @Override
            public void result(Object data) {
                JsonResult jsonResult = (JsonResult) data;
                OutParamMBDeviceRegister outParam = jsonResult.Eval(OutParamMBDeviceRegister.class);
                if (!Objects.equals("",outParam.TerminalNo)){
                    InParamTBTerminalAdd terminalAdd = new InParamTBTerminalAdd();
                    terminalAdd.OperID="register";
                    terminalAdd.TerminalNo = outParam.TerminalNo;
                    terminalAdd.TerminalName = outParam.TerminalName;
                    terminalAdd.Location =outParam.Location;
                    terminalAdd.MBDeviceNo=outParam.MBDeviceNo;
                    terminalAdd.DepartmentID=outParam.DepartmentID;
                    try {
                        DBSContext.localContext.doBusiness(terminalAdd);
                    } catch (DcdzSystemException e) {
                        log.error("保存设备信息错误 " + e.getErrorTitle());
                    }

                    InParamTBNetworkParamMod inParamMod = new InParamTBNetworkParamMod();
                    inParamMod.OperID="register";
                    inParamMod.SystemID=outParam.SystemID;
                    inParamMod.ServerIP = outParam.ServerUrl.split(":")[0];
                    inParamMod.ServerPort = Integer.parseInt(outParam.ServerUrl.split(":")[1]);
                    inParamMod.MonServerIP =inParam.ServerUrl.split(":")[0];
                    inParamMod.MonServerPort = Integer.parseInt(inParam.ServerUrl.split(":")[1]);
                    inParam.InitPasswd=outParam.InitPasswd;
                    try {
                        DBSContext.localContext.doBusiness(inParamMod);
                        SocketClient.stop();
                        SocketClient.start();
                    } catch (DcdzSystemException e) {
                        log.error("修改服务器地址错误 " + e.getErrorTitle());
                    }
                    LoadAsyncTask loadAsyncTask = new LoadAsyncTask();
                    if (AsyncTask.Status.PENDING.equals(loadAsyncTask.getStatus()))
                        loadAsyncTask.execute("");

                }
            }
            @Override
            public void fault(Object info) {
                if (info != null) {
                    FaultResult result = (FaultResult) info;
                    log.info("设备注册失败," + result.getFaultString());
                }
            }
        };
        try {
            DBSContext.remoteContext.doBusiness(inParam,responder,30);
        } catch (DcdzSystemException e) {
            mainModel.getProcessTips().postValue(e.getMessage());
            log.error("设备注册错误"+ e.getMessage());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        loadAsyncTask.cancel(true);
    }

    class LoadAsyncTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                loadMainData();
                log.info("开始初始化数据库");
                publishProgress("开始初始化数据库");
                DBSContext.initDatabase();
                publishProgress("初始化数据库成功");
                DBSContext.deviceFault = false;
            } catch (DcdzSystemException e) {
                DBSContext.deviceFault = true;
                log.error("初始化数据库失败,系统初始化中止 >>"+ e.getMessage());
                return false;
            }

            publishProgress("开始初始化网络");

            SocketClient.setOnStateChanged(new Observer() {
                @Override
                public void update(Observable o, Object arg) {
                    mainModel.getNetworkState().postValue((Boolean) arg);
                }
            });
            SocketClient.start();
            publishProgress("初始化网络成功");

            publishProgress("开始初始化设备");
            HAL.init(DBSApplication.getContext());
            publishProgress("初始化设备成功");

            SoundUtils.getInstance().loadSound(DBSApplication.getContext());

            return  true;
        }

        private  void loadMainData(){
            final PackageManager pm = DBSApplication.getContext().getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(DBSApplication.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
                DBSContext.currentVersion=pi.versionName;
                mainModel.getVersion().postValue(DBSContext.currentVersion);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            mainModel.getVersion().postValue(DBSContext.currentVersion);
            mainModel.getDeviceCode().postValue(DBSContext.terminalUid);
            mainModel.getServerHot().postValue(DBSContext.ctrlParam.serviceTel);
        }
        @Override
        protected void onProgressUpdate(String... values) {
            mainModel.getProcessTips().postValue(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (DBSContext.terminalUid==null||Objects.equals("",DBSContext.terminalUid)) {
                mainModel.getProcessTips().setValue("");
                mainModel.getCurrentFragment().postValue(NavigateRegisterFragment.class);
            }
            else
                mainModel.getCurrentFragment().postValue(NavigateHomeFragment.class);
        }
    }
}
