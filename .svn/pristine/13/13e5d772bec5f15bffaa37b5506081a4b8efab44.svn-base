package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.RMOpenBoxLogDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.RMOpenBoxLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamRMCheckPostmanRequest;
import com.hzdongcheng.bll.basic.dto.OutParamRMCheckPostmanRequest;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RMCheckPostmanRequest extends AbstractLocalBusiness {
	public OutParamRMCheckPostmanRequest doBusiness(
			InParamRMCheckPostmanRequest inParam) throws DcdzSystemException {
		OutParamRMCheckPostmanRequest result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamRMCheckPostmanRequest handleBusiness(IRequest request)
			throws DcdzSystemException {
		InParamRMCheckPostmanRequest inParam = (InParamRMCheckPostmanRequest) request;
		OutParamRMCheckPostmanRequest result = new OutParamRMCheckPostmanRequest();

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID)
				|| StringUtils.isEmpty(inParam.AppealType)
				|| StringUtils.isEmpty(inParam.PackageID)
				|| StringUtils.isEmpty(inParam.CustomerMobile))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);
		try {
			if (SysDict.APPEAL_TYPE_DELIVER_CLOSE.equals(inParam.AppealType)) // 取消投递后误关箱门
			{
				RMOpenBoxLogDAO openBoxLogDAO = DAOFactory
						.getRMOpenBoxLogDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();
				whereCols.add("PackageID", inParam.PackageID);
				whereCols.add("CustomerMobile", inParam.CustomerMobile);

				List<String> orderByField = new ArrayList<String>();
				orderByField.add("LastModifyTime DESC");

				List<RMOpenBoxLog> listLog = openBoxLogDAO.executeQuery(database, whereCols,
						"LastModifyTime DESC");
				if (listLog.size() > 0) {
					if (!listLog.get(0).OpenBoxUser.equals(inParam.PostmanID)) {
						throw new DcdzSystemException(
								DBSErrorCode.ERR_BUSINESS_OPENBOXRECORDNOTNEWEST);
					}

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
					result.StoredTime = listLog.get(0).LastModifyTime;
					result.PostmanID = listLog.get(0).OpenBoxUser;
				} else {
					throw new DcdzSystemException(
							DBSErrorCode.ERR_BUSINESS_NOTEXISTOPENBOXRECORD);
				}
			} else if (SysDict.APPEAL_TYPE_RECOVERY_CLOSE
					.equals(inParam.AppealType) || // 回收后误关箱门
					SysDict.APPEAL_TYPE_RECOVERY_NOTOPEN
							.equals(inParam.AppealType)) {
				PTDeliverHistoryDAO historyPackDAO = DAOFactory
						.getPTDeliverHistoryDAO();

				JDBCFieldArray whereCols = new JDBCFieldArray();
				whereCols.add("PackageID", inParam.PackageID);
				whereCols.add("CustomerMobile", inParam.CustomerMobile);

				List<String> orderByField = new ArrayList<String>();
				orderByField.add("TakedTime DESC");

				List<PTDeliverHistory> listLog = historyPackDAO.executeQuery(database,
						whereCols, "TakedTime DESC");
				if (listLog.size() > 0) {
					if (!listLog.get(0).TakedPersonID.equals(inParam.PostmanID)) {
						throw new DcdzSystemException(
								DBSErrorCode.ERR_BUSINESS_RECOVERYRECORDNOTNEWEST);
					}

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
				} else {
					throw new DcdzSystemException(
							DBSErrorCode.ERR_BUSINESS_NOTEXISTRECOVERYRECORD);
				}
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
