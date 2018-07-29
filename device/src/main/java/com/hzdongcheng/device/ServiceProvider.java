package com.hzdongcheng.device;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.hzdongcheng.device.bean.DriverErrorCode;
import com.hzdongcheng.drivers.IDriverManager;
import com.hzdongcheng.drivers.finger.IFingerController;
import com.hzdongcheng.drivers.locker.IMasterController;
import com.hzdongcheng.drivers.locker.ISlaveController;
import com.hzdongcheng.drivers.peripheral.cardreader.ICardReaderController;
import com.hzdongcheng.drivers.peripheral.scanner.IScannerController;
import com.hzdongcheng.drivers.system.ISystemController;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.lang.ref.WeakReference;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServiceProvider {
    private Log4jUtils log = Log4jUtils.createInstanse(ServiceProvider.class);
    private static ServiceProvider instance;
    private Intent driverIntent = new Intent("hzdongcheng.intent.action.DRIVER");
    private IDriverManager driverManager;
    private IScannerController scannerController;
    private ISystemController systemController;
    private IMasterController masterController;
    private ISlaveController slaveController;
    private ICardReaderController cardreaderController;
    protected IFingerController fingerController;

    private WeakReference<Context> contextWeakReference;

    private ServiceProvider() {
    }

    public synchronized static ServiceProvider getInstance() {
        if (instance == null) {
            instance = new ServiceProvider();
        }
        return instance;
    }

    public synchronized boolean bindService(Context context) {
        driverIntent.setPackage("com.hzdongcheng.drivers");
        log.info("-->开始绑定驱动服务");
        contextWeakReference = new WeakReference<>(context);
        return context.bindService(driverIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> schedule;

    /**
     * 服务重新连接(延时3秒)
     */
    private synchronized void reconnectService() {

        if (schedule != null && !schedule.isDone()) {
            log.info("--> 驱动服务器已经再重连,将不会执行此次重连请求");
            return;
        }

        schedule = executorService.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("--> 开始重新连接驱动服务");
                if (contextWeakReference.get() != null) {
                    boolean isBind;
                    int tryCount = 0;
                    do {
                        try {
                            Thread.sleep(tryCount * 30 * 1000);
                        } catch (InterruptedException ignored) {
                        }
                        isBind = contextWeakReference.get().bindService(driverIntent, serviceConnection, Context.BIND_AUTO_CREATE);
                    } while (!isBind || tryCount++ < 10);
                }
            }
        }, 3, TimeUnit.SECONDS);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            log.info("-->驱动服务连接成功");
            driverManager = IDriverManager.Stub.asInterface(iBinder);
            try {
                getScannerController();
                getCardreaderController();
            } catch (DcdzSystemException ignored) {
            }
            HAL.connectedChanged(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            log.info("-->驱动服务断开连接");
            driverManager = null;
            scannerController = null;
            systemController = null;
            masterController = null;
            slaveController = null;
            cardreaderController = null;
            fingerController = null;
            HAL.connectedChanged(false);
            reconnectService();
        }
    };

    public IScannerController getScannerController() throws DcdzSystemException {
        if (scannerController != null)
            return scannerController;
        if (driverManager != null) {
            try {
                scannerController = IScannerController.Stub.asInterface(driverManager.getScannerService());
                if (scannerController == null) {
                    throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
                }
                scannerController.start();
                scannerController.addObserver(HAL.scannerObserver);
            } catch (RemoteException e) {
                log.error(">>扫描枪控制器获取失败>>" + e.getMessage());
            }
        }
        if (scannerController == null) {
            throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
        }
        return scannerController;
    }

    public IMasterController getMasterController() throws DcdzSystemException {
        if (masterController != null)
            return masterController;
        if (driverManager != null) {
            try {
                masterController = IMasterController.Stub.asInterface(driverManager.getMasterService());
            } catch (RemoteException e) {
                log.error(">>主控控制器获取失败>>" + e.getMessage());
            }
        }
        if (masterController == null) {
            throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
        }
        return masterController;
    }

    public IFingerController getFingerController() throws DcdzSystemException {
        if (fingerController != null)
            return fingerController;
        if (driverManager != null) {
            try {
                fingerController = IFingerController.Stub.asInterface(driverManager.getService("FingerService"));
            } catch (RemoteException e) {
                log.error(">>获取指纹服务失败>>" + e.getMessage());
            }
        }
        if (fingerController == null) {
            throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
        }
        return fingerController;
    }

    public ISlaveController getSlaveController() throws DcdzSystemException {
        if (slaveController != null)
            return slaveController;
        if (driverManager != null) {
            try {
                slaveController = ISlaveController.Stub.asInterface(driverManager.getSlaveService());
            } catch (RemoteException e) {
                log.error(">>副柜控制器获取失败>>" + e.getMessage());
            }
        }
        if (slaveController == null) {
            throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
        }
        return slaveController;
    }

    public ICardReaderController getCardreaderController() throws DcdzSystemException {
        if (cardreaderController != null)
            return cardreaderController;
        if (driverManager != null) {
            try {
                cardreaderController = ICardReaderController.Stub.asInterface(driverManager.getCardReaderService());
                if (cardreaderController == null) {
                    throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
                }
                cardreaderController.addObserver(HAL.cardReaderObserver);
            } catch (RemoteException e) {
                log.error(">>读卡器控制器获取失败>>" + e.getMessage());
            }
        }
        if (cardreaderController == null) {
            throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
        }
        return cardreaderController;
    }

    public ISystemController getSystemController() throws DcdzSystemException {
        if (systemController != null)
            return systemController;
        if (driverManager != null) {
            try {
                systemController = ISystemController.Stub.asInterface(driverManager.getSystemService());
            } catch (RemoteException e) {
                log.error(">>系统服务获取失败>>" + e.getMessage());
            }
        }
        if (systemController == null) {
            throw new DcdzSystemException(DriverErrorCode.DEV_CONTROLLER_NOT_FOUND);
        }
        return systemController;
    }
}
