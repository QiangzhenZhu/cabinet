package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPAControlParamQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PATerminalCtrlParam;

import java.util.List;

public class PAControlParamQry extends AbstractLocalBusiness {
	public List<PATerminalCtrlParam> doBusiness(InParamPAControlParamQry inParam)
			throws DcdzSystemException {
		List<PATerminalCtrlParam> result;

		result = callMethod(inParam);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<PATerminalCtrlParam> handleBusiness(IRequest request) throws DcdzSystemException{
		InParamPAControlParamQry inParam = (InParamPAControlParamQry) request;

		List<PATerminalCtrlParam> result = null;

		JDBCFieldArray whereSQL = new JDBCFieldArray();
		whereSQL.checkAdd("CtrlTypeID", inParam.CtrlTypeID);
		whereSQL.checkAdd("KeyString", inParam.KeyString);

		/*String sql = "SELECT * FROM PAControlParam WHERE 1=1 ";
		sql = sql + whereSQL.PreparedWhereSQL();

		Cursor cs = dbWrapper.executeQuery(sql, whereSQL);
		result= MyDbHelper.readEntityListByReader(cs,PATerminalCtrlParam.class);*/
		result= DbUtils.executeQuery(database, "PAControlParam", whereSQL, PATerminalCtrlParam.class);

		return result;
	}

}
