package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTResetOpenBoxKey;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class PTResetOpenBoxKey extends AbstractLocalBusiness {
	public String doBusiness(InParamPTResetOpenBoxKey inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTResetOpenBoxKey inParam = (InParamPTResetOpenBoxKey) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID)
				|| StringUtils.isEmpty(inParam.CustomerMobile)
				|| StringUtils.isEmpty(inParam.OpenBoxKey))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 2. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 验证订单是否存在
		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		
		CommonDAO.findPackageByID(database, inParam.PackageID);

		// 重置提货码
		JDBCFieldArray setCols1 = new JDBCFieldArray();
		JDBCFieldArray whereCols1 = new JDBCFieldArray();
		setCols1.add("CustomerMobile", inParam.CustomerMobile);
		setCols1.add("OpenBoxKey", inParam.OpenBoxKey);
		whereCols1.add("PackageID", inParam.PackageID);
		try {
			inboxPackDAO.update(database, setCols1, whereCols1);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag + ",packid=" + inParam.PackageID;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}
