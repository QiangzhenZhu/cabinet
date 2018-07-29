package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSMSysInfoQry;
import com.hzdongcheng.bll.basic.dto.OutParamSMSysInfoQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class SMSysInfoQry extends AbstractLocalBusiness {
	public OutParamSMSysInfoQry doBusiness(InParamSMSysInfoQry inParam)
			throws DcdzSystemException {
		OutParamSMSysInfoQry result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamSMSysInfoQry handleBusiness(IRequest request)
			throws DcdzSystemException {
		//InParamSMSysInfoQry inParam = (InParamSMSysInfoQry) request;
		OutParamSMSysInfoQry result = new OutParamSMSysInfoQry();


		SMSystemInfoDAO sysInfoDAO = DAOFactory.getSMSystemInfoDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		try {
			SMSystemInfo systemInfo = sysInfoDAO.find(database, whereCols);

			result.SystemID = systemInfo.SystemID;
			result.SoftwareVersion = systemInfo.SoftwareVersion;
			result.UpdateContent = systemInfo.UpdateContent;
			result.InitPasswd = systemInfo.InitPasswd;
			result.LastInitPasswd = systemInfo.LastInitPasswd;
			result.TerminalPasswd = systemInfo.TerminalPasswd;
			result.ServerIP = systemInfo.ServerIP;
			result.ServerPort = systemInfo.ServerPort;
			result.ServerSSL = systemInfo.ServerSSL;
			result.MonServerIP = systemInfo.MonServerIP;
			result.MonServerPort = systemInfo.MonServerPort;
			result.LastModifyTime = systemInfo.LastModifyTime;
			result.Remark = systemInfo.Remark;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
