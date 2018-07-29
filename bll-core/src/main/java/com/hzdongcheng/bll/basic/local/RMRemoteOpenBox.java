package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.RMAppealLogDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.RMAppealLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamRMOpenBox;
import com.hzdongcheng.bll.basic.dto.InParamRMRemoteOpenBox;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class RMRemoteOpenBox extends AbstractLocalBusiness {
	public String doBusiness(InParamRMRemoteOpenBox inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamRMRemoteOpenBox inParam = (InParamRMRemoteOpenBox) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.AppealNo)
				|| StringUtils.isEmpty(inParam.OpenBoxKey)
				|| StringUtils.isEmpty(inParam.TerminalNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// ////////////////////////////////////////////////////////////////////////
		RMAppealLogDAO appealLogDAO = DAOFactory.getRMAppealLogDAO();
		RMAppealLog appealLog = new RMAppealLog();

		try {
			JDBCFieldArray findWhereCols = new JDBCFieldArray();
			findWhereCols.add("AppealNo",inParam.AppealNo);
			appealLog = appealLogDAO.find(database, findWhereCols);

			if (!EncryptHelper.md5Encrypt(inParam.OpenBoxKey).toLowerCase().equals(appealLog.OpenBoxKey))
				throw new DcdzSystemException(
						DBSErrorCode.ERR_BUSINESS_WRONGOPENBOXKEY);

			// ////////////////////////////////////////////////////////////////////////
			PTInBoxPackageDAO inboxPackDAO = DAOFactory
					.getPTInBoxPackageDAO();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			whereCols1.add("BoxNo", appealLog.BoxNo);

			if (inboxPackDAO.isExist(database, whereCols1) > 0)
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_BOXHAVEUSED);

			// ////////////////////////////////////////////////////////////////////////
			Date nowDateTime = DateUtils.nowDate();
			if (DateUtils.subtractMinute(-5, nowDateTime).getTime() > appealLog.LastModifyTime
					.getTime())
				throw new DcdzSystemException(
						DBSErrorCode.ERR_BUSINESS_OPENBOXTIMEOUT);

			// ////////////////////////////////////////////////////////////////////////
			JDBCFieldArray setCols2 = new JDBCFieldArray();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();

			setCols2.add("AppealStatus", SysDict.APPEAL_STATUS_OPENED);
			setCols2.add("LastModifyTime", nowDateTime);
			whereCols2.add("AppealNo", inParam.AppealNo);

			appealLogDAO.update(database, setCols2, whereCols2);

			TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			whereCols0.add("BoxNo", appealLog.BoxNo);

			TBBox box = boxDAO.find(database, whereCols0);

			// ////////////////////////////////////////////////////////////////////////
			HAL.openBox(box.BoxName);

			// /////////////////////////////////////////////////////////////////////////
			InParamRMOpenBox inParamOpenBox = new InParamRMOpenBox();
			inParamOpenBox.AppealNo = inParam.AppealNo;
			inParamOpenBox.OpenBoxKey = inParam.OpenBoxKey;
			inParamOpenBox.TerminalNo = inParam.TerminalNo;
			inParamOpenBox.RemoteFlag = inParam.RemoteFlag;

			result = CommonDAO.insertUploadDataQueue(database, inParamOpenBox);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
