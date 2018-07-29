package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 下载逾期包裹单列表
 **/
public class PTExpiredPackQry extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut)
			throws DcdzSystemException {
		isUseDB = true;
		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTExpiredPackQry inParam = (InParamPTExpiredPackQry) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		if (SysDict.RECOVERY_DELIVERY_SOURCE_NETWORK.equals(DBSContext.ctrlParam.recoverySource)) // 网络获取
		{
			netProxy.sendRequest(request, responder, timeOut,
					DBSContext.secretKey);
		} else if (SysDict.RECOVERY_DELIVERY_SOURCE_LOCAL.equals(DBSContext.ctrlParam.recoverySource)) {
			int days = NumberUtils
					.parseInt(DBSContext.ctrlParam.recoveryTimeout);
			Date sysDateTime = DateUtils.nowDate();

			List<OutParamPTExpiredPackQry> listOut = new ArrayList<OutParamPTExpiredPackQry>();
			PTInBoxPackageDAO inBoxPackageDAO = DAOFactory
					.getPTInBoxPackageDAO();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			if (DateUtils.isNotNull(inParam.ExpiredDate)) {
				// 存件日期比返回“逾期取回日期”早的邮件（不包含逾期取回日当天存件的邮件）
				whereCols.add("StoredTime", "<", inParam.ExpiredDate);
			} else {
				whereCols.addSQL(" AND ((2=2 ");
				// whereCols.add("ExpiredTime", "<", DateUtils.NowDateTime());
				// whereCols.add("ExpiredTime", "<>", DateTime.MinValue);
				whereCols.add("StoredTime", "<=",
						DateUtils.subtractMinute(days, sysDateTime));
				whereCols.addSQL(") OR PackageStatus IN('1','3'))"); // 1.锁定
																		// 3.超时
			}
			if (SysDict.RECOVERY_DELIVERY_SCOPE_PERSON.equals(DBSContext.ctrlParam.recoveryScope)) {
				whereCols.add("PostmanID", inParam.PostmanID);
			} else if (SysDict.RECOVERY_DELIVERY_SCOPE_COMPANY.equals(DBSContext.ctrlParam.recoveryScope)) {
				// 判断柜子本地有没有这个快递公司（不是快递员，是该快递员所属快递公司）
				whereCols.add("CompanyID", inParam.CompanyID);
			}
			whereCols.add("LeftFlag", SysDict.PACKAGE_LEFT_FLAG_N0);

			List<PTInBoxPackage> listPack;
			try {
				listPack = inBoxPackageDAO.executeQuery(database, whereCols);
			} catch (SQLException e) {
				throw new DcdzSystemException(
						DBSErrorCode.ERR_DATABASE_DATABASELAYER,
						e.getMessage());
			}
			for (PTInBoxPackage obj : listPack) {
				OutParamPTExpiredPackQry outParam = new OutParamPTExpiredPackQry();

				outParam.PID = obj.PackageID;
				outParam.MOB = obj.CustomerMobile;
				outParam.BNO = obj.BoxNo;
				outParam.STM = obj.StoredTime;
				outParam.STS = obj.PackageStatus;

				listOut.add(outParam);
			}

			JsonResult jsonResult = PacketUtils.buildLocalJsonResult(listOut);

			if (responder != null) {
				responder.result(jsonResult);
			}
		} else // 无逾期件来源,返回空列表
		{
			List<OutParamPTExpiredPackQry> listOut = new ArrayList<OutParamPTExpiredPackQry>();

			JsonResult jsonResult = PacketUtils.buildLocalJsonResult(listOut);

			if (responder != null) {
				responder.result(jsonResult);
			}
		}

		return result;
	}
}
