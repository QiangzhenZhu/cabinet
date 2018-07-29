package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

import java.util.Date;

public class InParamOPOperLogQry implements IRequest{
	public String FunctionID = "132061"; //功能编号

	public int RecordBegin = 0; 
	public int RecordNum = 0; 

	public String OperID = ""; //登陆管理员编号
	public Date BeginDate = null; //开始日期
    public Date EndDate = null; //结束日期
	public String UserID = ""; //管理员编号
	public int OperType; //管理员类型
	public String OperName = ""; //管理员姓名
	public String FactFunctionID = ""; //功能编号

}