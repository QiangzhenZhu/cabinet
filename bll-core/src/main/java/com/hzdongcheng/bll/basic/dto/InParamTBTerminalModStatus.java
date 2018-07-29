package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBTerminalModStatus implements IRequest{
	public String FunctionID = "210011"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String TerminalStatus = ""; //柜状态
	public String RemoteFlag = ""; //远程操作标志

}