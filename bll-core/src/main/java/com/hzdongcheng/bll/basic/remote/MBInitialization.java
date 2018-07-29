package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;


import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBInitialization;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.List;


import com.hzdongcheng.persistent.db.JDBCFieldArray;

/**
 * 设备安装初始化
 * 
 * @author Shalom
 *
 */
public class MBInitialization extends AbstractRemoteBusiness {

	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut)
			throws DcdzSystemException {
		if (timeOut == 0)
			timeOut = 20;
		isUseDB = true;
		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) throws DcdzSystemException {
		String result = "";
		InParamMBInitialization inParam = (InParamMBInitialization) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		// 查询柜体信息
		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		try {
			JDBCFieldArray findWhereCols= new JDBCFieldArray();
			findWhereCols.add("TerminalNo",inParam.TerminalNo);
			terminal = terminalDAO.find(database, findWhereCols);

			// 箱体或柜体为空的时候，禁止注册
			if (terminal.DeskNum == 0 || terminal.BoxNum == 0)
				throw new DcdzSystemException(
						DBSErrorCode.ERR_BUSINESS_DESKNOTALLOWEDEMPTY);

			// 查询系统信息
			SMSystemInfoDAO systemDAO = DAOFactory.getSMSystemInfoDAO();
			JDBCFieldArray whereCols = new JDBCFieldArray();
			SMSystemInfo systemInfo = systemDAO.find(database, whereCols);

			// 查询箱体信息
			TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
			JDBCFieldArray whereColsDummy = new JDBCFieldArray();
			List<TBBox> boxList = boxDAO.executeQuery(database, whereColsDummy);

			// //////////////////////////////////////////
			if (StringUtils.isNotEmpty(inParam.InitPasswd)) {
				JDBCFieldArray setCols2 = new JDBCFieldArray();
				JDBCFieldArray whereCols2 = new JDBCFieldArray();

				setCols2.add("InitPasswd", inParam.InitPasswd);
				whereCols2.add("SystemID", systemInfo.SystemID);

				systemDAO.update(database, setCols2, whereCols2);
			}

			// 重新查询
			terminal = terminalDAO.find(database, findWhereCols);
			terminal.BoxNum = boxList.size();

			// 赋值
			inParam.TerminalNo = terminal.TerminalNo;
			inParam.RegisterFlag = terminal.RegisterFlag; // 采用原始的注册标志
			inParam.SoftwareVersion = systemInfo.SoftwareVersion;
			// ?????(AppConfig读取)
			// inParam.InitPasswd = systemInfo.InitPasswd;
			inParam.SignMac = terminal.MacAddr;
			// inParam.SignMac = NetUtils.GetMac();
			inParam.SignIP = NetUtils.getIP();

			inParam.TerminalInfo = PacketUtils.serializeObject(terminal);
			// inParam.BoxInfo = PacketUtils.SerializeObject(boxList);
			StringBuilder sb = new StringBuilder(512);
			for (TBBox obj : boxList) {
				sb.append(String.format("%s,%s,%s,%s,%s,%s", obj.BoxNo,
						EscapeStr(obj.BoxName), obj.DeskNo, obj.DeskBoxNo,
						obj.BoxType, obj.BoxStatus));
				sb.append("~");
			}

			inParam.BoxInfo = sb.toString();

			if (responder != null)
				netProxy.sendRequest(request, responder, timeOut,
						DBSContext.secretKey);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_TERMINALNOTSAVED);
		}
		return result;
	}

	private String EscapeStr(String str) {
		if (StringUtils.isEmpty(str))
			return "";

		char[] cnames = str.toCharArray();
		for (int i = 0; i < cnames.length; i++) {
			if (cnames[i] == ',')
				cnames[i] = ' ';
			else if (cnames[i] == '~')
				cnames[i] = ' ';
		}

		return new String(cnames);
	}
}
