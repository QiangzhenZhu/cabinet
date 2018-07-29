package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class InParamMBReportPeripheralStatus implements IRequest{
	public String FunctionID = "150403"; //功能编号

	public String TerminalNo = ""; //设备号
	public String TradeWaterNo = ""; //交易流水号
	public String TerminalStatus = ""; //智能信包箱状态
	public String CardReaderStatus = ""; //读卡器状态
	public String ICStatus = ""; //IC设备状态
	public String IDCardStatus = ""; //IDCard设备状态
	public String BarcodeStatus = ""; //条码设备状态
	public String PowerStatus = ""; //电源状态
	public String PasskeyBoardStatus = ""; //密码键盘状态
	public String PrinterStatus = ""; //打印机状态
	public String Camera1Status = ""; //摄像1状态
	public String Camera2Status = ""; //摄像2状态
	public String Camera3Status = ""; //摄像3状态
	public int FaultNum; //故障箱数量
	public int FreeNum; //空闲箱数量
	public Date LocalTime = DateUtils.getMinDate(); //本地时间
	public String SoftwareVersion = ""; //软件版本
	public String HardwareVersion = ""; //硬件版本
	public String Remark = ""; //备注

}