package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTPackIsDelivery;
import com.hzdongcheng.bll.basic.dto.OutParamPTPackIsDelivery;
import com.hzdongcheng.bll.basic.dto.OutParamPTReadPackageQry;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class PTPackIsDelivery extends AbstractLocalBusiness {
	public OutParamPTPackIsDelivery doBusiness(InParamPTPackIsDelivery inParam)
			throws DcdzSystemException {
		OutParamPTPackIsDelivery result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamPTPackIsDelivery handleBusiness(IRequest request)
			throws DcdzSystemException {
		InParamPTPackIsDelivery inParam = (InParamPTPackIsDelivery) request;
		OutParamPTPackIsDelivery result = new OutParamPTPackIsDelivery();

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("PackageID", inParam.PackageID);

		// 包裹已经投递过
		try {
			if (inboxPackDAO.isExist(database, whereCols) > 0)
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKDELIVERYD);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKDELIVERYD);
		}
		// 验证是否在待投递列表中
		boolean isExist = false;
		if (inParam.ReadyPackList != null) {
			for (OutParamPTReadPackageQry outParam : inParam.ReadyPackList) {
				if (outParam.PID.toUpperCase().equals(inParam.PackageID
						.toUpperCase())) {
					if (StringUtils.isNotEmpty(outParam.STS)) {
						if (SysDict.PACKAGE_STATUS_NORMAL.equals(outParam.STS))
							isExist = true;
					} else {
						isExist = true;
					}

					result.CustomerMobile = outParam.MOB;

					break;
				}
			}
		}

		// 如果不存在订单列表中，判断订单外列表允许投递否(简单化，不和转入标志组合)
		if (!isExist
				&& !SysDict.ORDERS_NOTINLIST_YES.equals(DBSContext.ctrlParam.ordersNotInList))
			throw new DcdzSystemException(
					DBSErrorCode.ERR_BUSINESS_FORBIDNOTORDERLIST);

		// 订单需要网络验证
		if (SysDict.ORDER_NEED_NETCHCEK_YES.equals(DBSContext.ctrlParam.orderNeedNetCheck)
				&& !SysDict.INPUTMOBILE_FLAG_LOCAL.equals(inParam.InputMobileFlag))
			result.NetCheckFlag = SysDict.ORDER_NEED_NETCHCEK_YES;
		else
			result.NetCheckFlag = SysDict.ORDER_NEED_NETCHCEK_NO;

		return result;
	}
}
