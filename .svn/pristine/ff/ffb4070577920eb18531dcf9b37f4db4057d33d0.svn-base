package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTInboxPackQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.List;

public class PTInboxPackQry extends AbstractLocalBusiness {

	public List<PTInBoxPackage> doBusiness(InParamPTInboxPackQry inParam)
			throws DcdzSystemException {
		List<PTInBoxPackage> result;
		result = callMethod(inParam);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<PTInBoxPackage> handleBusiness(IRequest inparam) throws DcdzSystemException {
		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getInstance()
				.getPTInBoxPackageDAO();
		List<PTInBoxPackage> inboxPack = null;
		InParamPTInboxPackQry param=(InParamPTInboxPackQry)inparam;
		
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("CustomerID", param.CustomerID);
		try {
			inboxPack = inboxPackDAO.executeQuery(database, whereCols);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST,
					e.getMessage());
		}
		if (inboxPack == null) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
		}
		return inboxPack;
	}

}
