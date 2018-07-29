package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBLockStatusMod implements IRequest{
	public String FunctionID = "210210"; //功能编号

	public String OperID = ""; //管理员编号
	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String BoxNo = ""; //箱门编号
	public String LockStatus = ""; //锁定状态
	public String BoxType = ""; //箱类型编号
	public String RemoteFlag = ""; //远程操作标志

}