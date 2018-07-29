package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPMPostmanUnBindCard implements IRequest{
	public String FunctionID = "311032"; //功能编号

	public String PostmanID = ""; //投递员编号
	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String Remark = ""; //备注

}