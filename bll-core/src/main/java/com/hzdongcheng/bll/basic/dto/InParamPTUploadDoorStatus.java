package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTUploadDoorStatus implements IRequest{
	public String FunctionID = "330513"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号
	public String ArticleStatus = ""; //物品状态

}