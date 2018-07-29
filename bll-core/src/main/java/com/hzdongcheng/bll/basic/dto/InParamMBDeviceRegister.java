package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.bll.common.IRequest;

public class InParamMBDeviceRegister implements IRequest {
    public String FunctionID = ""; //功能编号
    public String SignIP = ""; //签到终端IP
    public String SignMac = ""; //签到终端MAC
    public String SoftwareVersion = ""; //软件版本号
    public String InitPasswd = ""; //安装密码
    public String ServerUrl = "";//服务器地址
    public String DeviceInfo = ""; //系统信息（系统版本，驱动版本..）
    public String remark;
}
