package com.hzdongcheng.bll.basic.local;

import android.database.Cursor;
import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBAutoLockRevert;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

public class TBAutoLockRevert extends AbstractLocalBusiness {
	public String doBusiness(InParamTBAutoLockRevert inParam)
			throws DcdzSystemException {
		return callMethod(inParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		Cursor result = null;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		JDBCFieldArray whereSQL = new JDBCFieldArray();
		whereSQL.checkAdd("OccurDate", "<=",
				DateUtils.addDay(DateUtils.nowDate(), -3));
		whereSQL.checkAdd("OccurDate", ">=",
				DateUtils.addDay(DateUtils.nowDate(), -7));
		whereSQL.checkAdd("OperID", "autoLocker");
		whereSQL.checkAdd("FunctionID", "210210");

//		String sql = "SELECT distinct Remark FROM V_OperatorLog WHERE 1=1 "
//				+ whereSQL.PreparedWhereSQL();
//
//		result = dbWrapper.executeQuery(sql, whereSQL);
		Cursor dt = DbUtils.executeQuery(database,"V_FreeBox",whereSQL, "DeskNo,DeskBoxNo");
		if (result == null)
			return "";

		// 2. 重置锁定状态
		try {
			while (result.moveToNext()) {
				try {
					TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
					JDBCFieldArray whereCols0 = new JDBCFieldArray();
					whereCols0.add("BoxNo", result.getString(0));

					// TBBox box = boxDAO.Find(whereCols0, dbWrapper);

					// ////////////////////////////////////////////////////////////////////////
					JDBCFieldArray setCols = new JDBCFieldArray();
					JDBCFieldArray whereCols = new JDBCFieldArray();

					setCols.add("BoxStatus", "0");
					whereCols.add("BoxNo", result.getString(0));

					boxDAO.update(database, setCols, whereCols);

					// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
					OPOperatorLog log = new OPOperatorLog();
					log.OperID = "autoLocker";
					log.FunctionID = ((InParamTBAutoLockRevert) request).FunctionID;
					log.OccurTime = DateUtils.nowDate();
					log.Remark = result.getString(0) + "-0";

					CommonDAO.addOperatorLog(database, log);
				} catch (SQLException e) {

				}

			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return "";
	}
}
