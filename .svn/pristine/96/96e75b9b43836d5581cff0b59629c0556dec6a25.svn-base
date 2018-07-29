package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PMPostmanDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PMPostman;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPMPushPostmanAdd;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import java.util.Date;


public class PMPushPostmanAdd extends AbstractLocalBusiness {
	public String doBusiness(InParamPMPushPostmanAdd inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPMPushPostmanAdd inParam = (InParamPMPushPostmanAdd) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.CompanyID)
				|| StringUtils.isEmpty(inParam.PostmanType)
				|| StringUtils.isEmpty(inParam.Password))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.OperID))
			inParam.OperID = "191919";

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// /////////////////////////////////////////////////
		PMPostmanDAO postmanDAO = DAOFactory.getPMPostmanDAO();
		PMPostman postman = new PMPostman();

		// /////////////////////////////
		try {
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("PostmanID",inParam.PostmanID);
			postmanDAO.delete(database, whereCols);

			// //////////////////////////////////////////////////////////////
			postman.PostmanName = StringUtils.isEmpty(inParam.PostmanName) ? inParam.PostmanID
					: inParam.PostmanName;
			postman.CompanyID = inParam.CompanyID;
			postman.PostmanType = inParam.PostmanType;
			postman.Password = inParam.Password;
			postman.InputMobileFlag = inParam.InputMobileFlag;
			postman.PostmanStatus = "0";
			postman.CreateTime = sysDateTime;

			postmanDAO.insert(database, postman);

			// /////////////////////////////
			// 箱体权限如何控制

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.PostmanID;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			log.error("[PMPushPostmanAdd]" + e.getMessage());
		}

		return result;
	}
}