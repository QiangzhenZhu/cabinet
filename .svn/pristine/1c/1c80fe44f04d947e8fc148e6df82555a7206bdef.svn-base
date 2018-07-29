package com.hzdongcheng.bll.monitors;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBUploadCloseBox;
import com.hzdongcheng.bll.basic.dto.InParamMBUploadOpenBox;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.device.bean.ICallBack;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimingDetect {
    private static Log4jUtils log = Log4jUtils.createInstanse(TimingDetect.class);
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static ScheduledFuture<?> heartBeatFuture;
    public static List<BoxDetectInfo> boxInfoList = new CopyOnWriteArrayList<>();

    static Map<String, ICallBack> openBoxCallBackMap = new LinkedHashMap<>();

    public static void detectStart(long delay) {
        if (heartBeatFuture == null || heartBeatFuture.isDone()) {
            heartBeatFuture = executorService.scheduleWithFixedDelay(doCabinetDetect, delay, delay, TimeUnit.SECONDS);
            log.info("[TimingDetect]柜子检测线程启动，间隔 " + delay);
        }
    }
    //开门上传
    private static void openBoxUpload(BoxDetectInfo boxDetectInfo){
        InParamMBUploadOpenBox inParam=new InParamMBUploadOpenBox();
        inParam.BoxNo=boxDetectInfo.BoxNo;
        inParam.TradeWaterNo=boxDetectInfo.TradeWaterNo;
        inParam.OperID=boxDetectInfo.OperID;
        inParam.OpenBoxType=boxDetectInfo.OperationType;
        inParam.ArticleStatus=boxDetectInfo.ArticleStatus;
        inParam.DoorStatus=boxDetectInfo.DoorStatus;
        inParam.OccurTime= DateUtils.nowDateTimeToString();
        try {
            DBSContext.remoteContext.doBusiness(inParam, new IResponder() {
                @Override
                public void result(Object data) {

                }

                @Override
                public void fault(Object data) {

                }
            },20);
        } catch (DcdzSystemException e) {
            log.info("上传开门记录失败错误" +e);
        }

    }
    //关门上传
    private static void closeBoxUpload(BoxDetectInfo boxDetectInfo){
        InParamMBUploadCloseBox inParam=new InParamMBUploadCloseBox();
        inParam.BoxNo=boxDetectInfo.BoxNo;
        inParam.TradeWaterNo=boxDetectInfo.TradeWaterNo;
        inParam.OperID=boxDetectInfo.OperID;
        inParam.CloseBoxType=boxDetectInfo.OperationType;
        inParam.ArticleStatus=boxDetectInfo.ArticleStatus;
        inParam.DoorStatus=boxDetectInfo.DoorStatus;
        inParam.OccurTime= DateUtils.nowDateTimeToString();
        try {
            DBSContext.remoteContext.doBusiness(inParam, new IResponder() {
                @Override
                public void result(Object data) {

                }

                @Override
                public void fault(Object data) {

                }
            },20);
        } catch (DcdzSystemException e) {
            log.info("上传关门记录失败错误" +e);
        }
    }

    private static Runnable doCabinetDetect = new Runnable() {
        @Override
        public void run() {
            if (boxInfoList.size() > 0) {
               for (BoxDetectInfo boxDetectInfo:boxInfoList) {
                   String boxNo = boxDetectInfo.BoxNo;
                   boxDetectInfo.Count++;
                   try {
                       BoxStatus boxStatus = HAL.getBoxStatus(boxNo);
                       boxDetectInfo.ArticleStatus = boxStatus.getGoodsStatus() + "";
                       boxDetectInfo.DoorStatus = boxStatus.getOpenStatus() + "";
                       if (boxDetectInfo.Count > 5 && !boxDetectInfo.OpenStatue) {
                           openBoxUpload(boxDetectInfo);
                           boxDetectInfo.OpenStatue = true;
                       }
                       if (boxStatus.getOpenStatus() == 1 && !boxDetectInfo.OpenStatue) {
                           openBoxUpload(boxDetectInfo);
                           boxDetectInfo.OpenStatue = true;
                       }
                       if (boxStatus.getOpenStatus() == 0 && boxDetectInfo.OpenStatue) {
                           closeBoxUpload(boxDetectInfo);
                           boxInfoList.remove(boxDetectInfo);
                       }
                       if (boxDetectInfo.Count == 300) {
                           closeBoxUpload(boxDetectInfo);
                           boxInfoList.remove(boxDetectInfo);
                       }
                   } catch (DcdzSystemException e) {
                       boxInfoList.remove(boxDetectInfo);
                       heartBeatFuture.cancel(true);
                       log.info("[TimingDetect]获取箱门状态错误，箱号" + boxNo);
                   }
               }
            }else {
                heartBeatFuture.cancel(true);
            }
        }
    };
}