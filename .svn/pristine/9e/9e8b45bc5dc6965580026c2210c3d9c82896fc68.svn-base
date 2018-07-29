package com.hzdongcheng.bll.monitors;


import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBModRegisterFlag;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryPackage;
import com.hzdongcheng.bll.basic.dto.InParamPTPickupPackage;
import com.hzdongcheng.bll.basic.dto.InParamSCWriteOpenBoxKey;
import com.hzdongcheng.bll.basic.dto.InParamSCWriteServerTime;
import com.hzdongcheng.bll.basic.dto.OutParamMBDeviceSign;
import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryPackage;
import com.hzdongcheng.bll.basic.dto.OutParamPTPickupPackage;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.proxy.ProxyManager;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.DBWrapper;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;

import java.lang.reflect.Type;
import java.util.List;

public class SyncDataProc {
    static Log4jUtils log = Log4jUtils.createInstanse(SyncDataProc.class);
    private static final int MAX_SYNC_TIMES = 20;
    private static SCUploadDataQueueDAO uploadDataQueueDAO = DAOFactory.getSCUploadDataQueueDAO();
    private static SQLiteDatabase database = DBWrapper.getInstance().openWorkDatabase(); // 自动创建一个数据库连接
    private static String externalFunction = "com.hzdongcheng.bll.dto";

    public static class SyncData {

        public SyncData(IRequest request, String msgid, int failureCount) {
            this.request = request;
            this.msgid = msgid;
            this.failureCount = failureCount;
        }

        public SyncData(IRequest request, String msgid) {
            this.request = request;
            this.msgid = msgid;
        }

        public SyncData(IRequest request) {
            this.request = request;
        }

        public IRequest request;
        public String msgid = "";
        public int failureCount = 0;
    }

    public static void syncDataProcess() {

        // 查询需要同步的业务数据
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("LastModifyTime", "<", DateUtils.subtractMinute(2, DateUtils.nowDate())); // 2分钟之前的

        List<SCUploadDataQueue> resultList;
        try {
            //resultList = uploadDataQueueDAO.select(whereCols, orderByField, dbWrapper);
            resultList = uploadDataQueueDAO.executeQuery(database, whereCols, "LastModifyTime");
        } catch (DcdzSystemException e) {
            log.error("[SyncDetection]-<uploadDataQueueDAO>" + e.getMessage());
            return;
        }
        String servicename = "";
        String msgid = "";
        int failureCount = 0; // 10次控制
        Type type;

        for (int i = 0; i < resultList.size(); i++) {
            servicename = resultList.get(i).ServiceName;
            msgid = resultList.get(i).MsgUid;
            failureCount = resultList.get(i).FailureCount;
            try {
                type = Class.forName("com.hzdongcheng.bll.basic.dto." + servicename);
            } catch (ClassNotFoundException e) {
                try {
                    type = Class.forName(externalFunction + "." + servicename);
                } catch (ClassNotFoundException e1) {
                    log.error("[TimingTask] 类型没找到 " + servicename);
                    continue;
                }
            }
            try {
                Object request = null;

                request = PacketUtils.deserializeObject(resultList.get(i).MsgContent, type);

                SyncDataProc.SyncData state = new SyncDataProc.SyncData((IRequest) request, msgid, failureCount);

                // ////////////////////////////////////////////////////////////////////////
                if (SysDict.SYNC_SERVICENAME_DELIVERY.equals(servicename)) // 投递记录
                {
                    new SyncDataProc.SendDeliveryRecord(state).run();
                } else if (SysDict.SYNC_SERVICENAME_WITHDRAW.equals(servicename)
                        || SysDict.SYNC_SERVICENAME_CUSTOMERPICKUP.equals(servicename)
                        || SysDict.SYNC_SERVICENAME_MANAGERPICKUP.equals(servicename)) {
                    new SyncDataProc.SendPickupRecord(state).run();
                } else {
                    new SyncDataProc.SendSyncDataToServer(state).run();
                }
            } catch (Exception e) {

            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    }
    /**
     * @param externalFunction 扩展方法的参数包名
     */
    public static void setExternalFunction(String externalFunction) {
        SyncDataProc.externalFunction = externalFunction;
    }
    public static class SendPickupRecord implements Runnable {


        Object arg = null;

        public SendPickupRecord(Object param) {
            arg = param;
        }

        @Override
        public void run() {
            SyncData ps = (SyncData) arg;
            IRequest request = ps.request;
            final String msgid = ps.msgid;
            final int failureCount = ps.failureCount;
            if (request instanceof InParamPTPickupPackage) {
                InParamPTPickupPackage inParam = (InParamPTPickupPackage) request;
                inParam.AgainUploading = failureCount == 0 ? SysDict.Agai_Uploading_FLAG_N0 : SysDict.Agai_Uploading_FLAG_YES;
            }

            IResponder responder = new IResponder() {

                @Override
                public void result(Object data) {
                    if (data != null && StringUtils.isNotEmpty(msgid)) {
                        JsonResult jsonResult = (JsonResult) data;

                        try {
                            // 业务回传同步交易时间
                            OutParamPTPickupPackage outParam = jsonResult.Eval(OutParamPTPickupPackage.class);

                            InParamSCWriteServerTime inParam = new InParamSCWriteServerTime();
                            inParam.MsgUid = msgid;
                            inParam.ServerTime = outParam.ServerTime;

                            DBSContext.localContext.doBusiness(inParam);
                        } catch (Exception ex) {
                            log.error("[WriteServerTime Error]:" + ex.getMessage());

                            if (failureCount >= MAX_SYNC_TIMES)
                                CommonDAO.delDataQueueTimestamp(msgid, ex.getMessage());
                            else
                                CommonDAO.modifyDataQueueTimestamp(msgid);
                        }
                    }
                }

                @Override
                public void fault(Object info) {
                    if (info != null) {
                        FaultResult faultResult = (FaultResult) info;
                        log.error("[SendPickupRecord fault]:" + faultResult.faultCode);

                        if (faultResult.faultType == FaultResult.FAULT_RESULT_TYPE_REMOTE
                                && StringUtils.isNotEmpty(msgid)) {
                            if (failureCount >= MAX_SYNC_TIMES)
                                CommonDAO.delDataQueueTimestamp(msgid, faultResult.faultCode);
                            else
                                CommonDAO.modifyDataQueueTimestamp(msgid);
                        }
                    }

                }

            };

            // 发送消息,30秒最大等待时间
            try {
                ProxyManager.getInstance().sendRequest(request, responder, 30, DBSContext.secretKey);
            } catch (Exception ex) {
                log.error("[SendMsg2 Error]:" + ex.getMessage());
            }
        }

    }

    public static class SendSyncDataToServer implements Runnable {
        public Object arg = null;

        public SendSyncDataToServer(Object param) {
            arg = param;
        }

        @Override
        public void run() {
            SyncData ps = (SyncData) arg;
            IRequest request = ps.request;
            final String msgId = ps.msgid;
            final int failureCount = ps.failureCount;
            IResponder responder = new IResponder() {

                @Override
                public void result(Object data) {
                    if (StringUtils.isNotEmpty(msgId)) {
                        CommonDAO.delDataQueueTimestamp(msgId, null);
                    }
                }

                @Override
                public void fault(Object info) {
                    if (info != null) {
                        FaultResult faultResult = (FaultResult) info;
                        if (faultResult.faultType == FaultResult.FAULT_RESULT_TYPE_REMOTE
                                && StringUtils.isNotEmpty(msgId)) {
                            if (failureCount >= 3)
                                CommonDAO.delDataQueueTimestamp(msgId, faultResult.faultCode);
                            else
                                CommonDAO.modifyDataQueueTimestamp(msgId);
                        }
                    }

                }
            };
            try {
                ProxyManager.getInstance().sendRequest(request, responder, 30, DBSContext.secretKey);
            } catch (Exception ex) {
                log.error("[SendMsg3 Error]:" + ex.getMessage());
            }
        }

    }

    public static class SendDeviceSignInfo implements Runnable {
        private Object arg = null;

        public SendDeviceSignInfo(Object param) {
            arg = param;
        }

        @Override
        public void run() {
            SyncData ps = (SyncData) arg;
            IRequest request = ps.request;
            String msgid = ps.msgid;
            IResponder responder = new IResponder() {

                @Override
                public void result(Object data) {
                    if (data != null) {
                        JsonResult jsonResult = (JsonResult) data;

                        try {
                            OutParamMBDeviceSign outParam = jsonResult.Eval(OutParamMBDeviceSign.class);
                            Thread.sleep(1000);
                            // 动态密钥
                            if (outParam.SignKey.length() == 36)
                                DBSContext.secretKey = outParam.SignKey;


                            // 欢迎词
                            if (StringUtils.isNotEmpty(outParam.WelcomeInfo))
                                DBSContext.sysInfo.UpdateContent = outParam.WelcomeInfo;

                            // 修改设备注册标志
                            InParamMBModRegisterFlag inParam = new InParamMBModRegisterFlag();

                            inParam.RegisterFlag = outParam.RegisterFlag;
                            inParam.ServerTime = outParam.ServerTime;
                            inParam.TerminalName = outParam.TerminalName;
                            inParam.InitPasswd = outParam.InitPasswd;
                            inParam.MBDeviceNo = outParam.MBDeviceNo;
                            inParam.OfBureau = outParam.OfBureau;

                            DBSContext.localContext.doBusiness(inParam);
                        } catch (Exception ex) {
                            log.error("[DeviceSign Error0]:" + ex.getMessage());
                        }
                    }
                }

                @Override
                public void fault(Object info) {
                    if (info != null) {
                        FaultResult faultResult = (FaultResult) info;
                        log.error("[DeviceSign Error1]:" + faultResult.faultCode);
                    }

                }
            };

            try {
                ProxyManager.getInstance().sendRequest(request, responder, 30, "");
            } catch (Exception ex) {
                log.error("[SendMsg4 Error]:" + ex.getMessage());
            }
        }
    }

    public static class SendDeliveryRecord implements Runnable {
        Object arg = null;

        public SendDeliveryRecord(Object param) {
            arg = param;
        }

        @Override
        public void run() {
            SyncData ps = (SyncData) arg;
            IRequest request = ps.request;
            final String msgid = ps.msgid;
            final int failureCount = ps.failureCount;
            InParamPTDeliveryPackage inParam = (InParamPTDeliveryPackage) request;
            inParam.AgainUploading = failureCount == 0 ? SysDict.Agai_Uploading_FLAG_N0 : SysDict.Agai_Uploading_FLAG_YES;

            IResponder responder = new IResponder() {

                @Override
                public void result(Object data) {
                    if (data != null) {
                        JsonResult jsonResult = (JsonResult) data;

                        try {
                            // 开箱密码写入本地库
                            OutParamPTDeliveryPackage outParam = jsonResult.Eval(OutParamPTDeliveryPackage.class);
                            InParamSCWriteOpenBoxKey inParam = new InParamSCWriteOpenBoxKey();

                            inParam.MsgUid = msgid;
                            inParam.OpenBoxKey = outParam.OpenBoxKey;
                            inParam.PackageID = outParam.PackageID;
                            inParam.BoxNo = outParam.BoxNo;
                            inParam.ServerTime = outParam.ServerTime;

                            DBSContext.localContext.doBusiness(inParam);
                        } catch (DcdzSystemException e) {
                            log.error("[WriteOpenBoxKey Error]:" + e.getMessage());

                            if (failureCount > MAX_SYNC_TIMES)
                                CommonDAO.delDataQueueTimestamp(msgid, e.getMessage());
                            else
                                CommonDAO.modifyDataQueueTimestamp(msgid);
                        }
                    }
                }

                @Override
                public void fault(Object info) {
                    if (info != null) {
                        FaultResult faultResult = (FaultResult) info;
                        log.error("[SendDeliveryRecord fault]:" + faultResult.faultCode);

                        if (faultResult.faultType == FaultResult.FAULT_RESULT_TYPE_REMOTE) {
                            if (failureCount >= MAX_SYNC_TIMES)
                                CommonDAO.delDataQueueTimestamp(msgid, faultResult.faultCode);
                            else
                                CommonDAO.modifyDataQueueTimestamp(msgid);
                        }
                    }
                }
            };

            // 发送消息,30秒最大等待时间
            ProxyManager.getInstance().sendRequest(request, responder, 30, DBSContext.secretKey);

        }
    }

}
