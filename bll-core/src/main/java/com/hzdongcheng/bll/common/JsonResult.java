package com.hzdongcheng.bll.common;

import java.lang.reflect.Type;

public class JsonResult {
	public boolean success;
	public String msg;
	public String result;

	/**
	 * 把业务输出的结果字符串转为相应的业务输出DTO
	 */
	public <T> T Eval(Type type) {
		return PacketUtils.<T> Eval(result,type);
	}
}
