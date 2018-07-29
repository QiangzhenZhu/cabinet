package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBCopyBoxType implements IRequest{
	public String FunctionID = "210273"; //功能编号

	public String OperID = ""; //管理员编号
	public int DeskNoSrc; //源副柜编号
	public int DeskNoDest; //目标副柜编号

}