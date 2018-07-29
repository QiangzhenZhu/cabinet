package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTVerfiyUser implements IRequest {
	public String FunctionID = "330201"; // 功能编号

	public String TerminalNo = ""; // 设备号
	public String TradeWaterNo = ""; // 交易流水号
	public String CustomerID = ""; // 取件人身份标识
	public String OpenBoxKey = ""; // 开箱密码
	public String CompanyID = ""; // 投递公司编号
	public String Remark = "";
}
