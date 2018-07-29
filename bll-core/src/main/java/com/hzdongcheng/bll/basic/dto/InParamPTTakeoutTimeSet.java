package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTTakeoutTimeSet implements IRequest{
	public String FunctionID = "330517"; //功能编号

	public String OperID = ""; //管理员编号
	public String TakeOutCtrlTime = ""; //取件控制时间
	public String TakeOutCtrlWeekend = ""; //取件控制周末时间
	public String TakeOutTime11 = ""; //取件控制时间段1
	public String TakeOutTime12 = ""; //取件控制时间段2
	public String TakeOutTime13 = ""; //取件控制时间段3
	public String TakeOutTime14 = ""; //取件控制时间段4
	public String Urgent1Mobile = ""; //紧急联系人1手机
	public String Urgent2Mobile = ""; //紧急联系人2手机

}