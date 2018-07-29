package com.hzdongcheng.device.bean;

import com.hzdongcheng.components.toolkits.exception.error.ErrorCode;
import com.hzdongcheng.components.toolkits.exception.error.ErrorTitle;

/**
 * Created by Peace on 2017/9/17.
 */

public final class DriverErrorCode extends com.hzdongcheng.components.toolkits.exception.error.ErrorCode {
    public static int DEV_NO_CONTEXT = 400010;
    public static int DEV_SERVICE = 400011;
    public static int DEV_PORT = 400021;
    public static int DEV_REMOTE_EXCEPTION = 400031;
    public static int DEV_CONTROLLER_NOT_FOUND = 400000;

    static {
        ErrorTitle.putErrorTitle(DEV_NO_CONTEXT, "context no set");
        ErrorTitle.putErrorTitle(DEV_SERVICE, "bind service failed");
        ErrorTitle.putErrorTitle(DEV_PORT, "open serial port failed");
        ErrorTitle.putErrorTitle(DEV_REMOTE_EXCEPTION, "service remote exception");
        ErrorTitle.putErrorTitle(DEV_CONTROLLER_NOT_FOUND, "未获取到服务模块");
    }
}


