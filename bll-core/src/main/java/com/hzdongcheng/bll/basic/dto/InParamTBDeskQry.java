package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBDeskQry implements IRequest{
	public String FunctionID = "210104"; //功能编号

	public String OperID = ""; //管理员编号
	public int DeskNo; //副柜编号
	public String TerminalNo = ""; //设备号

}