package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamRMRequestOpenBoxKey implements IRequest{
	public String FunctionID = "550021"; //功能编号

	public String AppealUser = ""; //求助用户
	public String AppealType = ""; //求助类型
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号
	public String CustomerMobile = ""; //取件人手机
	public Date StoredTime = DateUtils.getMinDate(); //存物时间
	public Date TakedTime = DateUtils.getMinDate(); //取件时间
	public String AppealNo = ""; //求助编号
	public String TerminalNo = ""; //设备号
	public String PostmanID = ""; //投递员编号
	public String DynamicCode = ""; //动态码

}