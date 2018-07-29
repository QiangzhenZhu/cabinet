package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;


public class OutParamPTOneDeliveryRecordQry{
	public String PackageID = ""; //订单号
    public Date StoredTime = DateUtils.getMinDate(); //存物时间
    public Date TakedTime = DateUtils.getMinDate(); //取物时间
	public String PackageStatus = ""; //包裹状态
	public String PackageStatusName = ""; //包裹状态名称

}