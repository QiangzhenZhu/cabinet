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
import com.hzdongcheng.bll.basic.dto.InParamTBDeskAdd;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class TBDeskAdd extends AbstractLocalBusiness {
	public String doBusiness(InParamTBDeskAdd inParam) throws DcdzSystemException {

		return callMethod(inParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBDeskAdd inParam = (InParamTBDeskAdd) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.BoxNumStr))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		TBDeskDAO deskDAO = DAOFactory.getTBDeskDAO();
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();

		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		int deskCount = 0; // 副柜数量

		String[] boxNumStrList = inParam.BoxNumStr.split(",");
		try {
			if (boxNumStrList.length > 1) // 设备初始化
			{
				// 清空副柜信息
				deskDAO.delete(database, whereCols0);

				// 清空箱体信息
				JDBCFieldArray whereCols1 = new JDBCFieldArray();

				boxDAO.delete(database, whereCols1);
			} else {
				// 查询副柜数量
				deskCount = deskDAO.isExist(database, whereCols0);
			}

			// 循环插入副柜信息
			TBDesk desk = new TBDesk();
			for (int i = 0; i < boxNumStrList.length; i++) {
				desk.DeskNo = deskCount;
				desk.DialupNo = deskCount; // 暂时统一
				desk.BoxNum = NumberUtils.parseInt(boxNumStrList[i]);
				desk.DeskType=inParam.DeskType;
				desk.DeskHeight = NumberUtils.parseDouble(DBSContext.ctrlParam.deskDefaultHeight);
				desk.DeskWidth = 0;

				deskDAO.insert(database, desk);

				// ////////////////////////////////////////////////////////////////////////
				// ///循环插入箱体信息
				CommonDAO.buildBoxList4Desk(database,desk.DeskNo, desk.BoxNum,
						DBSContext.terminalUid);

				deskCount++;
			}

			// 更新箱门数量
			JDBCFieldArray whereCols2 = new JDBCFieldArray();
			int boxCount = boxDAO.isExist(database, whereCols2);

			TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.add("BoxNum", boxCount);
			setCols.add("DeskNum", deskCount);
			setCols.add("LastModifyTime", sysDateTime);

			whereCols.add("TerminalNo", DBSContext.terminalUid);

			terminalDAO.update(database, setCols, whereCols);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "";

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
