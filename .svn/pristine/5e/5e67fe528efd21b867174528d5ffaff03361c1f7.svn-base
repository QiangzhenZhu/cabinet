package com.hzdongcheng.bll.basic.local;


import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTBoxIsUsed;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class PTBoxIsUsed extends AbstractLocalBusiness {
	public String doBusiness(InParamPTBoxIsUsed inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTBoxIsUsed inParam = (InParamPTBoxIsUsed) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.BoxNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("BoxNo", inParam.BoxNo);

		try {
			if (inboxPackDAO.isExist(database, whereCols) > 0)
				result = "1";
			else
				result = "0";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
