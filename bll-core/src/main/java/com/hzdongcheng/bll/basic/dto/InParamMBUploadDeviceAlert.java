package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamMBUploadDeviceAlert implements IRequest{
	public String FunctionID = "150352"; //功能编号

	public String TerminalNo = ""; //设备号
	public String AlertType = ""; //报警种类
	public String AlertLevel = ""; //报警等级
	public String BoxNo = ""; //箱门编号

}