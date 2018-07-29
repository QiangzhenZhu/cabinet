package com.hzdongcheng.bll.monitors;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBHeartBeat;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamSMGetUpgradeInfo;
import com.hzdongcheng.bll.basic.dto.OutParamMBHeartBeat;
import com.hzdongcheng.bll.basic.dto.OutParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.OutParamSMGetUpgradeInfo;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.basic.dto.InParamMBHeartBeat;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.DBWrapper;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TimingTask {
    private static Log4jUtils log = Log4jUtils.createInstanse(TimingTask.class);
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private static ScheduledFuture<?> heartBeatFuture;
    private static ScheduledFuture<?> transportFuture;
    private static ScheduledFuture<?> upgradeInfoFuture;

    private static Context mContext;

    /**
     * 启动线程
     */
    public static void transport(Context context) {
        mContext = context;
        if (transportFuture == null || transportFuture.isDone()) {
            transportFuture = executorService.scheduleWithFixedDelay(doTransport, 5, 2, TimeUnit.MINUTES);
            log.info("[TimingDetect] 数据上传线程启动");
        }
        if (heartBeatFuture == null || heartBeatFuture.isDone()) {
            heartBeatFuture = executorService.scheduleWithFixedDelay(doHeartBeat, 5 * 60, 5 * 60, TimeUnit.SECONDS);
            log.info("[TimingDetect] 心跳线程启动，心跳间隔 " + 5 * 60);
        }
        if (upgradeInfoFuture == null || upgradeInfoFuture.isDone()) {
            upgradeInfoFuture = executorService.scheduleWithFixedDelay(doupgrade, 1, 30, TimeUnit.MINUTES);
            log.info("[TimingDetect] 更新线程启动");
        }
    }

    private static Runnable doTransport = new Runnable() {
        @Override
        public void run() {
            SyncDataProc.syncDataProcess();
        }
    };

    private static Runnable doHeartBeat = new Runnable() {
        @Override
        public void run() {
            IResponder responder = new IResponder() {
                @Override
                public void result(Object data) {
                    if (data != null) {
                    }
                }

                @Override
                public void fault(Object info) {
                    if (info != null) {
                        FaultResult faultResult = (FaultResult) info;
                        log.error("[TimingDetect] 发送心跳包失败" + faultResult.getFaultString());
                    }
                }
            };

            InParamMBHeartBeat inParam = new InParamMBHeartBeat();

            try {
                DBSContext.remoteContext.doBusiness(inParam, responder, 30);
            } catch (DcdzSystemException e) {
                log.error("[TimingDetect] 发送心跳包异常" + e.getErrorTitle());
            }
        }
    };


    private static Runnable doupgrade = new Runnable() {
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour == 1) {
                Upgradesoftware("1");
                Upgradesoftware("2");
            }
        }
    };
    //下载更新信息
    public static void Upgradesoftware(final String SoftwareType) {
        final InParamSMGetUpgradeInfo inParam = new InParamSMGetUpgradeInfo();
        inParam.SoftwareType = SoftwareType;
        inParam.SoftwareVersion = SoftwareType=="1"?DBSContext.driverVersion:DBSContext.currentVersion;
        try {
            DBSContext.remoteContext.doBusiness(inParam, new IResponder() {
                @Override
                public void result(Object data) {
                    JsonResult result = (JsonResult) data;
                    OutParamSMGetUpgradeInfo outParam = result.Eval(OutParamSMGetUpgradeInfo.class);
                    if (!Objects.equals("", outParam.DownloadUrl)) {
                        Intent startIntent = new Intent();
                        startIntent.putExtra("dowload_url", outParam.DownloadUrl);
                        startIntent.putExtra("md5", outParam.SoftSignMd5.toLowerCase());
                        startIntent.putExtra("force_start", SoftwareType=="1"?false:true);
                        startIntent.putExtra("package_name", SoftwareType=="1"?"com.hzdongcheng.drivers":"com.hzdongcheng.parcellocker");
                        startIntent.setAction("com.hzdongcheng.dzapplicationmanager.action.updateservices");
                        startIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        mContext.startService(startIntent);
                        log.error("发送升级命令" +outParam.DownloadUrl +outParam.SoftSignMd5);
                    }
                }

                @Override
                public void fault(Object data) {
                    if (data != null) {
                        FaultResult result = (FaultResult) data;
                        log.info("获取更新信息失败," + result.getFaultString());
                    }
                }
            }, 30);
        } catch (DcdzSystemException e) {
            log.error("[TimingDetect] 获取app更新信息异常" + e.getErrorTitle());
        } catch (Exception e) {
            log.error("[TimingDetect] 获取app更新信息异常" + e.getMessage());
        }
    }

}
