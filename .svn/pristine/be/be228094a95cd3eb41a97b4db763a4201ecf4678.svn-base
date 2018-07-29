package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.OPOperatorDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamOPOperLogin;
import com.hzdongcheng.bll.basic.dto.InParamSCSyncManagerLog;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import java.util.Date;
import com.hzdongcheng.bll.common.CommonDAO;

public class OPOperLogin extends AbstractLocalBusiness {
	public String doBusiness(InParamOPOperLogin inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamOPOperLogin inParam = (InParamOPOperLogin) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.OperPassword))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 2. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		String pwd = EncryptHelper.md5Encrypt(inParam.OperPassword);
		try {
			if (!pwd.equals(DBSContext.ctrlParam.spotAdminPWD)
					|| !inParam.OperID.equals(DBSContext.ctrlParam.spotAdminID)) {
				// 校验一下本地是否存在管理员记录
				OPOperatorDAO operDAO = DAOFactory.getOPOperatorDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();
				whereCols.add("UpperUserID", inParam.OperID.toUpperCase());
				// 验证UserID,而不是OperID
				whereCols.add("OperPassword", pwd);

				// 也不存在抛出异常
				if (operDAO.isExist(database, whereCols) == 0)
					throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGPWD);
			}

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "";

			CommonDAO.addOperatorLog(database, log);


			// 插入数据上传队列
			if (SysDict.TERMINAL_REGISTERFLAG_YES == DBSContext.registerFlag) {
				InParamSCSyncManagerLog inParamLog = new InParamSCSyncManagerLog();
				inParamLog.OperID = inParam.OperID;
				inParamLog.TerminalNo = DBSContext.terminalUid;
				inParamLog.FactFunctionID = inParam.FunctionID;
				inParamLog.Remark = "";
				inParamLog.OccurTime = sysDateTime;
				inParamLog.TradeWaterNo = CommonDAO.buildTradeNo();

				CommonDAO.insertUploadDataQueue(database, inParamLog);
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
