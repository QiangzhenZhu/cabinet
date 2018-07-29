package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamMBGetDeviceSysLog implements IRequest{
	public String FunctionID = "150336"; //功能编号

	public String OperID = ""; //操作员编号
	public String TerminalNo = ""; //设备编号
	public int LogID; //日志序号
	public String UploadKey = ""; //上传Key

}