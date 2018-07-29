package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamSCInsertUploadDataQueue implements IRequest{
	public String FunctionID = "510011"; //功能编号

	public String ServiceName = ""; //服务名称
	public String PackageID = ""; //订单号
	public String MsgContent = ""; //消息内容

}