package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.bll.common.IRequest;

import java.util.Date;


public class InParamAPCustomerAppOpenBox implements IRequest {
	public String FunctionID = "650138"; //功能编号

	public String CustomerMobile = ""; //收件人手机
	public String PackageID = ""; //包裹编号
	public Date StoredTime =new Date();//存件时间
	public String TerminalNo = ""; //终端编号
	public String TradeWaterNo = ""; //流水号
}