package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Deposit;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.ArrayList;
import java.util.List;

public class TBOpenBox4Deposit extends AbstractLocalBusiness {
	public String doBusiness(InParamTBOpenBox4Deposit inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBOpenBox4Deposit inParam = (InParamTBOpenBox4Deposit) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.BoxType))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		if (StringUtils.isEmpty(inParam.BoxList))
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);

		List<String> scopelist = new ArrayList();
		String[] boxRightList = inParam.BoxList.split(",");
		try {
			// 查询业务层可用箱体(根据输入的箱类型)
			JDBCFieldArray whereSQL = new JDBCFieldArray();
			whereSQL.checkAdd("BoxType", inParam.BoxType);
			List<TBBox> boxList = DbUtils.executeQuery(database, "V_FreeBox",whereSQL, TBBox.class, "DeskNo,DeskBoxNo");

			for(TBBox tbBox : boxList){
				if (SysDict.POSTMAN_BOXRIGHT_JUDGE_NO == inParam.BoxList) {
					scopelist.add(tbBox.BoxNo);
				} else if (CommonDAO.isAvailableBox(boxRightList, tbBox.BoxNo)) {
					scopelist.add(tbBox.BoxNo);
				}
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX,
					e.getMessage());
		}
		if (scopelist.size() == 0)
			throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);

		return result;
	}
}
