package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.OPOperatorDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamOPDelSpotOperator;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import java.util.Date;
import com.hzdongcheng.bll.common.CommonDAO;

public class OPDelSpotOperator extends AbstractLocalBusiness {
	public String doBusiness(InParamOPDelSpotOperator inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamOPDelSpotOperator inParam = (InParamOPDelSpotOperator) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.ByOperID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();
		try {
			OPOperatorDAO operDAO = DAOFactory.getOPOperatorDAO();
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("OperID", inParam.ByOperID);
			if (operDAO.isExist(database, whereCols)>0) {

				operDAO.delete(database, whereCols);

				// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
				OPOperatorLog log = new OPOperatorLog();
				log.OperID = inParam.OperID;
				log.FunctionID = inParam.FunctionID;
				log.OccurTime = sysDateTime;
				log.Remark = inParam.ByOperID;

				CommonDAO.addOperatorLog(database, log);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
