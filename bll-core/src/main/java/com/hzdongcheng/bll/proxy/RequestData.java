package com.hzdongcheng.bll.proxy;


import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;

public class RequestData {
	public IRequest request;
    public IResponder responder;
    public int timeOut = 30;
    public String secretKey = "";
}
