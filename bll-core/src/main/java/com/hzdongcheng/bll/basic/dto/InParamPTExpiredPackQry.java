package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamPTExpiredPackQry implements IRequest{
	public String FunctionID = "330031"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String PostmanID = ""; //投递员编号
	public String CompanyID = ""; //投递公司编号
	public Date ExpiredDate = DateUtils.getMinDate(); //逾期日期

}