package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamMBInboxStatus implements IRequest{
    public String BoxNo = ""; //格口号
	public String BoxStatus = ""; //格口状态 0:空闲 1:占用 2:故障
	public String PackageID = ""; //包裹编号
    public String Mobile = ""; //收件人手机号码
	public String PostmanID = ""; //投递员编号
    public String CompanyID = ""; //投递公司编号
    public String TradeWaterNo = ""; //交易流水号
    public Date StoredTime = DateUtils.getMinDate(); //存物时间
    public String ArticleStatus = ""; //物品检测状态 (0无物，1有物,-1未知默认)
    public String OpenStatus = "";   //门锁检测状态 (0开，1关,-1未知默认)

}