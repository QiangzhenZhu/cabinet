package com.hzdongcheng.device;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.graphics.Region;
import android.os.IBinder;
import android.os.RemoteException;

import com.hzdongcheng.device.bean.AppVersions;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.device.bean.DriverErrorCode;
import com.hzdongcheng.device.bean.ICallBack;
import com.hzdongcheng.device.bean.MasterStatus;
import com.hzdongcheng.device.bean.SlaveStatus;
import com.hzdongcheng.drivers.bean.Result;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.JsonUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.drivers.peripheral.IObserver;

import java.util.Observer;

/**
 * 硬件操作
 */
public final class HAL {
    private static Log4jUtils log = Log4jUtils.createInstanse(HAL.class);

    public static boolean init(Context context) {
        log.info("[HAL]初始化硬件服务");
        return ServiceProvider.getInstance().bindService(context);
    }

    private static ICallBack scannerCallBack;

    private static Observer serviceObserver;

    public static void setServiceObserver(Observer observer) {
        serviceObserver = observer;
    }

    static void connectedChanged(boolean connect) {
        if (serviceObserver != null) {
            serviceObserver.update(null, connect);
        }
    }

    public static void setScannerCallBack(ICallBack _callBack) {
        scannerCallBack = _callBack;
    }

    private static ICallBack cardReadercallBack;

    public static void setCardReaderCallBack(ICallBack _callBack) {
        cardReadercallBack = _callBack;
    }

    //#region 扫描枪
    public static IObserver scannerObserver = new IObserver.Stub() {
        @Override
        public void onMessage(String msg) throws RemoteException {
            if (scannerCallBack!=null)
                scannerCallBack.onMessage(msg, 0);
        }
    };

    /**
     * 设置扫描枪是否常亮
     *
     * @param always true常亮模式，false 命令模式
     */
    public static void toggleModel(boolean always) throws DcdzSystemException {
        try {
            log.info("[HAL] 扫描枪模式设置 " + always);
            ServiceProvider.getInstance().getScannerController().switchMode(always);
        } catch (RemoteException e) {
            log.error("[HAL] 扫描枪模式设置出错 " + e.getMessage());
        }
    }

    /**
     * 设置扫描枪是否可以扫描条码
     *
     * @param enabled true可以识别条码，false 禁止识别条码
     */
    public static void toggleBarcode(boolean enabled) throws DcdzSystemException {
        try {
            log.info("[HAL] 扫描枪条码设置 " + enabled);
            ServiceProvider.getInstance().getScannerController().toggleBarcode(enabled);
        } catch (RemoteException e) {
            log.error("[HAL] 扫描枪条码设置出错 " + e.getMessage());
        }
    }

    /**
     * 设置扫描枪是否可以扫描二维码
     *
     * @param enabled true可以识别，false 禁止识别
     */
    public static void toggleQRCode(boolean enabled) throws DcdzSystemException {
        try {
            log.info("[HAL] 扫描枪二维码设置 " + enabled);
            ServiceProvider.getInstance().getScannerController().toggleQRCode(enabled);
        } catch (RemoteException e) {
            log.error("[HAL] 扫描枪二维码设置出错 " + e.getMessage());
        }
    }

    /**
     * 开始扫描（扫描枪命令模式下生效）
     */
    public static void startScanning() throws DcdzSystemException {
        try {
            log.info("[HAL] 扫描枪开始扫描 ");
            ServiceProvider.getInstance().getScannerController().start();
        } catch (RemoteException e) {
            log.error("[HAL] 扫描枪开始扫描出错 " + e.getMessage());
        }
    }

    /**
     * 停止扫描（扫描枪命令模式下生效）
     */
    public static void stopScanning() throws DcdzSystemException {
        try {
            log.info("[HAL] 扫描枪停止扫描 ");
            ServiceProvider.getInstance().getScannerController().stop();
        } catch (RemoteException e) {
            log.error("[HAL] 扫描枪停止扫描出错 " + e.getMessage());
        }
    }

    /**
     * 添加扫描监听
     */
    public static void addObserver() throws DcdzSystemException {
        try {
            log.info("[HAL] 添加扫描监听 ");
            ServiceProvider.getInstance().getScannerController().addObserver(scannerObserver);
        } catch (RemoteException e) {
            log.error("[HAL] 添加扫描监听 " + e.getMessage());
        }
    }

    /**
     * 删除扫描监听
     */
    public static void removeObserver() throws DcdzSystemException {
        try {
            log.info("[HAL] 添加扫描监听 ");
            ServiceProvider.getInstance().getScannerController().removeObserver(scannerObserver);
        } catch (RemoteException e) {
            log.error("[HAL] 添加扫描监听 " + e.getMessage());
        }
    }

    //#endregion

    //#region 主控板方法

    /**
     * 主控断电重启
     *
     * @param delay 重启延时时间
     */
    public static void powerOnAgain(int delay) throws DcdzSystemException {
        if (delay < 2000) {
            delay = 2000;
        }
        try {
            log.info("[HAL]开始断电重启");
            ServiceProvider.getInstance().getMasterController().reboot(delay);
        } catch (RemoteException e) {
            log.error("[HAL]断电重启调用失败 >>" + e.getMessage());
        }
    }

    /**
     * 打开或关闭照明灯 (主副统一控制)
     *
     * @param enabled true 打开，false 关闭
     * @return 执行成功
     */
    public static boolean toggleLamp(boolean enabled) throws DcdzSystemException {
        try {
            log.info("[HAL] 照明灯控制 " + enabled);
            ServiceProvider.getInstance().getMasterController().toggleMasterLamp(enabled);
            ServiceProvider.getInstance().getMasterController().toggleSlaveLamp(enabled);
        } catch (RemoteException e) {
            log.error("[HAL]照明灯开关失败 >>" + e.getMessage());
        }
        return true;
    }

    /**
     * 查询主机所有状态
     *
     * @return 主机状态
     */
    public static MasterStatus getMasterStatus() throws DcdzSystemException {
        MasterStatus masterStatus = null;
        try {
            log.info("[HAL] 获取主机状态");
            Result result = ServiceProvider.getInstance().getMasterController().queryMainStatus();
            if (result.getCode() == 0) {
                masterStatus = JsonUtils.toBean(result.getData(), MasterStatus.class);
            }
            log.error("[HAL] 获取主机状态失败, code " + result.getCode() + ",msg " + result.getErrorMsg());

        } catch (RemoteException e) {
            log.error("[HAL] 获取主机状态出错");
        }
        return masterStatus;
    }

    /**
     * 获取电表读数
     *
     * @return 电表读数，如果获取失败则返回null。
     */
    public static String getAmmeterReading() throws DcdzSystemException {
        Result result;
        try {
            log.info("[HAL] 获取电表读数");
            result = ServiceProvider.getInstance().getMasterController().getAmmeterReading();
            if (result.getCode() == 0) {
                return result.getData();
            }
            log.error("[HAL] 获取电表读数失败，code " + result.getCode() + ",msg " + result.getErrorMsg());
        } catch (RemoteException e) {
            log.error("[HAL] 获取电表读数出错");
        }
        return null;
    }

    /**
     * 获取管理开关
     *
     * @return 管理开关，如果获取失败则返回null。
     */
    public static String getManagerSwitch() throws DcdzSystemException {

        log.info("[HAL] 获取管理开关");
        return null;
    }

    /**
     * 打开指定箱门
     *
     * @param boxName 格口名称
     * @return 打开结果
     */
    public static boolean openBox(String boxName) throws DcdzSystemException {
        try {
            log.info("[HAL] 打开箱门 " + boxName);
            Result result = ServiceProvider.getInstance().getSlaveController().openBoxByName(boxName);
            if (result.getCode() == 0) {
                return true;
            }
            log.error("[HAL] 箱门打开失败，箱门" + boxName + ",code " + result.getCode());
        } catch (RemoteException e) {
            log.error("[HAL] 开箱服务调用失败，箱门 " + boxName);
            throw new DcdzSystemException(e.hashCode());
        }
        return false;
    }

    /**
     * 打开指定箱门
     *
     * @param boardId 驱动板ID
     * @param boxId   格口ID
     * @return 打开结果
     */
    public static boolean openBox(int boardId, int boxId) throws DcdzSystemException {
        try {
            log.info("[HAL] 打开箱门 ,board" + boardId + ",box " + boxId);
            Result result = ServiceProvider.getInstance().getSlaveController().openBoxById((byte) boardId, (byte) boxId);
            if (result.getCode() == 0) {
                return true;
            }
            log.error("[HAL] 箱门打开失败 " + JsonUtils.toJSONString(result));
        } catch (RemoteException e) {
            log.error("[HAL] 开箱服务调用失败，board" + boardId + ",box " + boxId);
        }
        return false;
    }

    /**
     * 获取格口状态
     *
     * @param boxName 格口名称
     * @return 格口状态
     * @throws DcdzSystemException 获取失败返回异常
     */
    public static BoxStatus getBoxStatus(String boxName) throws DcdzSystemException {
        try {
            Result result = ServiceProvider.getInstance().getSlaveController().queryBoxStatusByName(boxName);
            if (result.getCode() == 0) {
                return JsonUtils.toBean(result.getData(), BoxStatus.class);
            }
            log.error("[HAL] 获取格口状态失败 boxName " + boxName);
            throw new DcdzSystemException(result.getCode(), result.getErrorMsg());
        } catch (RemoteException e) {
            log.error("[HAL] 获取格口状态服务出错, boxName " + boxName);
            throw new DcdzSystemException(DriverErrorCode.DEV_REMOTE_EXCEPTION);
        }
    }

    public static BoxStatus getBoxStatus(int boardId, int boxId) throws DcdzSystemException {
        try {
            Result result = ServiceProvider.getInstance().getSlaveController().queryBoxStatusById((byte) boardId, (byte) boxId);
            if (result.getCode() == 0) {
                return JsonUtils.toBean(result.getData(), BoxStatus.class);
            }
            log.error("[HAL] 获取格口状态失败 boardId" + boardId + ",boxId " + boxId);
            throw new DcdzSystemException(result.getCode(), result.getErrorMsg());
        } catch (RemoteException e) {
            log.error("[HAL] 获取格口状态服务出错, boardId" + boardId + ",boxId " + boxId);
            throw new DcdzSystemException(DriverErrorCode.DEV_REMOTE_EXCEPTION);
        }
    }

    /**
     * 获取副柜状态
     *
     * @param boardId 副柜编码
     * @return 副柜状态
     */
    public static SlaveStatus getSlaveStatus(int boardId) throws DcdzSystemException {
        try {
            Result result = ServiceProvider.getInstance().getSlaveController().queryStatusById((byte) boardId);
            if (result.getCode() == 0) {
                return JsonUtils.toBean(result.getData(), SlaveStatus.class);
            }
            log.error("[HAL] 获取副柜状态失败 board" + boardId);
            throw new DcdzSystemException(result.getCode(), result.getErrorMsg());
        } catch (RemoteException e) {
            log.error("[HAL] 获取副柜状态服务出错, board " + boardId);
            throw new DcdzSystemException(DriverErrorCode.DEV_REMOTE_EXCEPTION);
        }
    }

    //#endregion

    // 读卡器数据回调
    public static IObserver cardReaderObserver = new IObserver.Stub() {
        @Override
        public void onMessage(String msg) throws RemoteException {
            if (cardReadercallBack != null)
                cardReadercallBack.onMessage(msg, 1);
        }
    };

    /*******************************************  系统 ************************************/
    /**
     * 操作系统重启
     *
     * @param delay 延时时间(最少1000毫秒)
     * @return 调用结果
     */
    public static boolean reboot(int delay) throws DcdzSystemException {
        if (delay < 1000) {
            delay = 1000;
        }
        try {
            log.info("[HAL] 操作系统重启");
            Result result = ServiceProvider.getInstance().getSystemController().reboot(delay);
            if (result.getCode() == 0) {
                return true;
            }
            log.error("[HAL] 操作系统重启处理失败，code" + result.getCode());
        } catch (RemoteException e) {
            log.error("[HAL] 操作系统重启服务错误");
        }
        return false;
    }

    /**
     * 获取驱动服务版本
     *
     * @return 驱动服务版本号
     */
    public static String getVersion() throws DcdzSystemException {
        try {
            Result result = ServiceProvider.getInstance().getSystemController().getVersions();
            if (result.getCode() == 0) {
                return JsonUtils.toBean(result.getData(), AppVersions.class).getVersion();
            }
            log.error("[HAL] 获取驱动版本失败");
        } catch (RemoteException e) {
            log.error("[HAL] 获取驱动版本服务出错" + e.getMessage());
        }
        return "";
    }

    //#region 指纹仪操作

    /**
     * 采集指纹特征码信息， 若果方法异常或者返回控值，需要重新打开指纹仪{ fingerClose()->fingerOpen() }
     *
     * @return 指纹特征码
     * @throws DcdzSystemException 异常
     */
    public static String getFingerFeature() throws DcdzSystemException {
        try {
            log.info("[HAL] 开始采集指纹特征码");
            Result feature = ServiceProvider.getInstance().getFingerController().getFeature();
            if (feature.getCode() == 0) {
                return feature.getData();
            }
            if (feature.getCode() == 11016) {
                log.info("[HAL] 没有采集到指纹，需要重新打开指纹仪-->" + feature.getErrorMsg());
            } else {
                log.info("[HAL] 获取指纹特征码失败-" + feature.getErrorMsg());
            }
        } catch (RemoteException e) {
            log.error("[HAL] 获取特征码服务出错" + e.getMessage());
        }
        return "";
    }

    public static boolean fingerOpen() throws DcdzSystemException {
        try {
            //log.info("[HAL] 打开指纹仪,打开之前先关闭一下");
            if (!ServiceProvider.getInstance().getFingerController().open()) {
                fingerClose();
                return false;
            }
            return true;
        } catch (RemoteException e) {
            log.error("[HAL] 打开指纹仪服务失败" + e.getMessage());
            ServiceProvider.getInstance().fingerController = null;
        }
        return false;
    }

    public static boolean fingerClose() throws DcdzSystemException {
        try {
            log.info("[HAL] 关闭指纹仪");
            return ServiceProvider.getInstance().getFingerController().close();
        } catch (RemoteException e) {
            log.error("[HAL] 关闭指纹仪服务失败" + e.toString());
            ServiceProvider.getInstance().fingerController = null;
        }
        return false;
    }

    public static boolean fingerPressed() throws DcdzSystemException {
        try {
            //log.info("[HAL] 检测手指是否按下");
            return ServiceProvider.getInstance().getFingerController().pressDetect();
        } catch (RemoteException e) {
            log.error("[HAL] 检测手指按下服务出错" + e.toString());
            return false;
        }
    }

    public static boolean featureMatch(String feature1, String feature2) throws DcdzSystemException {
        try {
            log.info("[HAL] 指纹特征匹配");
            Result match = ServiceProvider.getInstance().getFingerController().match(feature1, feature2, 3);
            if (match.getCode() == 0) {
                return true;
            }
            log.info("[HAL] 指纹特征码匹配错误-" + match.getErrorMsg());
        } catch (RemoteException e) {
            log.error("[HAL] 指纹匹配服务出错" + e.toString());
        }
        return false;
    }
    //#endregion
}
