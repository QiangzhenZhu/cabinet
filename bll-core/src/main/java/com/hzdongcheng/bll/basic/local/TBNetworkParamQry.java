package com.hzdongcheng.bll.basic.local;


import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBNetworkParamQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBNetworkParamQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SMSystemInfo;

public class TBNetworkParamQry extends AbstractLocalBusiness {
	public OutParamTBNetworkParamQry doBusiness(InParamTBNetworkParamQry inParam)
			throws DcdzSystemException {
		OutParamTBNetworkParamQry result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamTBNetworkParamQry handleBusiness(IRequest request) throws DcdzSystemException {
		//InParamTBNetworkParamQry inParam = (InParamTBNetworkParamQry) request;
		OutParamTBNetworkParamQry result = new OutParamTBNetworkParamQry();

		// 1. 验证输入参数是否有效，如果无效返回-1。
		SMSystemInfoDAO sysInfoDAO = DAOFactory.getSMSystemInfoDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		try {
			SMSystemInfo sysInfo = sysInfoDAO.find(database, whereCols0);

			result.ServerIP = sysInfo.ServerIP;
			result.ServerPort = sysInfo.ServerPort;
			result.ServerSSL = sysInfo.ServerSSL;
			result.InitPasswd = sysInfo.InitPasswd;
			result.SoftwareVersion = sysInfo.SoftwareVersion;

			result.MonServerIP = sysInfo.MonServerIP;
			result.MonServerPort = sysInfo.MonServerPort;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
