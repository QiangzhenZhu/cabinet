package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamOPOperLogQry;
import com.hzdongcheng.bll.basic.dto.OutParamOPOperLogQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import java.util.Date;
import java.util.List;

public class OPOperLogQry extends AbstractLocalBusiness {
	public List<OutParamOPOperLogQry> doBusiness(InParamOPOperLogQry inParam) throws DcdzSystemException {
		List<OutParamOPOperLogQry> result;
		result = callMethod(inParam);
		return result;
	}

	protected List<OutParamOPOperLogQry> handleBusiness(IRequest request) throws DcdzSystemException{
		InParamOPOperLogQry inParam = (InParamOPOperLogQry) request;
		List<OutParamOPOperLogQry> result = null;


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

		/*if (StringUtils.isNotEmpty(inParam.UserID)) {
			whereSQL.addSQL(" AND (UserID = "
					+ StringUtils.addQuote(inParam.UserID) + " OR OperID = "
					+ StringUtils.addQuote(inParam.UserID) + ") ");
		}*/

		whereSQL.checkAdd("OperID", inParam.UserID);
		whereSQL.checkAdd("OperType", inParam.OperType);
		whereSQL.checkAdd("FunctionID", inParam.FactFunctionID);
		result = DbUtils.executeQuery(database, "V_OperatorLog", whereSQL, OutParamOPOperLogQry.class, "OperLogID DESC", inParam.RecordBegin,inParam.RecordNum);

		return result;
	}
}
