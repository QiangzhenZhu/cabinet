package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamMBGetUpFailureData implements IRequest{
	public String FunctionID = "150338"; //功能编号

	public String OperID = ""; //操作员编号
	public String TerminalNo = ""; //设备编号
	public String UploadKey = ""; //上传Key

}