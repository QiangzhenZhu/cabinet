package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Pickup;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.TBBox;

public class TBOpenBox4Pickup extends AbstractLocalBusiness {
	public String doBusiness(InParamTBOpenBox4Pickup inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBOpenBox4Pickup inParam = (InParamTBOpenBox4Pickup) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.BoxNo)
				&& StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols0 = new JDBCFieldArray();

		if (StringUtils.isNotEmpty(inParam.BoxNo))
			whereCols0.add("BoxNo", inParam.BoxNo);
		else {
			inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

			PTInBoxPackageDAO inboxPackDAO = DAOFactory
					.getPTInBoxPackageDAO();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			whereCols1.add("PackageID", inParam.PackageID);
			try {
				PTInBoxPackage inboxPack = inboxPackDAO.find(database, whereCols1);
				whereCols0.add("BoxNo", inboxPack.BoxNo);
			} catch (SQLException e) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST,
						e.getMessage());
			}
		}
		try {
			TBBox box = boxDAO.find(database, whereCols0);
			// //////////////////////////////////////////////////////////////////////
			// 判断箱体是否被锁定
			if (box.BoxStatus.equals("1") || box.BoxStatus.equals("3"))
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKAGELOCKED);

			// ////////////////////////////////////////////////////////////////////////
			HAL.openBox(box.BoxName);
			try {
				Thread.sleep(100);
				BoxStatus boxStatus = HAL.getBoxStatus(box.BoxName);
				if (boxStatus.getOpenStatus() == 0) {
					HAL.openBox(box.BoxName);
					Thread.sleep(100);
					boxStatus = HAL.getBoxStatus(box.BoxName);
					if (boxStatus.getOpenStatus() == 0) {
						throw new DcdzSystemException(DBSErrorCode.ERR_DRIVER_OPENBOXFAILD);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			result = box.BoxNo;
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DRIVER_NOFIND,
					e.getMessage());
		}

		return result;
	}
}
