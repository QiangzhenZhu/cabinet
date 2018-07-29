package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTDownloadInboxInfo implements IRequest{
	public String FunctionID = "330511"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String DeskNo = ""; //副柜编号

}