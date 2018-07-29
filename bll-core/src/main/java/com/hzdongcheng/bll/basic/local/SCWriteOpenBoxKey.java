package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSCWriteOpenBoxKey;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class SCWriteOpenBoxKey extends AbstractLocalBusiness {
	public String doBusiness(InParamSCWriteOpenBoxKey inParam)
			throws DcdzSystemException {
		String result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		// /校正系统时钟
		if (DateUtils.isNotNull(inParam.ServerTime)) {

		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamSCWriteOpenBoxKey inParam = (InParamSCWriteOpenBoxKey) request;
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

			if (StringUtils.isNotEmpty(inParam.PackageID)
					&& !inParam.PackageID.equals(dataQueue.PackageID))
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);

			PTInBoxPackageDAO inboxPackDAO = DAOFactory
					.getPTInBoxPackageDAO();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			whereCols0.add("PackageID", dataQueue.PackageID);

			// 验证订单是否存在
			if (inboxPackDAO.isExist(database, whereCols0) > 0) {
				// 更新开箱密码信息
				JDBCFieldArray setCols = new JDBCFieldArray();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				if (StringUtils.isNotEmpty(inParam.OpenBoxKey))
					setCols.add("OpenBoxKey", inParam.OpenBoxKey);

				setCols.add("UploadFlag", SysDict.UPLOAD_FLAG_YES);

				// 服务器交易时间(进入到历史表中,可以保留数据)
				if (DateUtils.isNotNull(inParam.ServerTime)) {
					setCols.add("StoredTime", inParam.ServerTime); // 邮政要求交易时间和服务器同步
					setCols.add("StoredDate", inParam.ServerTime);
				}

				whereCols.add("PackageID", dataQueue.PackageID);

				inboxPackDAO.update(database, setCols, whereCols);
			}

			// 删除上传数据队列
			dataQueue.MsgUid = inParam.MsgUid;
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("MsgUid",inParam.MsgUid);
			dataQueueDAO.delete(database, whereCols);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
