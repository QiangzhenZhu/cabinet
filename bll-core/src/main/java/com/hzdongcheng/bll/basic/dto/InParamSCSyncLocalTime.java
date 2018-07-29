package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamSCSyncLocalTime implements IRequest{
	public String FunctionID = "510103"; //功能编号

	public Date ServerTime = DateUtils.getMinDate(); //服务器时间

}