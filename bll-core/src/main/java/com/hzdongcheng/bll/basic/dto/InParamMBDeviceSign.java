package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.bll.common.IRequest;

public class InParamMBDeviceSign implements IRequest {
	public String FunctionID = "150301"; // 功能编号

	public String TerminalNo = ""; // 设备编号
	public String SignIP = ""; // 签到IP
	public String SignMac = ""; // 签到MAC
	public String SoftwareVersion = ""; // 软件版本 <终端软件版本>[,<驱动软件>]
	public String InitPasswd = ""; // 初始化密码
	public String RegisterFlag = ""; // 注册标志
	public String TerminalInfo = ""; // 设备信息
	public String BoxInfo = ""; // 箱体信息

}