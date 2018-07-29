package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;


public class OutParamPTPostmanLogin {
    public String PostmanID = ""; //投递员编号
    public String PostmanName = ""; //投递员名称
    public String PostmanType = ""; //投递员类型
    public String CompanyID = ""; //投递公司编号
    public String InputMobileFlag = ""; //转入标志
    public String DynamicCode = ""; //动态码
    public String VerifyFlag = ""; //认证标志(0：获取动态密码
    public String BoxList = ""; //格口号列表
    public double Balance; //账户余额
    public int ExpiredCount; //逾期包裹数
    public Date ExpiredDate = DateUtils.getMinDate(); //逾期日期
    public String OfBureau = ""; //所属投递段
    public String Remark = ""; //备注

}