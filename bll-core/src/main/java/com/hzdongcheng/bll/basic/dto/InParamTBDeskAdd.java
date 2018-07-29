package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBDeskAdd implements IRequest{
	public String FunctionID = "210101"; //功能编号

	public String OperID = ""; //管理员编号
	public String BoxNumStr = ""; //副柜箱数
	public double DeskHeight; //副柜高度
	public double DeskWidth; //副柜宽度
	public int DeskType; //副柜类型
	public String Remark = ""; //备注

}