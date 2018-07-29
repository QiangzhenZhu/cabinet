package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamPTPickupPackage implements IRequest {
	public String FunctionID = "330203"; // 功能编号

	public String TerminalNo = ""; // 设备号
	public String TradeWaterNo = ""; // 交易流水号
	public String PostmanID = ""; // 投递员编号
	public String DynamicCode = ""; // 动态码
	public String CompanyID = ""; // 投递公司编号
	public String PackageID = ""; // 订单号
	public String BoxNo = ""; // 箱门编号
	public String CustomerID = ""; // 取件人编号
	public String CustomerMobile = ""; // 取件人手机
	public String CustomerName = ""; // 取件人姓名
	public Date StoredTime = DateUtils.getMinDate(); // 存物时间
	public Date OccurTime = DateUtils.getMinDate(); // 发生时间
	public String OpenBoxKey = ""; // 开箱密码
	public String TakedWay = ""; // 取件方式
	public double OverdueCost;// 逾期取件费用
	public String LeftFlage;// 寄存标志
	public String Remark = "";

	// / <summary>
	// / 寄存业务存在物流编号，有别于订单号
	// / </summary>
	public String LogisticsNo = "";

	public String AgainUploading="";//重传
}
