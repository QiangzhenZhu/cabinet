package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTModPackageStatus;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class PTModPackageStatus extends AbstractLocalBusiness {
	public String doBusiness(InParamPTModPackageStatus inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTModPackageStatus inParam = (InParamPTModPackageStatus) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 订单状态为0:正常 1:锁定 3:超时
		if (SysDict.PACKAGE_STATUS_NORMAL != inParam.PackageStatus
				&& SysDict.PACKAGE_STATUS_LOCKED != inParam.PackageStatus
				&& SysDict.PACKAGE_STATUS_TIMEOUT != inParam.PackageStatus)
			throw new DcdzSystemException(
					DBSErrorCode.ERR_BUSINESS_PACKSTATUSABNORMAL);

		// 验证订单是否存在
		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		try {
			PTInBoxPackage inboxPack = CommonDAO.findPackageByID(database, inParam.PackageID);
			if (inboxPack == null)
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
			// 修改订单状态
			JDBCFieldArray setCols1 = new JDBCFieldArray();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			setCols1.add("PackageStatus", inParam.PackageStatus);
			whereCols1.add("PackageID", inParam.PackageID);

			inboxPackDAO.update(database, setCols1, whereCols1);

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag + ",packid=" + inParam.PackageID
					+ ",packstatus=" + inParam.PackageStatus;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}
