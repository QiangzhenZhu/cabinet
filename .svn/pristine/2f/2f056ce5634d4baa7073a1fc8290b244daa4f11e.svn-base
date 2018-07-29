package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBPeripheralsMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PAControlParamDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;

import java.util.Date;

/**
 * 修改外设配置
 */
public class TBPeripheralsMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBPeripheralsMod inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBPeripheralsMod inParam = (InParamTBPeripheralsMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.DeviceBoardPort))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// /改控制参数信息
		PAControlParamDAO ctrlParamDAO = DAOFactory.getPAControlParamDAO();
		try {
			// /deviceBoardPort
			JDBCFieldArray setCols0 = new JDBCFieldArray();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			setCols0.add("DefaultValue", inParam.DeviceBoardPort);
			whereCols0.add("CtrlTypeID", 12001);
			whereCols0.add("KeyString", "deviceBoardPort");

			ctrlParamDAO.update(database, setCols0, whereCols0);

			// /cardReaderPort
			JDBCFieldArray setCols1 = new JDBCFieldArray();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			setCols1.add("DefaultValue", inParam.CardReaderPort);
			whereCols1.add("CtrlTypeID", 12002);
			whereCols1.add("KeyString", "cardReaderPort");

			ctrlParamDAO.update(database, setCols1, whereCols1);

			JDBCFieldArray setCols2 = new JDBCFieldArray();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();
			setCols2.add("DefaultValue", inParam.CardReaderNeedFlag);
			whereCols2.add("CtrlTypeID", 12002);
			whereCols2.add("KeyString", "cardReaderNeedFlag");

			ctrlParamDAO.update(database, setCols2, whereCols2);

			// /printPort
			JDBCFieldArray setCols3 = new JDBCFieldArray();
			JDBCFieldArray whereCols3 = new JDBCFieldArray();
			setCols3.add("DefaultValue", inParam.PrintPort);
			whereCols3.add("CtrlTypeID", 12003);
			whereCols3.add("KeyString", "printPort");

			ctrlParamDAO.update(database, setCols3, whereCols3);

			JDBCFieldArray setCols4 = new JDBCFieldArray();
			JDBCFieldArray whereCols4 = new JDBCFieldArray();
			setCols4.add("DefaultValue", inParam.PrintNeedFlag);
			whereCols4.add("CtrlTypeID", 12003);
			whereCols4.add("KeyString", "printNeedFlag");

			ctrlParamDAO.update(database, setCols4, whereCols4);

			// /barCodePort
			JDBCFieldArray setCols5 = new JDBCFieldArray();
			JDBCFieldArray whereCols5 = new JDBCFieldArray();
			setCols5.add("DefaultValue", inParam.BarCodePort);
			whereCols5.add("CtrlTypeID", 12004);
			whereCols5.add("KeyString", "barCodePort");

			ctrlParamDAO.update(database, setCols5, whereCols5);

			JDBCFieldArray setCols6 = new JDBCFieldArray();
			JDBCFieldArray whereCols6 = new JDBCFieldArray();
			setCols6.add("DefaultValue", inParam.BarNeedFlag);
			whereCols6.add("CtrlTypeID", 12004);
			whereCols6.add("KeyString", "barNeedFlag");

			ctrlParamDAO.update(database, setCols6, whereCols6);

			// /shortMsgPort
			JDBCFieldArray setCols7 = new JDBCFieldArray();
			JDBCFieldArray whereCols7 = new JDBCFieldArray();
			setCols7.add("DefaultValue", inParam.ShortMsgPort);
			whereCols7.add("CtrlTypeID", 12005);
			whereCols7.add("KeyString", "shortMsgPort");

			ctrlParamDAO.update(database, setCols7, whereCols7);

			JDBCFieldArray setCols8 = new JDBCFieldArray();
			JDBCFieldArray whereCols8 = new JDBCFieldArray();
			setCols8.add("DefaultValue", inParam.ShortMsgNeedFlag);
			whereCols8.add("CtrlTypeID", 12005);
			whereCols8.add("KeyString", "shortMsgNeedFlag");

			ctrlParamDAO.update(database, setCols8, whereCols8);

			// /ledScreenPort
			JDBCFieldArray setCols9 = new JDBCFieldArray();
			JDBCFieldArray whereCols9 = new JDBCFieldArray();
			setCols9.add("DefaultValue", inParam.LedScreenPort);
			whereCols9.add("CtrlTypeID", 12006);
			whereCols9.add("KeyString", "ledScreenPort");

			ctrlParamDAO.update(database, setCols9, whereCols9);

			JDBCFieldArray setCols10 = new JDBCFieldArray();
			JDBCFieldArray whereCols10 = new JDBCFieldArray();
			setCols10.add("DefaultValue", inParam.LedScreenNeedFlag);
			whereCols10.add("CtrlTypeID", 12006);
			whereCols10.add("KeyString", "ledSreenNeedFlag");

			ctrlParamDAO.update(database, setCols10, whereCols10);

			// /posPort
			JDBCFieldArray setCols11 = new JDBCFieldArray();
			JDBCFieldArray whereCols11 = new JDBCFieldArray();
			setCols11.add("DefaultValue", inParam.PosPort);
			whereCols11.add("CtrlTypeID", 12007);
			whereCols11.add("KeyString", "posPort");

			ctrlParamDAO.update(database, setCols11, whereCols11);

			JDBCFieldArray setCols12 = new JDBCFieldArray();
			JDBCFieldArray whereCols12 = new JDBCFieldArray();
			setCols12.add("DefaultValue", inParam.PosNeedFlag);
			whereCols12.add("CtrlTypeID", 12007);
			whereCols12.add("KeyString", "posNeedFlag");

			ctrlParamDAO.update(database, setCols12, whereCols12);

			// /coinPort
			JDBCFieldArray setCols13 = new JDBCFieldArray();
			JDBCFieldArray whereCols13 = new JDBCFieldArray();
			setCols13.add("DefaultValue", inParam.CoinPort);
			whereCols13.add("CtrlTypeID", 12008);
			whereCols13.add("KeyString", "coinPort");

			ctrlParamDAO.update(database, setCols13, whereCols13);

			JDBCFieldArray setCols14 = new JDBCFieldArray();
			JDBCFieldArray whereCols14 = new JDBCFieldArray();
			setCols14.add("DefaultValue", inParam.CoinNeedFlag);
			whereCols14.add("CtrlTypeID", 12008);
			whereCols14.add("KeyString", "coinNeedFlag");

			ctrlParamDAO.update(database, setCols14, whereCols14);

			// /bankCardPort
			JDBCFieldArray setCols15 = new JDBCFieldArray();
			JDBCFieldArray whereCols15 = new JDBCFieldArray();
			setCols15.add("DefaultValue", inParam.BankCardPort);
			whereCols15.add("CtrlTypeID", 12009);
			whereCols15.add("KeyString", "bankCardPort");

			ctrlParamDAO.update(database, setCols15, whereCols15);

			JDBCFieldArray setCols16 = new JDBCFieldArray();
			JDBCFieldArray whereCols16 = new JDBCFieldArray();
			setCols16.add("DefaultValue", inParam.BankCardNeedFlag);
			whereCols16.add("CtrlTypeID", 12009);
			whereCols16.add("KeyString", "bankCardNeedFlag");

			ctrlParamDAO.update(database, setCols16, whereCols16);

			// /cameraPort
			JDBCFieldArray setCols17 = new JDBCFieldArray();
			JDBCFieldArray whereCols17 = new JDBCFieldArray();
			setCols17.add("DefaultValue", inParam.CameraPort);
			whereCols17.add("CtrlTypeID", 12021);
			whereCols17.add("KeyString", "cameraPort");

			ctrlParamDAO.update(database, setCols17, whereCols17);

			JDBCFieldArray setCols18 = new JDBCFieldArray();
			JDBCFieldArray whereCols18 = new JDBCFieldArray();
			setCols18.add("DefaultValue", inParam.CameraNeedFlag);
			whereCols18.add("CtrlTypeID", 12021);
			whereCols18.add("KeyString", "cameraNeedFlag");

			ctrlParamDAO.update(database, setCols18, whereCols18);

			// /keyboardPort
			JDBCFieldArray setCols19 = new JDBCFieldArray();
			JDBCFieldArray whereCols19 = new JDBCFieldArray();
			setCols19.add("DefaultValue", inParam.KeyboardPort);
			whereCols19.add("CtrlTypeID", 12010);
			whereCols19.add("KeyString", "keyboardPort");

			ctrlParamDAO.update(database, setCols19, whereCols19);

			JDBCFieldArray setCols20 = new JDBCFieldArray();
			JDBCFieldArray whereCols20 = new JDBCFieldArray();
			setCols20.add("DefaultValue", inParam.KeyboardNeedFlag);
			whereCols20.add("CtrlTypeID", 12010);
			whereCols20.add("KeyString", "keyboardNeedFlag");

			ctrlParamDAO.update(database, setCols20, whereCols20);

			// /dvrPort
			JDBCFieldArray setCols21 = new JDBCFieldArray();
			JDBCFieldArray whereCols21 = new JDBCFieldArray();
			setCols21.add("DefaultValue", inParam.DvrPort);
			whereCols21.add("CtrlTypeID", 12013);
			whereCols21.add("KeyString", "dvrPort");

			ctrlParamDAO.update(database, setCols21, whereCols21);

			JDBCFieldArray setCols22 = new JDBCFieldArray();
			JDBCFieldArray whereCols22 = new JDBCFieldArray();
			setCols22.add("DefaultValue", inParam.DvrNeedFlag);
			whereCols22.add("CtrlTypeID", 12013);
			whereCols22.add("KeyString", "dvrNeedFlag");

			ctrlParamDAO.update(database, setCols22, whereCols22);

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
