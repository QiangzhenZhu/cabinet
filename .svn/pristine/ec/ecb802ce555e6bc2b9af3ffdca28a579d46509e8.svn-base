package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxQryCount;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class TBBoxQryCount extends AbstractLocalBusiness {
	public int doBusiness(InParamTBBoxQryCount inParam) throws DcdzSystemException {
		int result;
		result = (int) callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object handleBusiness(IRequest request) throws DcdzSystemException{
		InParamTBBoxQryCount inParam = (InParamTBBoxQryCount) request;
		int result = 0;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		JDBCFieldArray whereSQL = new JDBCFieldArray();
		whereSQL.checkAdd("DeskNo", inParam.DeskNo);
		whereSQL.checkAdd("BoxNo", inParam.BoxNo);
		whereSQL.checkAdd("BoxStatus", inParam.BoxStatus);

//		String sql = "SELECT COUNT(BoxNo) FROM TBBox WHERE 1=1 "
//				+ whereSQL.PreparedWhereSQL();
//
//		result = dbWrapper.executeQueryCount(sql, whereSQL);
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		result= boxDAO.isExist(database, whereSQL);
		return result;
	}
}
