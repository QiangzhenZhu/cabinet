package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBDeskMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dao.TBDeskDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBDesk;

import java.util.Date;


public class TBDeskMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBDeskMod inParam) throws DcdzSystemException {
		return callMethod(inParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBDeskMod inParam = (InParamTBDeskMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID) || inParam.BoxNum <= 0)
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 查询副柜信息
		TBDeskDAO deskDAO = DAOFactory.getTBDeskDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("DeskNo", inParam.DeskNo);
		try {
			TBDesk desk = deskDAO.find(database, whereCols0);

			// 更新副柜信息
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.add("BoxNum", inParam.BoxNum);
			setCols.checkAdd("DialupNo", inParam.DialupNo);
			setCols.checkAdd("DeskHeight", inParam.DeskHeight);
			setCols.checkAdd("DeskWidth", inParam.DeskWidth);
			setCols.add("Remark", inParam.Remark);

			whereCols.add("DeskNo", inParam.DeskNo);

			deskDAO.update(database, setCols, whereCols);

			// 重新生成箱体信息
			String remark = "";
			if (desk.BoxNum != inParam.BoxNum) {
				remark = "DeskNo=" + inParam.DeskNo + ",oldBoxNum="
						+ desk.BoxNum + ",newBoxNum=" + inParam.BoxNum;

				// 删除箱体信息
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols5 = new JDBCFieldArray();
				whereCols5.add("DeskNo", ">=", inParam.DeskNo);

				boxDAO.delete(database, whereCols5);

				JDBCFieldArray whereColsDummy = new JDBCFieldArray();
				int deskCount = deskDAO.isExist(database, whereColsDummy);
				for (int i = inParam.DeskNo; i < deskCount; i++) {
					JDBCFieldArray whereCols6 = new JDBCFieldArray();
					whereCols6.add("DeskNo", i);

					desk = deskDAO.find(database, whereCols6);

					// 生成箱体信息
					CommonDAO.buildBoxList4Desk(database, desk.DeskNo, desk.BoxNum,
							DBSContext.terminalUid);
				}

				// 更新箱门数量
				JDBCFieldArray whereCols2 = new JDBCFieldArray();
				int boxCount = boxDAO.isExist(database, whereCols2);

				TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
				JDBCFieldArray setCols3 = new JDBCFieldArray();
				JDBCFieldArray whereCols3 = new JDBCFieldArray();

				setCols3.add("BoxNum", boxCount);
				setCols3.add("LastModifyTime", sysDateTime);
				whereCols3.add("TerminalNo", DBSContext.terminalUid);

				terminalDAO.update(database, setCols3, whereCols3);
			}

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = remark;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}