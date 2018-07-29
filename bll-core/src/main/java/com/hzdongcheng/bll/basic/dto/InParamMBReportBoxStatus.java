package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamMBReportBoxStatus implements IRequest{
	public String FunctionID = "150402"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String BoxInfo = ""; //箱体信息

}