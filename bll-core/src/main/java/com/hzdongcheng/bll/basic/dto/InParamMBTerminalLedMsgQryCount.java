package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamMBTerminalLedMsgQryCount implements IRequest{
	public String FunctionID = "150455"; //功能编号

	public String OperID = ""; //管理员编号
	public String DepartmentID = ""; //运营网点编号
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String OnlineStatus = ""; //在线状态

}