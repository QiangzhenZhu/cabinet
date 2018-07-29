package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;


public class OutParamPTDownloadInboxInfo{
	public String TradeWaterNo = ""; //交易流水号
	public String PostmanID = ""; //投递员编号
	public String DynamicCode = ""; //动态码
	public String CompanyID = ""; //投递公司编号
	public String PackageID = ""; //订单号
	public String BoxNo = ""; //箱门编号
	public Date StoredTime = DateUtils.getMinDate(); //存物时间
	public String CustomerID = ""; //取件人编号
	public String CustomerMobile = ""; //取件人手机
	public String OpenBoxKey = ""; //开箱密码

}