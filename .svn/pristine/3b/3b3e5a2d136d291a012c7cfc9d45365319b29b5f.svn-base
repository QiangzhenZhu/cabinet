package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamRMCheckCustomerRequest;
import com.hzdongcheng.bll.basic.dto.OutParamRMCheckCustomerRequest;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RMCheckCustomerRequest extends AbstractLocalBusiness {
	public OutParamRMCheckCustomerRequest doBusiness(
			InParamRMCheckCustomerRequest inParam) throws DcdzSystemException {
		OutParamRMCheckCustomerRequest result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamRMCheckCustomerRequest handleBusiness(IRequest request)
			throws DcdzSystemException {
		InParamRMCheckCustomerRequest inParam = (InParamRMCheckCustomerRequest) request;
		OutParamRMCheckCustomerRequest result = new OutParamRMCheckCustomerRequest();

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID)
				|| StringUtils.isEmpty(inParam.OpenBoxKey)
				|| StringUtils.isEmpty(inParam.AppealType))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		PTDeliverHistoryDAO historyPackDAO = DAOFactory
				.getPTDeliverHistoryDAO();

		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("PackageID", inParam.PackageID);
		whereCols.add("OpenBoxKey", EncryptHelper
				.md5Encrypt(inParam.OpenBoxKey).toLowerCase());

		List<String> orderByField = new ArrayList<String>();
		orderByField.add("TakedTime DESC");
		try {
			List<PTDeliverHistory> listLog = historyPackDAO.executeQuery(database, whereCols,
					"TakedTime DESC");
			if (listLog.size() > 0) {
				PTInBoxPackageDAO inboxPackDAO = DAOFactory
						.getPTInBoxPackageDAO();
				JDBCFieldArray whereCols1 = new JDBCFieldArray();
				whereCols1.add("BoxNo", listLog.get(0).BoxNo);

				if (inboxPackDAO.isExist(database, whereCols1) > 0)
					throw new DcdzSystemException(
							DBSErrorCode.ERR_BUSINESS_BOXHAVEUSED);

				result.PackageID = listLog.get(0).PackageID;
				result.CustomerMobile = listLog.get(0).CustomerMobile;
				result.BoxNo = listLog.get(0).BoxNo;
				result.StoredTime = listLog.get(0).StoredTime;
				result.TakedTime = listLog.get(0).TakedTime;
				result.PostmanID = listLog.get(0).PostmanID;
				result.DynamicCode = listLog.get(0).DynamicCode;
			} else {
				throw new DcdzSystemException(
						DBSErrorCode.ERR_BUSINESS_NOTEXISTDELIVERYRECORD);
			}
		} catch (DcdzSystemException e) {
			throw e;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}
