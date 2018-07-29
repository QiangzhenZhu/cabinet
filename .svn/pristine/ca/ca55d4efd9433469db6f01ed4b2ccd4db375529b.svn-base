package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamOPOperLogQryCount;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import java.util.Date;

public class OPOperLogQryCount extends AbstractLocalBusiness {
	public int doBusiness(InParamOPOperLogQryCount inParam) throws DcdzSystemException {
		int result;
		result = (int) callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object handleBusiness(IRequest request) throws DcdzSystemException {

		InParamOPOperLogQryCount inParam = (InParamOPOperLogQryCount) request;
		int result = 0;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		Date sysDate = DateUtils.nowDate();
		if(inParam.BeginDate ==null){
			inParam.BeginDate = DateUtils.addDay(sysDate, -30);
		}
		if(inParam.EndDate ==null){
			inParam.EndDate = sysDate;
		}
		JDBCFieldArray whereSQL = new JDBCFieldArray();
		whereSQL.add("OccurDate", ">=", inParam.BeginDate);
		whereSQL.add("OccurDate", "<=", inParam.EndDate);

		if (StringUtils.isNotEmpty(inParam.UserID))
			whereSQL.addSQL(" AND (UserID = "
					+ StringUtils.addQuote(inParam.UserID) + " OR OperID = "
					+ StringUtils.addQuote(inParam.UserID) + ") ");
		whereSQL.checkAdd("OperID", inParam.UserID);
		whereSQL.checkAdd("OperType", inParam.OperType);
		whereSQL.checkAdd("FunctionID", inParam.FactFunctionID);

		/*String sql = "SELECT COUNT(OperLogID) FROM V_OperatorLog WHERE 1=1 "
				+ whereSQL.PreparedWhereSQL();

		result = dbWrapper.executeQueryCount(sql, whereSQL);*/
		result = DbUtils.executeCount(database, "V_OperatorLog", whereSQL);

		return result;
	}
}
