package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class OutParamPTDeliveryRecordQry{
	public String TerminalNo = ""; //设备号
	public String BoxNo = ""; //箱门编号
	public String PackageID = ""; //订单号
	public String PostmanID = ""; //投递员编号
	public String CompanyID = ""; //投递公司编号
    public Date StoredTime = DateUtils.getMinDate(); //存物时间
    public Date TakedTime = DateUtils.getMinDate(); //取物时间
    public Date ExpiredTime = DateUtils.getMinDate(); //逾期时间
	public String CustomerMobile = ""; //取件人手机
	public String PackageStatus = ""; //包裹状态
	public String PackageStatusName = ""; //包裹状态名称
	public String UploadFlag = ""; //上传标志
	public String UploadFlagName = ""; //上传标志名称

}