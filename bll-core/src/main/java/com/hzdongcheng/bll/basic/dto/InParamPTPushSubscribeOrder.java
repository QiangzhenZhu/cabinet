package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTPushSubscribeOrder implements IRequest{
	public String FunctionID = "330311"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String OrderList = ""; //订单列表
	public String RemoteFlag = ""; //远程操作标志

}