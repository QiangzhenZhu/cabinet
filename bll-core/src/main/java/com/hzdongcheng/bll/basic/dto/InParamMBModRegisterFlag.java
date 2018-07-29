package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;


public class InParamMBModRegisterFlag implements IRequest{
	public String FunctionID = "150302"; //功能编号

	public String TerminalNo = ""; //设备号
	public String SoftwareVersion = ""; //软件版本号
	public String RegisterFlag = ""; //注册标志
	public Date ServerTime = DateUtils.getMinDate(); //服务器时间
	public String TerminalName = ""; //设备名称
	public String MBDeviceNo = ""; //运营商设备编号
	public String OfBureau = ""; //所属投递局
	public String InitPasswd = ""; //安装密码
	public String TerminalPasswd = ""; //终端密码

}