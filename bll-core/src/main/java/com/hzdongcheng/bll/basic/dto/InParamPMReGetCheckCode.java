package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPMReGetCheckCode implements IRequest{
	public String FunctionID = "311012"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String Mobile = ""; //手机

}