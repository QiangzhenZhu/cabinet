package com.hzdongcheng.bll.basic.dto;

import com.hzdongcheng.bll.common.IRequest;

public class InParamSMGetUpgradeInfo implements IRequest {
    public String FunctionID = "110541"; //功能编号

    public String TerminalNo = ""; //设备号
    public String SoftwareType = ""; //软件类型：1-驱动软件 2-终端软件
    public String SoftwareVersion = ""; //软件版本号
}
