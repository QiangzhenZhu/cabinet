package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBReOpenBox4Cancel;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class TBReOpenBox4Cancel extends AbstractLocalBusiness {
	public String doBusiness(InParamTBReOpenBox4Cancel inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBReOpenBox4Cancel inParam = (InParamTBReOpenBox4Cancel) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID)
				|| StringUtils.isEmpty(inParam.BoxNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		// ////////////////////////////////////////////////////////////////////////
		// //判断箱门是否已经再使用
		JDBCFieldArray whereCols1 = new JDBCFieldArray();
		whereCols1.add("BoxNo", inParam.BoxNo);
		try {
			if (inboxPackDAO.isExist(database, whereCols1) > 0)
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_BOXHAVEUSED);

			TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			whereCols0.add("BoxNo", inParam.BoxNo);

			TBBox box = boxDAO.find(database, whereCols0);

			// ////////////////////////////////////////////////////////////////////////
			HAL.openBox(box.BoxName);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
