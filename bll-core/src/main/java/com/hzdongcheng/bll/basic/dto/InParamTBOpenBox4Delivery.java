package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBOpenBox4Delivery implements IRequest{
	public String FunctionID = "210402"; //功能编号

	public String PostmanID = ""; //投递员编号
	public String BoxList = ""; //箱门编号列表
	public String BoxType = ""; //箱类型编号
}