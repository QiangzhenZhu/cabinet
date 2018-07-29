package com.hzdongcheng.bll.basic.remote;

import android.database.Cursor;
import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTReadPackageQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTReadPackageQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.ArrayList;
import java.util.List;

import com.hzdongcheng.bll.common.CommonDAO;


/**
 * <summary> 下载待投递订单列表
 **/
public class PTReadPackageQry extends AbstractRemoteBusiness {

	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		isUseDB = true;
		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTReadPackageQry inParam = (InParamPTReadPackageQry) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		if (SysDict.ORDER_DELIVERY_SOURCE_NETWORK.equals(DBSContext.ctrlParam.orderDeliverySource)) // 网络获取
		{
			netProxy.sendRequest(request, responder, timeOut, DBSContext.secretKey);
		} else {
			List<OutParamPTReadPackageQry> listOut = new ArrayList<OutParamPTReadPackageQry>();

			if (SysDict.ORDER_DELIVERY_SOURCE_LOCAL.equals(DBSContext.ctrlParam.orderDeliverySource)) // 本地获取
			{
				/*String sql = "SELECT * FROM PTReadyPackage WHERE 1=1 AND PostManID = "
						+ StringUtils.addQuote(inParam.PostmanID);
				if (StringUtils.isNotEmpty(inParam.CompanyID))
					sql = sql + " OR CompanyID = " + StringUtils.addQuote(inParam.CompanyID);
				Cursor dt = dbWrapper.executeQuery(sql);
				*/
				JDBCFieldArray whereCols = new JDBCFieldArray();
				whereCols.add("PostManID", inParam.PostmanID);
				whereCols.checkAdd("CompanyID", inParam.CompanyID);
				Cursor dt = DbUtils.executeQuery(database, "PTReadyPackage", whereCols, "OrderTime");
				try {
					while (dt.moveToNext()) {
						OutParamPTReadPackageQry outParam = new OutParamPTReadPackageQry();
						outParam.PID = dt.getString(dt.getColumnIndex("PackageID"));
						outParam.BNO = dt.getString(dt.getColumnIndex("BoxNo"));
						outParam.MOB = dt.getString(dt.getColumnIndex("CustomerMobile"));
						outParam.FlG = dt.getString(dt.getColumnIndex("PosPayFlag"));
						outParam.STS = dt.getString(dt.getColumnIndex("PackageStatus"));
					}
				} catch (SQLException e) {
					log.error("[PTReadPackageQry] ERROR-" + e.getMessage());
				}
			}

			JsonResult jsonResult = PacketUtils.buildLocalJsonResult(listOut);

			if (responder != null) {
				responder.result(jsonResult);
			}
		}

		return result;
	}
}
