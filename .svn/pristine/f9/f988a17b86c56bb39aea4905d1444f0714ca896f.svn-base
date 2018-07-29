package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTLockUser4WrongPwd;
import com.hzdongcheng.bll.basic.dto.InParamPTVerfiyUser;
import com.hzdongcheng.bll.basic.dto.OutParamPTVerfiyUser;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.MBLockUserTimeDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dto.MBLockUserTime;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;

/**
 * 用户取件身份认证
 **/
public class PTVerfiyUser extends AbstractRemoteBusiness {
	@Override
	public String doBusiness(IRequest request, IResponder responder, int timeOut)
			throws DcdzSystemException {
		if (timeOut == 0)
			timeOut = 20;
		isUseDB = true;
		return super.doBusiness(request, responder, timeOut);
	}

	@Override
	protected String handleBusiness(IRequest request, IResponder responder,
			int timeOut) throws DcdzSystemException {
		String result = "";
		InParamPTVerfiyUser inParam = (InParamPTVerfiyUser) request;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OpenBoxKey))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		inParam.TerminalNo = DBSContext.terminalUid;
		inParam.TradeWaterNo = CommonDAO.buildTradeNo();

		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();

		List<PTInBoxPackage> listResult = new ArrayList<PTInBoxPackage>();

		// 密码要做特殊处理
		String plainpwd = inParam.OpenBoxKey;

		if (SysDict.TAKEOUTPWD_MD5TYPE_JINGDONG.equals(DBSContext.ctrlParam.takeOutPwdMD5Type)) // 京东???
			inParam.OpenBoxKey = EncryptHelper.md5Encrypt(
					inParam.OpenBoxKey).toLowerCase();
		else
			inParam.OpenBoxKey = EncryptHelper.md5Encrypt(
					inParam.OpenBoxKey).toLowerCase();

		if (SysDict.TAKEOUTCHECK_TYPE_PACKAGEID.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols.addSQL(" AND PackageID = "
					+ StringUtils.addQuote(inParam.CustomerID) + "");
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_MOBILE.equals(DBSContext.ctrlParam.takeOutCheckType)) {

			whereCols.addSQL(" AND CustomerMobile = "
					+ StringUtils.addQuote(inParam.CustomerID) + "");
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_MOBILEAFTER4.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols
					.addSQL(" AND SUBSTR(CustomerMobile,LENGTH(CustomerMobile)-3,4) = "
							+ StringUtils.addQuote(inParam.CustomerID) + "");
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_PACKORMOBILE.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols.addSQL(" AND (PackageID = "
					+ StringUtils.addQuote(inParam.CustomerID));
			whereCols.addSQL("      OR CustomerMobile = "
					+ StringUtils.addQuote(inParam.CustomerID) + ")");

			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_CARDID.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols.addSQL(" AND CustomerID = "
					+ StringUtils.addQuote(inParam.CustomerID) + "");
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_4PACKID.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols
					.addSQL(" AND SUBSTR(PackageID,LENGTH(PackageID)-3,4) = "
							+ StringUtils.addQuote(inParam.CustomerID) + "");
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_4PACKORMOBILE.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols
					.addSQL(" AND (SUBSTR(PackageID,LENGTH(PackageID)-3,4) = "
							+ StringUtils.addQuote(inParam.CustomerID));
			whereCols.addSQL("      OR CustomerMobile = "
					+ StringUtils.addQuote(inParam.CustomerID) + ")");
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		} else if (SysDict.TAKEOUTCHECK_TYPE_PWD.equals(DBSContext.ctrlParam.takeOutCheckType)) {
			whereCols.addSQL(" AND LOWER(OpenBoxKey) = "
					+ StringUtils.addQuote(inParam.OpenBoxKey) + "");
		}
		try {
			listResult = inboxPackDAO.executeQuery(database, whereCols);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
		}
		if (listResult.size() == 0) // 用户名或密码错误
		{
			if (SysDict.TAKEOUTCHECK_MODEL_NETWORK == DBSContext.ctrlParam.takeOutCheckModel) {
				netProxy.sendRequest(request, responder, timeOut,
						DBSContext.secretKey);
			}
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
		} else {
			OutParamPTVerfiyUser outParam = new OutParamPTVerfiyUser();
			outParam.BoxNo = listResult.get(0).BoxNo;
			outParam.PackageID = listResult.get(0).PackageID;
			outParam.PosPayFlag = listResult.get(0).PosPayFlag;
			outParam.Company = listResult.get(0).CompanyID;
			outParam.CustomerTel = listResult.get(0).CustomerMobile;
			outParam.PostmanID = listResult.get(0).PostmanID;
			outParam.ExpiredAmt = 0D; // ???
			outParam.Remark = listResult.get(0).Remark;
			// 打包返回
			if (responder != null) {
				responder
						.result(PacketUtils.buildLocalJsonResult(outParam));
			}
		}
		return result;
	}

	private void LockUser4WrongPwd(JDBCFieldArray whereCols) throws DcdzSystemException {
		String result = "";

		PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
		List<PTInBoxPackage> listPack;
		try {
			listPack = inboxPackDAO.executeQuery(database,whereCols);
		} catch (SQLException e1) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e1.getMessage());
		}

		// 唯一存在才锁定（会不会引起句柄泄漏)
		if (listPack.size() == 1) {
			PTInBoxPackage inboxPack = listPack.get(0);

			InParamPTLockUser4WrongPwd inParam = new InParamPTLockUser4WrongPwd();
			inParam.PackageID = inboxPack.PackageID;

			result = DBSContext.localContext.doBusiness(inParam);

		}

		if (StringUtils.isNotEmpty(result))
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGPICKPASSWD, result);
	}

	private void CheckPackageIsTempLocked(String packageid)
			throws DcdzSystemException, SQLException {
		Date nowDate = DateUtils.nowDate();
		Date nowDateTime = DateUtils.nowDate();

		MBLockUserTimeDAO lockUserTimeDAO = DAOFactory
				.getMBLockUserTimeDAO();
		MBLockUserTime lockUserTime = new MBLockUserTime();
		//lockUserTime.PackageID = packageid;
		//lockUserTime.LockedDate = nowDate;
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("PackageID", packageid);
		whereCols.add("LockedDate", nowDate);
		if (lockUserTimeDAO.isExist(database, whereCols)>0) {
			lockUserTime = lockUserTimeDAO.find(database, whereCols);

			if (lockUserTime.LastModifyTime.compareTo(DateUtils.subtractMinute(
					1, nowDateTime)) > 0 && lockUserTime.LockTimeRange == 1) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_LOCK1MIN);
			} else if (lockUserTime.LastModifyTime.compareTo(DateUtils
					.subtractMinute(5, nowDateTime)) > 0
					&& lockUserTime.LockTimeRange == 5) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_LOCK5MIN);
			} else if (lockUserTime.LastModifyTime.compareTo(DateUtils
					.subtractMinute(30, nowDateTime)) > 0
					&& lockUserTime.LockTimeRange == 30) {
				throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_LOCK30MIN);
			}
		}

	}
}
