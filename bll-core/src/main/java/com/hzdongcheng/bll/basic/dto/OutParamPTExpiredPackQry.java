package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class OutParamPTExpiredPackQry {
    public String PID = ""; //订单号
    public Date STM = DateUtils.getMinDate(); //存物时间
    public String BNO = ""; //箱门编号
    public String MOB = ""; //取件人手机
    public String STS = ""; //包裹状态

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public Date getSTM() {
        return STM;
    }

    public void setSTM(Date STM) {
        this.STM = STM;
    }

    public String getBNO() {
        return BNO;
    }

    public void setBNO(String BNO) {
        this.BNO = BNO;
    }

    public String getMOB() {
        return MOB;
    }

    public void setMOB(String MOB) {
        this.MOB = MOB;
    }

    public String getSTS() {
        return STS;
    }

    public void setSTS(String STS) {
        this.STS = STS;
    }

}