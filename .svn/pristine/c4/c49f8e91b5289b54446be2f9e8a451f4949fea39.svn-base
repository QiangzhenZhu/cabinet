package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;


import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBModRegisterFlag;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class MBModRegisterFlag extends AbstractLocalBusiness {
	public String doBusiness(InParamMBModRegisterFlag inParam)
			throws DcdzSystemException {
		String result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		DBSContext.registerFlag = result;

		if (StringUtils.isNotEmpty(inParam.MBDeviceNo))
			DBSContext.terminalUid = inParam.MBDeviceNo; // 运营商设备编号

		if (StringUtils.isNotEmpty(inParam.TerminalPasswd))
			DBSContext.sysInfo.TerminalPasswd = inParam.TerminalPasswd;

		String mac = NetUtils.getMac();
		if (StringUtils.isNotEmpty(mac))
			DBSContext.signMac = mac;

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamMBModRegisterFlag inParam = (InParamMBModRegisterFlag) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();

		setCols.add("RegisterFlag", inParam.RegisterFlag);
		setCols.checkAdd("MBDeviceNo", inParam.MBDeviceNo); // 宁夏奕鑫

		setCols.add("MacAddr", NetUtils.getMac());

		whereCols.add("TerminalNo", inParam.TerminalNo);
		try {
			terminalDAO.update(database, setCols, whereCols);

			// 终端通讯密码(广州邮政)
			if (StringUtils.isNotEmpty(inParam.TerminalPasswd)) {
				SMSystemInfoDAO systemDAO = DAOFactory.getSMSystemInfoDAO();
				JDBCFieldArray setCols1 = new JDBCFieldArray();
				JDBCFieldArray whereColsDummy = new JDBCFieldArray();

				setCols1.add("TerminalPasswd", inParam.TerminalPasswd);

				systemDAO.update(database, setCols1, whereColsDummy);
			}

			result = inParam.RegisterFlag;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
