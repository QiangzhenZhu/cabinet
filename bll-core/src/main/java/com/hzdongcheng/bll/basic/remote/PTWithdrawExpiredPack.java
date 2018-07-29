package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTWithdrawExpiredPack;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.db.JDBCFieldArray;

/**
 * 取回逾期包裹
 * 
 * @author Shalom
 *
 */
public class PTWithdrawExpiredPack extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		isUseDB = true;
		// 本地数据已经提交
		String result = callMethod(request, responder, timeOut);

		if (StringUtils.isNotEmpty(result)) {
			SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
			ThreadPool.QueueUserWorkItem(new SyncDataProc.SendPickupRecord(syncData));
		}

		return result;
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTWithdrawExpiredPack inParam = (InParamPTWithdrawExpiredPack) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID) || StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();
		try {
			// 验证包裹单号
			PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
			PTInBoxPackage inboxPack = null;
			try {
				inboxPack = CommonDAO.findPackageByID(database, inParam.PackageID);
			} catch (DcdzSystemException e) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
			}
			// 不判断订单状态,以服务器状态为准

			// 从在箱信息里面删除
			JDBCFieldArray whereCols = new JDBCFieldArray();
			whereCols.add("PackageID", inParam.PackageID);
			inboxPackDAO.delete(database, whereCols);

			// 先删除，保证数据不重复
			PTDeliverHistoryDAO historyPackDAO = DAOFactory.getPTDeliverHistoryDAO();
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
			historyPack.DynamicCode = inParam.DynamicCode;
			historyPack.ExpiredTime = inboxPack.ExpiredTime;
			historyPack.CustomerID = inboxPack.CustomerID;
			historyPack.CustomerMobile = inboxPack.CustomerMobile;
			historyPack.CustomerName = inboxPack.CustomerName;
			historyPack.CustomerAddress = inboxPack.CustomerAddress;
			historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
			historyPack.TakedPersonID = inParam.PostmanID; // 邮政要求投递员可以取同一个公司的
			historyPack.HireAmt = inboxPack.HireAmt;
			historyPack.HireWhoPay = inboxPack.HireWhoPay;
			historyPack.PayedAmt = inboxPack.PayedAmt;
			historyPack.TakedTime = DateUtils.nowDate();
			historyPack.LeftFlag = inboxPack.LeftFlag;
			historyPack.PosPayFlag = inboxPack.PosPayFlag;
			historyPack.UploadFlag = SysDict.UPLOAD_FLAG_NO;
			historyPack.TradeWaterNo = inParam.TradeWaterNo;
			historyPack.Remark = inboxPack.DynamicCode + "," + inboxPack.TradeWaterNo;

			historyPack.LastModifyTime = DateUtils.nowDate(); // 服务器时间

			if (SysDict.PACKAGE_STATUS_LOCKED.equals(inboxPack.PackageStatus))
				historyPack.PackageStatus = SysDict.PACKAGE_STATUS_OUTEXCEPTION;
			else {
				if (StringUtils.isEmpty(inParam.PackageStatus))
					historyPack.PackageStatus = SysDict.PACKAGE_STATUS_OUTEXPIRED;
				else
					historyPack.PackageStatus = inParam.PackageStatus;
			}

			historyPackDAO.insert(database, historyPack);

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.PostmanID;
			log.FunctionID = inParam.FunctionID;
			log.OperType = NumberUtils.parseInt(SysDict.OPER_TYPE_POSTMAN);
			log.OccurTime = DateUtils.nowDate();
			log.Remark = "[packid]=" + inboxPack.PackageID;

			CommonDAO.addOperatorLog(database, log);

			// 上传数据赋值
			inParam.BoxNo = inboxPack.BoxNo;
			inParam.CustomerMobile = inboxPack.CustomerMobile;
			inParam.StoredTime = inboxPack.StoredTime;
			inParam.Remark = inboxPack.TradeWaterNo;
			inParam.OccurTime = historyPack.LastModifyTime;
			inParam.LeftFlag = inboxPack.LeftFlag;
			// inParam.CompanyID = inboxPack.CompanyID; (投递员换了快递公司如何处理???)

			// 插入数据上传队列
			result = CommonDAO.insertUploadDataQueue(database,request);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
		}
		return result;
	}
}
