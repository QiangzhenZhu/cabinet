package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamRMOpenBox;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.RMAppealLogDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dto.RMAppealLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;

public class RMOpenBox extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		isUseDB = true;
		// 本地数据已经提交
		String result = callMethod(request, responder, timeOut);

		// ////////////////////////////////////////////////////////////////////////
		if (StringUtils.isNotEmpty(result)) {
			SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
			ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));
		}

		return result;
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		String result = "";
		InParamRMOpenBox inParam = (InParamRMOpenBox) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.AppealNo) || StringUtils.isEmpty(inParam.OpenBoxKey))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		if (StringUtils.isEmpty(inParam.RemoteFlag))
			inParam.RemoteFlag = SysDict.OPER_FLAG_LOCAL;

		// ////////////////////////////////////////////////////////////////////////
		RMAppealLogDAO appealLogDAO = DAOFactory.getRMAppealLogDAO();
		RMAppealLog appealLog = new RMAppealLog();
		try {
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("AppealNo", inParam.AppealNo);
			appealLog = appealLogDAO.find(database, whereCols);

			if (!EncryptHelper.md5Encrypt(inParam.OpenBoxKey).equalsIgnoreCase(appealLog.OpenBoxKey))
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGOPENBOXKEY);

			// ////////////////////////////////////////////////////////////////////////
			PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			whereCols1.add("BoxNo", appealLog.BoxNo);

			if (inboxPackDAO.isExist(database,whereCols1) > 0)
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_BOXHAVEUSED);

			// ////////////////////////////////////////////////////////////////////////
			JDBCFieldArray setCols2 = new JDBCFieldArray();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();

			setCols2.add("AppealStatus", SysDict.APPEAL_STATUS_OPENED);
			setCols2.add("LastModifyTime", DateUtils.nowDate());
			whereCols2.add("AppealNo", inParam.AppealNo);

			appealLogDAO.update(database, setCols2, whereCols2);

			TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			whereCols0.add("BoxNo", appealLog.BoxNo);

			TBBox box = boxDAO.find(database, whereCols0);

			// ////////////////////////////////////////////////////////////////////////
			HAL.openBox(box.BoxName);

			// 插入数据上传队列
			if (SysDict.OPER_FLAG_LOCAL.equals(inParam.RemoteFlag)) {
				result = CommonDAO.insertUploadDataQueue(database, request);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}
		return result;
	}
}
