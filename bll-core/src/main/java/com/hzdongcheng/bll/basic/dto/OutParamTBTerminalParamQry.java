package com.hzdongcheng.bll.basic.dto;

public class OutParamTBTerminalParamQry{
	public String TerminalNo = ""; //设备号
	public String ScreensoundFlag = ""; //语音提示标志
	public int RefuseCloseDoor; //拒关箱门次数
	public String ExistSuperLargeBox = ""; //存在超大箱体
	public String ExistFreshBox = ""; //存在生鲜箱体
	public String ArticleInspectFlag = ""; //物品检测标志
	public String DoorInspectFlag = ""; //箱门检测标志
	public String PowerInspectFlag = ""; //电源检测标志
	public String ShockInspectFlag = ""; //震动防撬检测标志
	public String LampOnTime ="";//开灯时间
	public String LampOffTime ="";//关灯时间
}