package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTExpiredPackLocalFilter;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTVerfiyUser;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PTExpiredPackLocalFilter extends AbstractLocalBusiness {
	public String doBusiness(InParamPTExpiredPackLocalFilter inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTExpiredPackLocalFilter inParam = (InParamPTExpiredPackLocalFilter) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		Map<String, String> packidDict = new HashMap<String, String>(20);

		// 查询所有在箱记录
		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		JDBCFieldArray whereColsDummy = new JDBCFieldArray();
		try {
			List<PTInBoxPackage> inboxList = inboxPackDAO.executeQuery(database,
					whereColsDummy);
			for (PTInBoxPackage obj : inboxList) {
				packidDict.put(obj.PackageID, obj.BoxNo);
			}
			if (inParam.ExpiredPackList !=null) {
				Iterator<OutParamPTExpiredPackQry> it = inParam.ExpiredPackList.iterator();
				while (it.hasNext()) {
					OutParamPTExpiredPackQry param = it.next();
					if (!packidDict.containsKey(param.PID))
						it.remove();
				}
			}
			if (inParam.verfiyUsersPackList!=null) {
				Iterator<OutParamPTVerfiyUser> ituser = inParam.verfiyUsersPackList.iterator();
				while (ituser.hasNext()) {
					OutParamPTVerfiyUser param = ituser.next();
					param.BoxNo=packidDict.get(param.PackageID);
					if (!packidDict.containsKey(param.PackageID))
						ituser.remove();
				}
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}

		return result;
	}
}
