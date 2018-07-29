package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSCModUploadDataQueue;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class SCModUploadDataQueue extends AbstractLocalBusiness {
	public String doBusiness(InParamSCModUploadDataQueue inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamSCModUploadDataQueue inParam = (InParamSCModUploadDataQueue) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.MsgUid))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 修改上传数据队列
		SCUploadDataQueueDAO dataQueueDAO = DAOFactory
				.getSCUploadDataQueueDAO();

		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();

		setCols.addSQL(" FailureCount = FailureCount + 1 ");
		setCols.add("LastModifyTime", DateUtils.nowDate());
		whereCols.add("MsgUid", inParam.MsgUid);

		try {
			dataQueueDAO.update(database, setCols, whereCols);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}