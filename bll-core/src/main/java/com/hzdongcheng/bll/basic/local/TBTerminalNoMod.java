package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalNoMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class TBTerminalNoMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBTerminalNoMod inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBTerminalNoMod inParam = (InParamTBTerminalNoMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.TerminalNo)
				|| StringUtils.isEmpty(inParam.NewTerminalNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 判断是否存在记录
		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		TBTerminal terminal = new TBTerminal();
		terminal.TerminalNo = inParam.TerminalNo;
		try {
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("TerminalNo", inParam.TerminalNo);
			terminal = terminalDAO.find(database,whereCols);

			// 修改设备号(如何通知服务端 ???)
			JDBCFieldArray setCols = new JDBCFieldArray();
			setCols.add("TerminalNo", inParam.NewTerminalNo);
			setCols.add("Remark", inParam.TerminalNo);

			terminalDAO.update(database,setCols, whereCols);

			// 修改在箱信息表中的设备号
			PTInBoxPackageDAO inboxPackDAO = DAOFactory
					.getPTInBoxPackageDAO();
			JDBCFieldArray setCols3 = new JDBCFieldArray();
			JDBCFieldArray whereCols3 = new JDBCFieldArray();

			setCols3.add("TerminalNo", inParam.NewTerminalNo);
			whereCols3.add("TerminalNo", inParam.TerminalNo);

			inboxPackDAO.update(database,setCols3, whereCols3);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "oldNO=" + terminal.TerminalNo + ",newNo="
					+ inParam.NewTerminalNo;

			CommonDAO.addOperatorLog(database,log);

			// 修改缓存中的设备号
			DBSContext.terminalUid = inParam.NewTerminalNo;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
