package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamPTModPackageInfo implements IRequest {
	public String FunctionID = "330305"; // 功能编号

	public String OperID = ""; // 管理员编号
	public String TerminalNo = ""; // 设备号
	public String PackageID = ""; // 订单号
	public String OpenBoxKey = "";// 开箱密码
	public String CompanyID = ""; // 投递公司编号
	public String RemoteFlag = ""; // 远程操作标志
}
