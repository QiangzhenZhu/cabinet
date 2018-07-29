package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dao.TBDeskDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBDesk;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskDel;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import java.util.Date;
import com.hzdongcheng.bll.common.CommonDAO;

public class TBDeskDel extends AbstractLocalBusiness {
	public String doBusiness(InParamTBDeskDel inParam) throws DcdzSystemException {
		String result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		//DBSContext.resetDriverBoardBoxList();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBDeskDel inParam = (InParamTBDeskDel) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 输入副柜号为空，全部删除
		TBDeskDAO deskDAO = DAOFactory.getTBDeskDAO();
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();

		int deskCount = 0;
		int boxCount = 0;
		try {
			if (inParam.DeskNo < 0) // 全部清空
			{
				JDBCFieldArray whereColsDummy0 = new JDBCFieldArray();
				deskDAO.delete(database,whereColsDummy0);

				JDBCFieldArray whereColsDummy1 = new JDBCFieldArray();
				boxDAO.delete(database, whereColsDummy1);
			} else {
				JDBCFieldArray whereColsDummy2 = new JDBCFieldArray();
				deskCount = deskDAO.isExist(database, whereColsDummy2);

				// 删除副柜信息
				JDBCFieldArray whereCols0 = new JDBCFieldArray();
				whereCols0.add("DeskNo", inParam.DeskNo);

				deskDAO.delete(database,whereCols0);

				// 删除箱体信息
				JDBCFieldArray whereCols1 = new JDBCFieldArray();
				whereCols1.add("DeskNo", ">=", inParam.DeskNo);

				boxDAO.delete(database, whereCols1);

				// 重新生成副柜和箱体信息
				for (int i = (inParam.DeskNo + 1); i < deskCount; i++) {
					JDBCFieldArray whereCols2 = new JDBCFieldArray();
					whereCols2.add("DeskNo", i);

					TBDesk desk = deskDAO.find(database,whereCols2);

					// 删除副柜信息
					deskDAO.delete(database,whereCols2);

					// 加入副柜信息
					desk.DeskNo = i - 1;
					desk.DialupNo = desk.DeskNo;
					deskDAO.insert(database, desk);

					// 生成箱体信息
					CommonDAO.buildBoxList4Desk(database, desk.DeskNo, desk.BoxNum,
							DBSContext.terminalUid);
				}

				// 更新箱门数量
				JDBCFieldArray whereCols3 = new JDBCFieldArray();
				boxCount = boxDAO.isExist(database,whereCols3);

				deskCount = deskCount - 1;
			}

			TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
			JDBCFieldArray setCols4 = new JDBCFieldArray();
			JDBCFieldArray whereCols4 = new JDBCFieldArray();

			setCols4.add("BoxNum", boxCount);
			setCols4.add("DeskNum", deskCount);
			whereCols4.add("TerminalNo", DBSContext.terminalUid);

			terminalDAO.update(database, setCols4, whereCols4);

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = String.valueOf(inParam.DeskNo);

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
