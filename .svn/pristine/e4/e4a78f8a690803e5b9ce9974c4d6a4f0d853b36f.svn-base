package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTDelPackage;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class PTDelPackage extends AbstractLocalBusiness {
	public String doBusiness(InParamPTDelPackage inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTDelPackage inParam = (InParamPTDelPackage) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		try {
			// 验证订单是否存在
			PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
			PTInBoxPackage inboxPack = CommonDAO.findPackageByID(database, inParam.PackageID);

			// 从在箱信息里面删除
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("PackageID", inParam.PackageID);
			inboxPackDAO.delete(database, whereCols);

			// 先删除，保证数据不重复
			PTDeliverHistoryDAO historyPackDAO = DAOFactory
					.getPTDeliverHistoryDAO();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			whereCols1.add("PackageID", inboxPack.PackageID);
			whereCols1.add("StoredTime", inboxPack.StoredTime);

			historyPackDAO.delete(database, whereCols1);

			// 插入历史记录
			PTDeliverHistory historyPack = new PTDeliverHistory();

			historyPack.TerminalNo = inParam.TerminalNo;
			historyPack.BoxNo = inboxPack.BoxNo;
			historyPack.StoredTime = inboxPack.StoredTime;
			historyPack.StoredDate = inboxPack.StoredDate;
			historyPack.PackageID = inboxPack.PackageID;
			historyPack.PostmanID = inboxPack.PostmanID;
			historyPack.CompanyID = inboxPack.CompanyID;
			historyPack.ExpiredTime = inboxPack.ExpiredTime;
			historyPack.CustomerID = inboxPack.CustomerID;
			historyPack.CustomerMobile = inboxPack.CustomerMobile;
			historyPack.CustomerName = inboxPack.CustomerName;
			historyPack.CustomerAddress = inboxPack.CustomerAddress;
			historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
			historyPack.HireAmt = inboxPack.HireAmt;
			historyPack.HireWhoPay = inboxPack.HireWhoPay;
			historyPack.PayedAmt = inboxPack.PayedAmt;
			// historyPack.TakedTime = DateUtils.NowDateTime();
			historyPack.LeftFlag = inboxPack.LeftFlag;
			historyPack.PosPayFlag = inboxPack.PosPayFlag;
			historyPack.PackageStatus = SysDict.PACKAGE_STATUS_CANCEL;
			historyPack.LastModifyTime = DateUtils.nowDate();
			historyPack.UploadFlag = SysDict.UPLOAD_FLAG_YES;
			historyPack.Remark = inboxPack.Remark + ","
					+ inboxPack.TradeWaterNo;

			historyPackDAO.insert(database, historyPack);

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag + "," + inParam.PackageID;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
