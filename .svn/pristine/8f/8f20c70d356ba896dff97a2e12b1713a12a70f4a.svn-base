package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTPickupPackage;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
/// <summary>
/// 用户取件(不需要支付)
/// </summary>

public class PTPickupPackage extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		isUseDB = true;
		// 本地数据已经提交
		String result = callMethod(request, responder, timeOut);

		// 同步向服务端发送数据
		if (StringUtils.isNotEmpty(result)) {
			SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
			ThreadPool.QueueUserWorkItem(new SyncDataProc.SendPickupRecord(syncData));
		}

		return result;
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTPickupPackage inParam = (InParamPTPickupPackage) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);
		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		// 验证包裹单号
		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		PTInBoxPackage inboxPack = new PTInBoxPackage();

		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("PackageID", inParam.PackageID);

		try {
			inboxPack = inboxPackDAO.find(database, whereCols);

			// 从在箱信息里面删除
			inboxPackDAO.delete(database, whereCols);

			PTDeliverHistoryDAO historyPackDAO = DAOFactory.getPTDeliverHistoryDAO();

			// 先删除，保证数据不重复
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			whereCols1.add("PackageID", inboxPack.PackageID);
			whereCols1.add("StoredTime", inboxPack.StoredTime);

			historyPackDAO.delete(database, whereCols1);

			// ////////////////////////////////////////////////////////////////////////
			// 插入历史记录
			PTDeliverHistory historyPack = new PTDeliverHistory();

			historyPack.TerminalNo = inParam.TerminalNo;
			historyPack.PackageID = inboxPack.PackageID;
			historyPack.BoxNo = inboxPack.BoxNo;
			historyPack.StoredTime = inboxPack.StoredTime;
			historyPack.StoredDate = inboxPack.StoredDate;
			historyPack.PostmanID = inboxPack.PostmanID;
			historyPack.CompanyID = inboxPack.CompanyID;
			historyPack.ExpiredTime = inboxPack.ExpiredTime;
			historyPack.CustomerID = inboxPack.CustomerID;
			historyPack.CustomerMobile = inboxPack.CustomerMobile;
			historyPack.CustomerName = inboxPack.CustomerName;
			historyPack.CustomerAddress = inboxPack.CustomerAddress;

			if (StringUtils.isNotEmpty(inboxPack.OpenBoxKey))
				historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
			else {
				if (StringUtils.isNotEmpty(inParam.OpenBoxKey))
					historyPack.OpenBoxKey = EncryptHelper.md5Encrypt(inParam.OpenBoxKey).toLowerCase();

				inParam.OpenBoxKey = "";
			}

			historyPack.HireAmt = inboxPack.HireAmt;
			historyPack.HireWhoPay = inboxPack.HireWhoPay;
			historyPack.PayedAmt = inboxPack.PayedAmt;
			historyPack.TakedTime = DateUtils.nowDate();
			historyPack.LeftFlag = inboxPack.LeftFlag;
			historyPack.PosPayFlag = inboxPack.PosPayFlag;
			historyPack.PackageStatus = SysDict.PACKAGE_STATUS_OUTNORMAL;
			historyPack.UploadFlag = SysDict.UPLOAD_FLAG_NO;
			historyPack.TradeWaterNo = inParam.TradeWaterNo;
			historyPack.LastModifyTime = historyPack.TakedTime; // 服务器时间
			historyPack.Remark = inboxPack.DynamicCode + "," + inboxPack.TradeWaterNo;

			// /////////////////////////////////////////////////
			if (StringUtils.isNotEmpty(inParam.TakedWay))
				historyPack.TakedWay = inParam.TakedWay; // 取件方式
			else
				historyPack.TakedWay = SysDict.TAKEOUT_WAY_PWD;

			historyPackDAO.insert(database, historyPack);

			// 上传数据赋值
			inParam.BoxNo = inboxPack.BoxNo;
			inParam.CustomerMobile = inboxPack.CustomerMobile;
			inParam.CustomerName = inboxPack.CustomerName;
			inParam.StoredTime = inboxPack.StoredTime;
			inParam.OccurTime = historyPack.LastModifyTime;
			inParam.CompanyID = inboxPack.CompanyID;
			inParam.PostmanID = inboxPack.PostmanID;
			inParam.Remark = inboxPack.TradeWaterNo;
			inParam.LeftFlage = inboxPack.LeftFlag;

			inParam.DynamicCode = inboxPack.DynamicCode;

			// 插入数据上传队列
			result = CommonDAO.insertUploadDataQueue(database, request);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
		}
		return result;
	}
}
