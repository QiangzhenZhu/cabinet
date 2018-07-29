package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dao.TBDeskDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.ControlParam;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;
import java.util.List;

public class TBTerminalMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBTerminalMod inParam) throws DcdzSystemException {
		String result;

		result = callMethod(inParam);

		// ////////////////////////////////////////////////////////////////////////
		// //加载设备信息
		// dbsClient.loadTerminalInfo();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBTerminalMod inParam = (InParamTBTerminalMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.TerminalNo))
			inParam.TerminalNo = DBSContext.terminalUid;
		else
			inParam.TerminalNo = inParam.TerminalNo.trim();

		// ////////////////////////////////////////////////////////////////////////
		// 柜号不能为空
		if (StringUtils.isEmpty(inParam.TerminalNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_TERMINALNOEMPTY);

		if (StringUtils.isEmpty(inParam.TerminalName))
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_TERMINALNAMEEMPTY);

		if (StringUtils.isEmpty(inParam.Location))
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_TERMINALLOCATIONEMPTY);

		if (StringUtils.isNotEmpty(inParam.OfBureau) && inParam.OfBureau.length() != 8)
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_TDJHFORMATWRONG);

		ControlParam ctrlParam = DBSContext.ctrlParam;
		if (ctrlParam.ofBureauEmpty == "0" && StringUtils.isEmpty(inParam.OfBureau)) // 不允许为空
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_TDJHNOTEMPTY);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 判断是否增加过副柜信息
		TBDeskDAO deskDAO = DAOFactory.getTBDeskDAO();
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereColsDummy = new JDBCFieldArray();

		try {
			int DeskNum = deskDAO.isExist(database, whereColsDummy);
			int BoxNum = boxDAO.isExist(database, whereColsDummy);

			// 副柜不能为空，请添加副柜
			if (DeskNum == 0 || BoxNum == 0)
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_DESKNOTALLOWEDEMPTY);

			// 判断是否存在记录
			String terminalNo = "";
			TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
			List<TBTerminal> listTerminal = terminalDAO.executeQuery(database, whereColsDummy);

			if (listTerminal.size() > 0) {
				// 修改记录
				terminalNo = listTerminal.get(0).TerminalNo;
				JDBCFieldArray setCols = new JDBCFieldArray();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				setCols.checkAdd("TerminalNo", inParam.TerminalNo);
				setCols.checkAdd("TerminalName", inParam.TerminalName);
				setCols.checkAdd("MainDeskAddress", inParam.MainDeskAddress);
				setCols.add("BoxNum", BoxNum);
				setCols.add("DeskNum", DeskNum);
				setCols.checkAdd("MBDeviceNo", inParam.MBDeviceNo);
				setCols.add("OfBureau", inParam.OfBureau);
				setCols.add("OfSegment", inParam.OfSegment);
				setCols.checkAdd("Location", inParam.Location);
				setCols.checkAdd("Latlon", inParam.Latlon);
				setCols.checkAdd("Remark", inParam.Remark);
				setCols.add("LastModifyTime", sysDateTime);

				if (StringUtils.isEmpty(listTerminal.get(0).MacAddr))
					setCols.add("MacAddr", NetUtils.getMac());

				// 新的柜号未注册认证
				if (!terminalNo.equals(inParam.TerminalNo)) // 号码变动重新注册
					setCols.add("RegisterFlag", SysDict.TERMINAL_REGISTERFLAG_NO);
				else if (!listTerminal.get(0).TerminalName.equals(inParam.TerminalName)) // 注册失败重新注册(用于网络重启)
					setCols.add("RegisterFlag", SysDict.TERMINAL_REGISTERFLAG_YES);

				whereCols.add("TerminalNo", listTerminal.get(0).TerminalNo);

				terminalDAO.update(database, setCols, whereCols);
			} else {
				TBTerminal terminal = new TBTerminal();

				terminal.TerminalNo = inParam.TerminalNo;
				terminal.TerminalName = inParam.TerminalName;
				terminal.MainDeskAddress = inParam.MainDeskAddress;
				terminal.BoxNum = BoxNum;
				terminal.DeskNum = DeskNum;
				terminal.MBDeviceNo = inParam.MBDeviceNo;
				terminal.OfBureau = inParam.OfBureau;
				terminal.OfSegment = inParam.OfSegment;
				terminal.Location = inParam.Location;
				terminal.Latlon = inParam.Latlon;
				terminal.Remark = inParam.Remark;
				terminal.MacAddr = NetUtils.getMac();

				terminal.CreateTime = sysDateTime;
				terminal.LastModifyTime = sysDateTime;
				terminal.TerminalStatus = SysDict.TERMINAL_STATUS_NORMAL;
				terminal.RegisterFlag = SysDict.TERMINAL_REGISTERFLAG_NO; // 未注册

				terminalDAO.insert(database, terminal);

				terminalNo = inParam.TerminalNo;
			}

			// ///////////////////////////
			if (StringUtils.isNotEmpty(inParam.InitPasswd)) {
				SMSystemInfoDAO systemDAO = DAOFactory.getSMSystemInfoDAO();
				SMSystemInfo systemInfo = CommonDAO.findSystemInfo(database);

				JDBCFieldArray setCols2 = new JDBCFieldArray();
				JDBCFieldArray whereCols2 = new JDBCFieldArray();

				setCols2.add("InitPasswd", inParam.InitPasswd);
				whereCols2.add("SystemID", systemInfo.SystemID);

				systemDAO.update(database, setCols2, whereCols2);
			}

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.TerminalNo;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}
		return result;
	}
}
