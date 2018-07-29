package com.hzdongcheng.bll.basic.local;


import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PAControlParamDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PAControlParam;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTTakeoutTimeQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTTakeoutTimeQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class PTTakeoutTimeQry extends AbstractLocalBusiness {
	public OutParamPTTakeoutTimeQry doBusiness(InParamPTTakeoutTimeQry inParam)
			throws DcdzSystemException {
		OutParamPTTakeoutTimeQry result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamPTTakeoutTimeQry handleBusiness(IRequest request)
			throws DcdzSystemException {
		// InParamPTTakeoutTimeQry inParam = (InParamPTTakeoutTimeQry)request;
		OutParamPTTakeoutTimeQry result = new OutParamPTTakeoutTimeQry();

		// 1. 验证输入参数是否有效，如果无效返回-1。

		// 2. 调用CommonDAO.isOnline(操作员编号)判断操作员是否在线。
		PAControlParamDAO ctrlParamDAO = DAOFactory.getPAControlParamDAO();

		// /takeOutCtrlTime
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("CtrlTypeID", 33033);
		whereCols.add("KeyString", "takeOutCtrlTime");
		try {
			PAControlParam ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.TakeOutCtrlTime = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "takeOutCtrlWeekend");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.TakeOutCtrlWeekend = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "takeOutTime11");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.TakeOutTime11 = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "takeOutTime12");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.TakeOutTime12 = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "takeOutTime13");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.TakeOutTime13 = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "takeOutTime14");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.TakeOutTime14 = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "urgent1Mobile");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.Urgent1Mobile = ctrlParam.DefaultValue;

			// /
			whereCols = new JDBCFieldArray();
			whereCols.add("CtrlTypeID", 33033);
			whereCols.add("KeyString", "urgent2Mobile");

			ctrlParam = ctrlParamDAO.find(database, whereCols);
			result.Urgent2Mobile = ctrlParam.DefaultValue;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
