package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.bll.common.IRequest;

public class InParamMBUploadCloseBox implements IRequest {
    public String  FunctionID = ""; //功能编号
    public String OperID = ""; //请求开箱用户ID
    public String BoxNo = ""; //箱号
    public String TerminalNo = ""; //设备号
    public String TradeWaterNo = ""; //交易流水号
    public String ArticleStatus = ""; //物品状态 0-无物  1-有物 9-未知
    public String DoorStatus = "";//门状态： 0-关  1-开 9-未知
    public String CloseBoxType= "";//关箱类型1-存关箱 2-取关箱 3-管理关箱 9-其他关箱
    public String OccurTime  =  "" ;//关箱时间
}

