package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamPTWithdrawExpiredPack implements IRequest {
	public String FunctionID = "330033"; // 功能编号

	public String TerminalNo = ""; // 设备号
	public String TradeWaterNo = ""; // 交易流水号
	public String PostmanID = ""; // 投递员编号
	public String DynamicCode = ""; // 动态码
	public String CompanyID = ""; // 投递公司编号
	public String PackageID = ""; // 订单号
	public String BoxNo = ""; // 箱门编号
	public String CustomerMobile = ""; // 取件人手机
	public String PackageStatus = ""; // 包裹状态
	public String LeftFlag = "";
	public Date StoredTime = DateUtils.getMinDate(); // 存物时间
	public Date OccurTime = DateUtils.getMinDate(); // 发生时间
	public String Remark = "";
	public String  AgainUploading="";//重传
}
