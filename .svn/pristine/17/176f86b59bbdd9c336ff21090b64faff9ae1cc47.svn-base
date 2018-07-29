package com.hzdongcheng.bll.basic.local;


import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.RMAppealLogDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamRMWriteLocalOpenBoxKey;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class RMWriteLocalOpenBoxKey extends AbstractLocalBusiness {
	public String doBusiness(InParamRMWriteLocalOpenBoxKey inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamRMWriteLocalOpenBoxKey inParam = (InParamRMWriteLocalOpenBoxKey) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.AppealNo)
				|| StringUtils.isEmpty(inParam.OpenBoxKey))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		RMAppealLogDAO appealLogDAO = DAOFactory.getRMAppealLogDAO();
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();

		setCols.add("OpenBoxKey", inParam.OpenBoxKey.toLowerCase());
		setCols.add("AppealStatus", SysDict.APPEAL_STATUS_READYOPEN);

		whereCols.add("AppealNo", inParam.AppealNo);

		try {
			appealLogDAO.update(database, setCols, whereCols);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}
