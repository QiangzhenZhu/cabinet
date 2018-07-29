package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PAControlParamDAO;
import com.hzdongcheng.persistent.dao.TBBoxTypeDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxHeightSet;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

public class TBBoxHeightSet extends AbstractLocalBusiness {
	public String doBusiness(InParamTBBoxHeightSet inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBBoxHeightSet inParam = (InParamTBBoxHeightSet) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (inParam.TerminalHeight <= 0)
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		PAControlParamDAO ctrlParamDAO = DAOFactory.getPAControlParamDAO();
		JDBCFieldArray setCols = new JDBCFieldArray();
		JDBCFieldArray whereCols = new JDBCFieldArray();

		setCols.add("DefaultValue", String.valueOf(inParam.TerminalHeight));
		whereCols.add("CtrlTypeID", 21002);
		whereCols.add("KeyString", "deskDefaultHeight");

		try {
			ctrlParamDAO.update(database, setCols, whereCols);

			//
			TBBoxTypeDAO boxTypeDAO = DAOFactory.getTBBoxTypeDAO();
			JDBCFieldArray setCols1 = new JDBCFieldArray();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();

			setCols1.add("BoxHeight", inParam.SmallHeight);
			whereCols1.add("BoxType", SysDict.BOX_TYPE_SMALL);

			boxTypeDAO.update(database, setCols1, whereCols1);

			//
			JDBCFieldArray setCols2 = new JDBCFieldArray();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();

			setCols2.add("BoxHeight", inParam.MediumHeight);
			whereCols2.add("BoxType", SysDict.BOX_TYPE_MEDIAL);

			boxTypeDAO.update(database, setCols2, whereCols2);

			//
			JDBCFieldArray setCols3 = new JDBCFieldArray();
			JDBCFieldArray whereCols3 = new JDBCFieldArray();

			setCols3.add("BoxHeight", inParam.LargeHeight);
			whereCols3.add("BoxType", SysDict.BOX_TYPE_BIG);

			boxTypeDAO.update(database, setCols3, whereCols3);

			//
			JDBCFieldArray setCols4 = new JDBCFieldArray();
			JDBCFieldArray whereCols4 = new JDBCFieldArray();

			setCols4.add("BoxHeight", inParam.SuperHeight);
			whereCols4.add("BoxType", SysDict.BOX_TYPE_HUGE);

			boxTypeDAO.update(database, setCols4, whereCols4);

			//
			JDBCFieldArray setCols5 = new JDBCFieldArray();
			JDBCFieldArray whereCols5 = new JDBCFieldArray();

			setCols5.add("BoxHeight", inParam.MasterHeight);
			whereCols5.add("BoxType", SysDict.BOX_TYPE_MASTER);

			boxTypeDAO.update(database, setCols5, whereCols5);

			JDBCFieldArray setCols6 = new JDBCFieldArray();
			JDBCFieldArray whereCols6 = new JDBCFieldArray();

			setCols6.add("BoxHeight", inParam.MiniHeight);
			whereCols6.add("BoxType", SysDict.BOX_TYPE_SUPERSMALL);

			boxTypeDAO.update(database, setCols6, whereCols6);

			JDBCFieldArray setCols7 = new JDBCFieldArray();
			JDBCFieldArray whereCols7 = new JDBCFieldArray();

			setCols7.add("BoxHeight", inParam.AdvertisingHeight);
			whereCols7.add("BoxType", SysDict.BOX_TYPE_ADVI);

			boxTypeDAO.update(database, setCols7, whereCols7);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
