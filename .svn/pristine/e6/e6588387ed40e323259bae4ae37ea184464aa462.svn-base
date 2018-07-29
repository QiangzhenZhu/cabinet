package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.MBLockUserTimeDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.MBLockUserTime;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTLockUser4WrongPwd;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

public class PTLockUser4WrongPwd extends AbstractLocalBusiness {
	public String doBusiness(InParamPTLockUser4WrongPwd inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamPTLockUser4WrongPwd inParam = (InParamPTLockUser4WrongPwd) request;
		int result = 0;

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.PackageID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		Date nowDate = DateUtils.nowDate();
		Date nowDateTime = DateUtils.nowDate();

		MBLockUserTimeDAO lockUserTimeDAO = DAOFactory
				.getMBLockUserTimeDAO();
		MBLockUserTime lockUserTime = new MBLockUserTime();

		try {
			JDBCFieldArray findWhereCols = new JDBCFieldArray();
			findWhereCols.add("PackageID",inParam.PackageID);
			findWhereCols.add("LockedDate",nowDate);
			lockUserTime = lockUserTimeDAO.find(database, findWhereCols);
			JDBCFieldArray setCols = new JDBCFieldArray();
			JDBCFieldArray whereCols = new JDBCFieldArray();

			if (lockUserTime.WrongCount < 3) //
			{
				setCols.add("WrongCount", lockUserTime.WrongCount + 1);

				setCols.add("LastModifyTime", nowDateTime);
			} else if (lockUserTime.WrongCount == 3) // 锁定1分钟
			{
				if (lockUserTime.LastModifyTime.compareTo(DateUtils
						.subtractMinute(1, nowDateTime)) > 0) // 一分钟之内
				{
					result = DBSErrorCode.ERR_BUSINESS_LOCK1MIN;
					setCols.add("LockTimeRange", 1);
					setCols.add("LockStatus",
							SysDict.PACKAGE_TEMPSTATUS_LOCK_YES);
				} else {
					setCols.add("WrongCount", lockUserTime.WrongCount + 1);
					setCols.add("LockStatus",
							SysDict.PACKAGE_TEMPSTATUS_LOCK_NO);
					setCols.add("LastModifyTime", nowDateTime);
				}
			} else if (lockUserTime.WrongCount > 3
					&& lockUserTime.WrongCount <= 6) // 锁定5分钟
			{
				if (lockUserTime.LastModifyTime.compareTo(DateUtils
						.subtractMinute(5, nowDateTime)) > 0) {
					result = DBSErrorCode.ERR_BUSINESS_LOCK5MIN;
					setCols.add("LockTimeRange", 5);
					setCols.add("LockStatus",
							SysDict.PACKAGE_TEMPSTATUS_LOCK_YES);
				} else {
					setCols.add("WrongCount", lockUserTime.WrongCount + 1);
					setCols.add("LockStatus",
							SysDict.PACKAGE_TEMPSTATUS_LOCK_NO);
					setCols.add("LastModifyTime", nowDateTime);
				}
			} else // 锁定30分钟
			{
				if (lockUserTime.LastModifyTime.compareTo(DateUtils
						.subtractMinute(30, nowDateTime)) > 0) {
					result = DBSErrorCode.ERR_BUSINESS_LOCK30MIN;
					setCols.add("LockTimeRange", 30);
					setCols.add("LockStatus",
							SysDict.PACKAGE_TEMPSTATUS_LOCK_YES);
				} else {
					setCols.add("WrongCount", lockUserTime.WrongCount + 1);
					setCols.add("LockStatus",
							SysDict.PACKAGE_TEMPSTATUS_LOCK_NO);
					setCols.add("LastModifyTime", nowDateTime);
				}
			}

			whereCols.add("PackageID", inParam.PackageID);
			whereCols.add("LockedDate", nowDate);

			lockUserTimeDAO.update(database, setCols, whereCols);
		} catch (Exception e) {
			lockUserTime.LockedDate = nowDate;
			lockUserTime.PackageID = inParam.PackageID;
			lockUserTime.WrongCount = 1;
			lockUserTime.LockStatus = SysDict.PACKAGE_TEMPSTATUS_LOCK_NO;
			lockUserTime.LockTimeRange = 0;
			lockUserTime.LastModifyTime = DateUtils.nowDate();

			try {
				lockUserTimeDAO.insert(database, lockUserTime);
			} catch (Exception ex) {

			}
		}

		return String.valueOf(result);
	}
}
