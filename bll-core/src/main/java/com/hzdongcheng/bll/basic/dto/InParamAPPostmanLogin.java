package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.bll.common.IRequest;

public class InParamAPPostmanLogin implements IRequest {
	public String FunctionID = "650008"; // 功能编号

	public String PostmanID = ""; // 投递员编号
	public String Password = ""; // 投递员密码
	public String TerminalNo = ""; // 设备号
	public String RandomCode = ""; // 验证码
	public String PostmanName = ""; // 投递员名称
	public String CompanyID = ""; // 投递公司编号
	public String InputMobileFlag = ""; // 转入标志
	public String BoxList = ""; // 格口号列表
	public String VerifyFlag = ""; // 认证标志
	public int ExpiredCount; // 逾期包裹数
	public int ExpiredDay; // 逾期天数

}
