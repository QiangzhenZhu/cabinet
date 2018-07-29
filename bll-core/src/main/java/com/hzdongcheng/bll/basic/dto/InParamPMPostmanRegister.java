package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPMPostmanRegister implements IRequest{
	public String FunctionID = "311011"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String Mobile = ""; //手机
	public String CompanyID = ""; //投递公司编号
	public String IDCard = ""; //身份证
	public String PostmanName = ""; //投递员姓名
	public String CheckCode = ""; //验证码
	public String Password = ""; //登陆密码

}