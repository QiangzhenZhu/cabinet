package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamPTReSentOpenBoxKey implements IRequest {
	public String FunctionID = "330515"; // 功能编号

	public String TerminalNo = ""; // 设备号
	public String TradeWaterNo = ""; // 交易流水号
	public String PostmanID = ""; // 投递员编号
	public String DynamicCode = ""; // 动态码
	public String CompanyID = ""; // 投递公司编号
	public String PackageID = ""; // 订单号
	public String CustomerMobile = ""; // 取件人手机
	public Date OccurTime = DateUtils.getMinDate(); // 发生时间
	public String Remark = "";
}
