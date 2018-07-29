package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTPreDeliveryHandle implements IRequest {
	public String FunctionID = "330024"; //功能编号

	public String Remark;
	public InParamPTDeliveryPackage inParamPTDeliveryPackage;
}
