package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTPostmanLogin implements IRequest{
	public String FunctionID = "330001"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String PostmanID = ""; //投递员编号
	public String Password = ""; //投递员密码
	public String VerifyFlag = ""; //认证标志(0：获取动态密码
    public String LoginType = "1"; //手机密码登录 2:身份证登录

}