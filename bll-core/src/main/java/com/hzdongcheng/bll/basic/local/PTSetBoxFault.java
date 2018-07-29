package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTSetBoxFault;
import com.hzdongcheng.bll.basic.dto.InParamTBFaultStatusMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class PTSetBoxFault extends AbstractLocalBusiness {
	public String doBusiness(InParamPTSetBoxFault inParam) throws DcdzSystemException {
		String result = callMethod(inParam);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTSetBoxFault inParam = (InParamPTSetBoxFault) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID)
				|| StringUtils.isEmpty(inParam.BoxNo))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// ////////////////////////////////////////////////////////////////////////
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();
		whereCols0.add("BoxNo", inParam.BoxNo);
		try {
			TBBox box = boxDAO.find(database, whereCols0);

			int boxStatus = NumberUtils.parseInt(box.BoxStatus);

			boxStatus = boxStatus | 2; // 2为故障状态

			// ////////////////////////////////////////////////////////////////////////
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			setCols.add("BoxStatus", String.valueOf(boxStatus));
			whereCols.add("BoxNo", inParam.BoxNo);

			boxDAO.update(database, setCols, whereCols);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.PostmanID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.OperType = NumberUtils.parseInt(SysDict.OPER_TYPE_POSTMAN);
			log.Remark = "boxNo=" + inParam.BoxNo + inParam.Remark;

			CommonDAO.addOperatorLog(database, log);

			// 插入数据上传队列
			InParamTBFaultStatusMod inParamMod = new InParamTBFaultStatusMod();
			inParamMod.OperID = inParam.PostmanID;
			inParamMod.TerminalNo = DBSContext.terminalUid;
			inParamMod.BoxNo = inParam.BoxNo;
			inParamMod.FaultStatus = SysDict.BOX_FAULTSTATUS_YES;
			inParamMod.TradeWaterNo = CommonDAO.buildTradeNo();
			inParamMod.BoxType = box.BoxType;
			inParamMod.RemoteFlag = SysDict.OPER_FLAG_LOCAL;

			CommonDAO.insertUploadDataQueue(database, inParamMod);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		// /////////////////////////////////////////////////////////
		// if(SysDict.DELIVER_FLAG_YES == inParam.DeliverFlag)
		// result = dbsClient.OpenBox(box.DeskNo, box.DeskBoxNo);

		return result;
	}
}
