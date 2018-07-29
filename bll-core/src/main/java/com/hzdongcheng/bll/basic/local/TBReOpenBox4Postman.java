package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBReOpenBox4Postman;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class TBReOpenBox4Postman extends AbstractLocalBusiness {
	public String doBusiness(InParamTBReOpenBox4Postman inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBReOpenBox4Postman inParam = (InParamTBReOpenBox4Postman) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.BoxNo)
				|| StringUtils.isEmpty(inParam.PostmanID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("BoxNo", inParam.BoxNo);
		try {
			TBBox box = boxDAO.find(database, whereCols0);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.PostmanID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.BoxNo;

			CommonDAO.addOperatorLog(database, log);

			// ////////////////////////////////////////////////////////////////////////
			HAL.openBox(box.BoxName);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}

		return result;
	}
}
