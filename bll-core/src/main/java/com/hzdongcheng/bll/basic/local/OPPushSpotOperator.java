package com.hzdongcheng.bll.basic.local;


import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.OPOperatorDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperator;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamOPPushSpotOperator;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class OPPushSpotOperator extends AbstractLocalBusiness {
	public String doBusiness(InParamOPPushSpotOperator inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamOPPushSpotOperator inParam = (InParamOPPushSpotOperator) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		 if (StringUtils.isEmpty(inParam.OperID)
		 || StringUtils.isEmpty(inParam.ByOperID)
		 || StringUtils.isEmpty(inParam.UserID)
		 || StringUtils.isEmpty(inParam.OperName)
		 || StringUtils.isEmpty(inParam.OperPassword)
		 || inParam.OperType <= 0)
		 throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		 //3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		 Date sysDateTime = DateUtils.nowDate();
		 Date sysDate = DateUtils.nowDate();

		 //////////////////////////////////////////////////////////////////////////
		 OPOperatorDAO operDAO = DAOFactory.getOPOperatorDAO();

		 JDBCFieldArray whereCols0 = new JDBCFieldArray();
		 whereCols0.add("OperID", inParam.ByOperID);
		 if (operDAO.isExist(database, whereCols0)>0)
		 {
			 JDBCFieldArray setCols = new JDBCFieldArray();
			 JDBCFieldArray whereCols = new JDBCFieldArray();

			 setCols.add("OperName", inParam.OperName);
			 setCols.add("UserID", inParam.UserID);
			 setCols.add("OperPassword", inParam.OperPassword);
			 setCols.add("OperType", inParam.OperType);
			 setCols.add("DepartmentID", inParam.DepartmentID);
			 setCols.add("OfficeTel", inParam.OfficeTel);
			 setCols.add("Mobile", inParam.Mobile);
			 setCols.add("Email", inParam.Email);
			 setCols.add("OperStatus", inParam.OperStatus);
			 setCols.add("UpperUserID", inParam.UserID.toUpperCase());

			 whereCols.add("OperID", inParam.ByOperID);

			 operDAO.update(database, setCols, whereCols);
		 }
		 else
		 {
			 OPOperator oper = new OPOperator();
			 oper.OperID = inParam.ByOperID;
			 oper.OperName = inParam.OperName;
			 oper.UserID = inParam.UserID;
			 oper.OperPassword = inParam.OperPassword;
			 oper.OperName = inParam.OperName;
			 oper.OperType = inParam.OperType;
			 oper.DepartmentID = inParam.DepartmentID;
			 oper.OfficeTel = inParam.OfficeTel;
			 oper.Mobile = inParam.Mobile;
			 oper.Email = inParam.Email;
			 oper.OperStatus = inParam.OperStatus;
			 oper.UpperUserID = inParam.UserID.toUpperCase();
			 oper.CreateDate = sysDate;
			 oper.CreateTime = sysDateTime;

			 operDAO.insert(database, oper);
		 }


		 // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
		 OPOperatorLog log = new OPOperatorLog();
		 log.OperID = inParam.OperID;
		 log.FunctionID = inParam.FunctionID;
		 log.OccurTime = sysDateTime;
		 log.Remark = inParam.ByOperID;

		CommonDAO.addOperatorLog(database, log);

		return result;
	}
}
