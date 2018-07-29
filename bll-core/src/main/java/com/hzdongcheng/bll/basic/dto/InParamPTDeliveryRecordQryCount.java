package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamPTDeliveryRecordQryCount implements IRequest{
	public String FunctionID = "330045"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号
	public String PackageStatus = ""; //包裹状态
	public String PostmanID = ""; //投递员编号
	public String CompanyID = ""; //投递公司编号
	public String CustomerMobile = ""; //取件人手机
    public Date BeginDate = DateUtils.getMinDate(); //开始日期
    public Date EndDate = DateUtils.getMinDate(); //结束日期
	public String InboxFlag = ""; //在箱标志

}