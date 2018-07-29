package com.hzdongcheng.bll.common;

public class JsonPacket {
	public final static int MSG_SENT_BY_CLIENT = 1;
	public final static int MSG_SENT_BY_SERVER = 2;

	// public string _Version = "1.0";
	public int _CmdType; //消息发起方 1:客户端发起 2:服务端

	public String _MessageId = ""; // 消息ID
	// public string _TerminalUid = ""; ///设备号
	public String _ServiceName = ""; // 服务名称
	public String _Sign = ""; // 消息签名 (body + 客户端密码) md5

	public String body = ""; // 业务数据
}
