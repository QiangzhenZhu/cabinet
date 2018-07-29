package com.hzdongcheng.bll.basic.dto;


import com.hzdongcheng.bll.common.IRequest;


public class InParamMBSendUpgradeInform implements IRequest {
	public String FunctionID = "150345"; //功能编号

	public String OperID = ""; //操作员编号
	public String TerminalNoStr = ""; //设备列表
	public String UploadKey = ""; //上传Key

}