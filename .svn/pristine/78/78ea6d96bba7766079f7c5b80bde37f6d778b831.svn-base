package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBIsExistMasterType;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;

public class TBIsExistMasterType extends AbstractLocalBusiness {
	public int doBusiness(InParamTBIsExistMasterType inParam)
			throws DcdzSystemException {
		int result;
		result = (int) callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBIsExistMasterType inParam = (InParamTBIsExistMasterType) request;
		int result = 0;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("BoxType", SysDict.BOX_TYPE_MASTER);

		try {
			result = boxDAO.isExist(database, whereCols);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}
