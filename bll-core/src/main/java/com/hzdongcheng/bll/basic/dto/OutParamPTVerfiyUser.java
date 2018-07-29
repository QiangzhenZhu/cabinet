package com.hzdongcheng.bll.basic.dto;

public class OutParamPTVerfiyUser{
	public String PackageID = ""; //订单号
	public String BoxNo = ""; //箱门编号
	public String PosPayFlag = ""; //支付标志
    public String CustomerTel = "";//收件人手机号
    public String PostmanID = "";//投递员ID
    public String Company = "";//快递公司
    public String OverPayFlag = "";//逾期包裹付费 1 付费
	public double ExpiredAmt; //超时付费金额
    public String Remark = "";

}