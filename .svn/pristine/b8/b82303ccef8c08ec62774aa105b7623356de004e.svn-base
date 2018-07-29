
package com.hzdongcheng.bll;

import android.database.SQLException;

import com.hzdongcheng.bll.basic.LocalContext;
import com.hzdongcheng.bll.basic.RemoteContext;
import com.hzdongcheng.bll.basic.dto.InParamMBDeviceSign;
import com.hzdongcheng.bll.basic.dto.OutParamSMSysInfoQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBTerminalDetail;
import com.hzdongcheng.bll.constant.ControlParam;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.basic.dto.InParamPAControlParamQry;
import com.hzdongcheng.bll.basic.dto.InParamSMSysInfoQry;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalDetail;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalParamMod;
import com.hzdongcheng.bll.websocket.SocketClient;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.dto.PATerminalCtrlParam;
import com.hzdongcheng.persistent.dto.SMSystemInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;


public class DBSContext {
    private static Log4jUtils log = Log4jUtils.createInstanse(DBSContext.class);
    public static LocalContext localContext = new LocalContext();
    public static RemoteContext remoteContext = new RemoteContext();

    public static String registerFlag = ""; // 注册标志
    public static String terminalUid = ""; // 设备编号
    public static String terminalNick;   //客户的设备编号
    public static String currentOperID = "";//后台管理员
    public static String terminalName = "";
    public static String currentVersion = "";//系统版本
    public static String driverVersion = "";//驱动版本
    public static String secretKey = ""; // 密钥
    public static String monHost = "211.155.225.116"; // 监控服务器
    public static int monPort = 58888; // 监控服务器端口
    public static String lockerTokenID;//

    public static String serverHost = "";
    public static String serverPort = "";
    public static String randomCode = "";

    public static ControlParam ctrlParam = new ControlParam();
    public static SMSystemInfo sysInfo = new SMSystemInfo();

    public static String location;
    public static String signMac;
    public static String terminalStatus;
    public static boolean deviceFault;

    /**
     * 初始化数据库
     *
     * @throws DcdzSystemException
     */
    public static void initDatabase() throws DcdzSystemException {
        loadSysInfo();
        loadCtrlParams();
        loadTerminalInfo();
    }

    /**
     * 设备签到
     */
    public static void deviceSign() {
        try {
            InParamMBDeviceSign inParam = new InParamMBDeviceSign();
            driverVersion= HAL.getVersion();
            remoteContext.doBusiness(inParam, null, 30);
        } catch (DcdzSystemException e) {
            log.error("[deviceSign]" + e.getMessage());
        }

    }

    /**
     * 加载控制参数
     */
    public static void loadCtrlParams() {
        InParamPAControlParamQry inParam = new InParamPAControlParamQry();
        try {
            List<PATerminalCtrlParam> dt = localContext.doBusiness(inParam);
            for (PATerminalCtrlParam param : dt) {
                try {
                    ctrlParam.getClass().getField(param.KeyString.trim()).set(ctrlParam, param.DefaultValue);
                } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                        | SecurityException e) {
                    e.printStackTrace();
                }
            }

        } catch (DcdzSystemException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void modifyMonitorFlag(InParamTBTerminalParamMod inParam) {
        ctrlParam.screensoundFlag = inParam.ScreensoundFlag;
        ctrlParam.articleInspectFlag = inParam.ArticleInspectFlag;
        ctrlParam.doorInspectFlag = inParam.DoorInspectFlag;
        ctrlParam.powerInspectFlag = inParam.PowerInspectFlag;
        ctrlParam.shockInspectFlag = inParam.ShockInspectFlag;
        ctrlParam.existSuperLargeBox = inParam.ExistSuperLargeBox;
        ctrlParam.existSameTypeBox = inParam.ExistFreshBox;
        ctrlParam.lampOffTime = inParam.LampOffTime;
        ctrlParam.lampOnTime = inParam.LampOnTime;
        ctrlParam.refuseCloseDoor = String.valueOf(inParam.RefuseCloseDoor);
    }

    public static void loadSysInfo() {
        InParamSMSysInfoQry inParamQry = new InParamSMSysInfoQry();
        OutParamSMSysInfoQry outParam = new OutParamSMSysInfoQry();

        try {
            outParam = localContext.doBusiness(inParamQry);
            sysInfo.ServerIP = outParam.ServerIP;
            sysInfo.ServerPort = outParam.ServerPort;
            sysInfo.ServerSSL = outParam.ServerSSL;
            sysInfo.InitPasswd = outParam.InitPasswd;
            sysInfo.TerminalPasswd = outParam.TerminalPasswd;
            sysInfo.SoftwareVersion = outParam.SoftwareVersion;

            sysInfo.MonServerIP = outParam.MonServerIP;
            sysInfo.MonServerPort = outParam.MonServerPort;
            monHost = outParam.MonServerIP;
            monPort = outParam.MonServerPort;
            serverHost = outParam.ServerIP;
            serverPort = String.valueOf(outParam.ServerPort);

            sysInfo.UpdateContent = outParam.UpdateContent; // 欢迎词

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadTerminalInfo() throws DcdzSystemException {
        OutParamTBTerminalDetail outParam;
        try {
            InParamTBTerminalDetail inParam = new InParamTBTerminalDetail();
            outParam = localContext.doBusiness(inParam);
        } catch (DcdzSystemException e) {
            // 设备还未初始化
            log.error("[loadTerminalInfo]" + e.getMessage());
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_NOINITDEVICE, e.getMessage());
        }

        // 设备还未初始化
        if (StringUtils.isEmpty(outParam.TerminalNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_NOINITDEVICE);

        // 注册未通过，请修改柜号
        if (SysDict.TERMINAL_REGISTERFLAG_FAILURE.equals(outParam.RegisterFlag))
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_MODTERMINANO4REGISTER);

        terminalUid = outParam.TerminalNo;
        terminalNick = outParam.MBDeviceNo;
        registerFlag = outParam.RegisterFlag;
        terminalName = outParam.TerminalName;
    }
}