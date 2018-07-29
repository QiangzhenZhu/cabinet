package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSCWriteServerTime;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class SCWriteServerTime extends AbstractLocalBusiness {
	public String doBusiness(InParamSCWriteServerTime inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamSCWriteServerTime inParam = (InParamSCWriteServerTime) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.MsgUid))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// ////////////////////////////////////////////////////////////////////////
		SCUploadDataQueueDAO dataQueueDAO = DAOFactory
				.getSCUploadDataQueueDAO();
		SCUploadDataQueue dataQueue = new SCUploadDataQueue();
		dataQueue.MsgUid = inParam.MsgUid;

		try {
			JDBCFieldArray findWhereCols = new JDBCFieldArray();
			findWhereCols.add("MsgUid",inParam.MsgUid);
			dataQueue = dataQueueDAO.find(database, findWhereCols);

			// 更新服务器时间
			PTDeliverHistoryDAO historyPackDAO = DAOFactory
					.getPTDeliverHistoryDAO();

			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.add("UploadFlag", SysDict.UPLOAD_FLAG_YES);
			// 服务器交易时间
			setCols.checkAdd("LastModifyTime", inParam.ServerTime); // 邮政要求交易时间和服务器同步

			whereCols.add("PackageID", dataQueue.PackageID);
			whereCols.add("StoredTime", dataQueue.StoredTime);

			historyPackDAO.update(database, setCols, whereCols);

			// 删除上传数据队列
			dataQueue.MsgUid = inParam.MsgUid;
			JDBCFieldArray deleteWhereCols = new JDBCFieldArray();
			deleteWhereCols.add("MsgUid",inParam.MsgUid);
			dataQueueDAO.delete(database, deleteWhereCols);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}