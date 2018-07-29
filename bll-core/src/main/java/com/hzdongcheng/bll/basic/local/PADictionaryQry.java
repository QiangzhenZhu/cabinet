package com.hzdongcheng.bll.basic.local;


import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPADictionaryQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PADictionaryDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PADictionary;

import java.util.List;

public class PADictionaryQry extends AbstractLocalBusiness {
	public List<PADictionary> doBusiness(InParamPADictionaryQry inParam)
			throws DcdzSystemException {
		List<PADictionary> result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<PADictionary> handleBusiness(IRequest request) throws DcdzSystemException{
		InParamPADictionaryQry inParam = (InParamPADictionaryQry) request;
		List<PADictionary> result = null;

		JDBCFieldArray whereSQL = new JDBCFieldArray();
		whereSQL.checkAdd("DictTypeID", inParam.DictTypeID);
		whereSQL.checkAdd("DictID", inParam.DictID);

		/*String sql = "SELECT * FROM PADictionary WHERE 1=1 "
				+ whereSQL.PreparedWhereSQL() + " ORDER BY DictTypeID,DictID";

		Cursor cs = dbWrapper.executeQuery(sql, whereSQL);
		result=MyDbHelper.readEntityListByReader(cs,PADictionary.class);*/
		PADictionaryDAO dictionaryDAO = DAOFactory.getPADictionaryDAO();
		result= dictionaryDAO.executeQuery(database, whereSQL);

		return result;
	}
}
