package com.hzdongcheng.bll.common;

public class FaultResult {
	public final static int FAULT_RESULT_TYPE_SYSTEM = 0; // 系统异常
	public final static int FAULT_RESULT_TYPE_LOCAL = 1; // 本地业务异常
	public final static int FAULT_RESULT_TYPE_REMOTE = 2; // 远程业务异常

	public FaultResult() {

	}

	public FaultResult(String faultCode, String faultString, int faultType) {
		this.faultCode = faultCode;
		this.faultString = faultString;
		this.faultType = faultType;
	}

	// 错误消息全球化
	public String getFaultString() {
		return faultString;
	}

	// / <summary>
	// / The raw content of the fault (if available), such as an HTTP response
	// body.
	// / </summary>
	public Object content;

	// / <summary>
	// / The cause of the fault. The value will be null if the cause is
	// / unknown or whether this fault represents the root itself.
	// / </summary>
	public Object rootCause;

	// / <summary>
	// / A simple code describing the fault.
	// / </summary>
	public String faultCode;

	// / <summary>
	// / 错误类型
	// / </summary>
	public int faultType;

	// / <summary>
	// / Text description of the fault.
	// / </summary>
	public String faultString;

}
