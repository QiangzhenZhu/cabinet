package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxTypeMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class TBBoxTypeMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBBoxTypeMod inParam) throws DcdzSystemException {
		return callMethod(inParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBBoxTypeMod inParam = (InParamTBBoxTypeMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.BoxNo)
				|| StringUtils.isEmpty(inParam.BoxType))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// ////////////////////////////////////////////////////////////////////////
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("BoxNo", inParam.BoxNo);

		try {
			TBBox box = boxDAO.find(database, whereCols0);

			// ////////////////////////////////////////////////////////////////////////
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.add("BoxType", inParam.BoxType);
			whereCols.add("BoxNo", inParam.BoxNo);

			boxDAO.update(database, setCols, whereCols);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "boxNo=" + box.BoxNo + ",oldType=" + box.BoxType
					+ ",newType=" + inParam.BoxType;

			CommonDAO.addOperatorLog(database, log);

			// 插入数据上传队列
			if (SysDict.TERMINAL_REGISTERFLAG_YES == DBSContext.registerFlag) {
				inParam.TradeWaterNo = CommonDAO.buildTradeNo();
				result = CommonDAO.insertUploadDataQueue(database, request);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
