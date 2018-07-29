package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBDeskDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBDesk;

import java.util.List;

public class TBDeskQry extends AbstractLocalBusiness {
	public List<TBDesk> doBusiness(InParamTBDeskQry inParam) throws DcdzSystemException {
		List<TBDesk> result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<TBDesk> handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBDeskQry inParam = (InParamTBDeskQry) request;
		List<TBDesk> result = null;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.checkAdd("DeskNo", inParam.DeskNo);


		/*String sql = "SELECT * FROM TBDesk WHERE 1=1 "
				+ whereSQL.PreparedWhereSQL() + " ORDER BY DeskNo";
		Cursor cs = dbWrapper.executeQuery(sql, whereSQL);
		result= MyDbHelper.readEntityListByReader(cs,TBDesk.class);*/
		TBDeskDAO deskDAO = DAOFactory.getTBDeskDAO();
		result = deskDAO.executeQuery(database,whereCols, "DeskNo");
		return result;
	}
}
