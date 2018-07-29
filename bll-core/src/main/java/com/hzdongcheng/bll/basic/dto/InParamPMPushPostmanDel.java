package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPMPushPostmanDel implements IRequest{
	public String FunctionID = "311203"; //功能编号

	public String OperID = ""; //操作员编号
	public String PostmanID = ""; //投递员编号
	public String BindCardID = ""; //绑定卡号
	public String Remark = ""; //备注
	public String RemoteFlag = ""; //远程操作标志
	public String TerminalNoStr = ""; //设备列表

}