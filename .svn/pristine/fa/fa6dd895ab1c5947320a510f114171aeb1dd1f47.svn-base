package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.RMOpenBoxLogDAO;
import com.hzdongcheng.persistent.dto.RMOpenBoxLog;
import com.hzdongcheng.persistent.sequence.SequenceGenerator;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamRMOpenBoxRecord;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class RMOpenBoxRecord extends AbstractLocalBusiness {
	public String doBusiness(InParamRMOpenBoxRecord inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamRMOpenBoxRecord inParam = (InParamRMOpenBoxRecord) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OpenBoxUser)
				|| StringUtils.isEmpty(inParam.OpenBoxType)
				|| StringUtils.isEmpty(inParam.BoxNo)
				|| StringUtils.isEmpty(inParam.PackageID)
				|| StringUtils.isEmpty(inParam.CustomerMobile))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		RMOpenBoxLogDAO openBoxLogDAO = DAOFactory.getRMOpenBoxLogDAO();
		RMOpenBoxLog openBoxLog = new RMOpenBoxLog();

		SequenceGenerator seqGen = SequenceGenerator.GetIntance();

		try {

			openBoxLog.WaterID = seqGen
					.GetNextKey(SequenceGenerator.SEQ_WATERID);

			openBoxLog.OpenBoxUser = inParam.OpenBoxUser;
			openBoxLog.OpenBoxType = inParam.OpenBoxType;
			openBoxLog.BoxNo = inParam.BoxNo;
			openBoxLog.PackageID = inParam.PackageID;
			openBoxLog.CustomerMobile = inParam.CustomerMobile;
			openBoxLog.TerminalNo = DBSContext.terminalUid;
			openBoxLog.LastModifyTime = sysDateTime;

			openBoxLogDAO.insert(database, openBoxLog);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
