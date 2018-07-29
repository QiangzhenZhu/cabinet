package com.hzdongcheng.bll.basic.dto;

public class OutParamMBTerminalLedMsgQry{
	public String TerminalNo = ""; //设备号
	public String TerminalName = ""; //设备名称
	public String DepartmentID = ""; //运营网点编号
	public String DepartmentName = ""; //运营网点名称
	public String Location = ""; //安装地址
	public String OnlineStatus = ""; //在线状态
	public String OnlineStatusName = ""; //在线状态名称
	public int StartPointX; //起始X位置
	public int StartPointY; //起始Y位置
	public int LedWidth; //屏宽
	public int LedHeight; //屏高
	public int DisplayWay; //显示方式
	public int QuitWay; //退场方式
	public int RunSpeed; //运行速度
	public int StopTime; //停留时间
	public int FontSize; //字体大小
	public int FontColor; //字体颜色
	public String Remark = ""; //备注

}