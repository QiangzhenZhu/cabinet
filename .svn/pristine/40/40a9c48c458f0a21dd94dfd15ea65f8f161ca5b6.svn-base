package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTCheckWithdrawPack;
import com.hzdongcheng.bll.basic.dto.OutParamPTCheckWithdrawPack;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

public class PTCheckWithdrawPack extends AbstractLocalBusiness {
	public OutParamPTCheckWithdrawPack doBusiness(
			InParamPTCheckWithdrawPack inParam) throws DcdzSystemException {
		OutParamPTCheckWithdrawPack result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamPTCheckWithdrawPack handleBusiness(IRequest request)
			throws DcdzSystemException {
		InParamPTCheckWithdrawPack inParam = (InParamPTCheckWithdrawPack) request;
		OutParamPTCheckWithdrawPack result = new OutParamPTCheckWithdrawPack();

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PostmanID)
				|| StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

		// 验证包裹单号,不存在抛出异常
		PTInBoxPackage inboxPack = CommonDAO.findPackageByID(database, inParam.PackageID);

		// 列表里面需要验证
		if (!SysDict.RECOVERY_DELIVERY_SOURCE_NOTHING.equals(DBSContext.ctrlParam.recoverySource)) {
			boolean isExist = false;
			if (inParam.ExpiredPackList != null) {
				for (OutParamPTExpiredPackQry outParam : inParam.ExpiredPackList) {
					if (outParam.PID.equalsIgnoreCase(inParam.PackageID)) {
						isExist = true;

						break;
					}
				}
			}

			// 列表外不允许回收
			if (!isExist) {
				// (去掉，投递员随时都可以取回) at 2014/06/17
				// throw new
				// DcdzSystemException(ErrorMessage.ERR_BUSINESS_FORBIDWITHDRAW);
			}else{
				result.PackageStatus = SysDict.PACKAGE_STATUS_TIMEOUT;
			}
		}

		// 验证订单状态
		// 判断包裹状态不????(判断锁定，不判断超时)
		if (inboxPack.PackageStatus.equals(SysDict.PACKAGE_STATUS_LOCKED)) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKAGELOCKED);
		}

		// 验证是否是自己投递的否???(验证自己公司的
		if (DBSContext.ctrlParam.recoveryScope
				.equals(SysDict.RECOVERY_DELIVERY_SCOPE_PERSON)) {
			if (!inboxPack.PostmanID.equals(inParam.PostmanID))
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
		} else if (DBSContext.ctrlParam.recoveryScope
				.equals(SysDict.RECOVERY_DELIVERY_SCOPE_COMPANY)) {
			if (StringUtils.isNotEmpty(inParam.CompanyID)
					&& !inboxPack.CompanyID.equals(inParam.CompanyID)) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
			}
		}

		result.PackageID = inboxPack.PackageID;
		result.BoxNo = inboxPack.BoxNo;

		return result;
	}
}
