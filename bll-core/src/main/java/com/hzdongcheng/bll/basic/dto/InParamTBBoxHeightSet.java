package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;

public class InParamTBBoxHeightSet implements IRequest{
	public String FunctionID = "210292"; //功能编号

	public String OperID = ""; //管理员编号
	public int TerminalHeight; //柜体高度
	public int MiniHeight; //迷你箱高度
	public int SmallHeight; //小箱高度
	public int MediumHeight; //中箱高度
	public int LargeHeight; //大箱高度
	public int SuperHeight; //超大箱高度
	public int MasterHeight; //主控箱高度
	public int AdvertisingHeight; //广告箱高度

}