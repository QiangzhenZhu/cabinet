package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBCopyBoxType;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;
import java.util.List;


public class TBCopyBoxType extends AbstractLocalBusiness {
	public String doBusiness(InParamTBCopyBoxType inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);


		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBCopyBoxType inParam = (InParamTBCopyBoxType) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID) || inParam.DeskNoSrc < 0
				|| inParam.DeskNoDest < 0)
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// ////////////////////////////////////////////////////////////////////////
		TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
		JDBCFieldArray whereCols1 = new JDBCFieldArray();
		JDBCFieldArray whereCols2 = new JDBCFieldArray();
		whereCols1.add("DeskNo", inParam.DeskNoSrc);
		whereCols2.add("DeskNo", inParam.DeskNoDest);
		try {
			int count1 = boxDAO.isExist(database, whereCols1);
			int count2 = boxDAO.isExist(database, whereCols2);

			if (count1 != count2)
				return result;

			List<TBBox> listBox = boxDAO.executeQuery(database, whereCols1);
			for (TBBox box : listBox) {
				JDBCFieldArray setCols = new JDBCFieldArray();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				setCols.add("BoxType", box.BoxType);
				whereCols.add("DeskBoxNo", box.DeskBoxNo);
				whereCols.add("DeskNo", inParam.DeskNoDest);

				boxDAO.update(database, setCols, whereCols);
			}

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "";

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
