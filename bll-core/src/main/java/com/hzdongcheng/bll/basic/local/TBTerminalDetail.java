package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalDetail;
import com.hzdongcheng.bll.basic.dto.OutParamTBTerminalDetail;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.List;

public class TBTerminalDetail extends AbstractLocalBusiness {
	public OutParamTBTerminalDetail doBusiness(InParamTBTerminalDetail inParam)
			throws DcdzSystemException {
		OutParamTBTerminalDetail result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamTBTerminalDetail handleBusiness(IRequest request) throws DcdzSystemException {
		//InParamTBTerminalDetail inParam = (InParamTBTerminalDetail) request;
		OutParamTBTerminalDetail result = new OutParamTBTerminalDetail();

		// 查询数据
		TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		try {
			TBTerminal terminal = terminalDAO.find(database, whereCols);
			result.TerminalNo = terminal.TerminalNo;
			result.TerminalName = terminal.TerminalName;
			result.BoxNum = terminal.BoxNum;
			result.DeskNum = terminal.DeskNum;
			result.MBDeviceNo = terminal.MBDeviceNo;
			result.OfBureau = terminal.OfBureau;
			result.OfSegment = terminal.OfSegment;
			result.Location = terminal.Location;
			result.Latlon = terminal.Latlon;
			result.CreateTime = terminal.CreateTime;
			result.LastModifyTime = terminal.LastModifyTime;
			result.TerminalStatus = terminal.TerminalStatus;
			result.RegisterFlag = terminal.RegisterFlag;
			result.Remark = terminal.Remark;
			result.SignMac = terminal.MacAddr;

			// 查询系统信息
			SMSystemInfo systemInfo = CommonDAO.findSystemInfo(database);

			result.InitPasswd = systemInfo.InitPasswd;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
