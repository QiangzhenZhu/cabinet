package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBBoxQryCount implements IRequest{
	public String FunctionID = "210215"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public int DeskNo; //副柜编号
	public String BoxNo = ""; //箱门编号
	public String BoxStatus = ""; //箱状态
	public String LockStatus = ""; //锁定状态
	public String FaultStatus = ""; //故障状态

}