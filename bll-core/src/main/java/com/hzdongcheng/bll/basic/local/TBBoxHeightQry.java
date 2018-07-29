package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PAControlParamDAO;
import com.hzdongcheng.persistent.dao.TBBoxTypeDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PAControlParam;
import com.hzdongcheng.persistent.dto.TBBoxType;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxHeightQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBBoxHeightQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;

import java.util.List;

public class TBBoxHeightQry extends AbstractLocalBusiness {
	public OutParamTBBoxHeightQry doBusiness(InParamTBBoxHeightQry inParam)
			throws DcdzSystemException {
		OutParamTBBoxHeightQry result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamTBBoxHeightQry handleBusiness(IRequest request) throws DcdzSystemException {
		//InParamTBBoxHeightQry inParam = (InParamTBBoxHeightQry) request;
		OutParamTBBoxHeightQry result = new OutParamTBBoxHeightQry();

		PAControlParamDAO ctrlParamDAO = DAOFactory.getPAControlParamDAO();
		JDBCFieldArray whereCols = new JDBCFieldArray();
		whereCols.add("CtrlTypeID", 21002);
		whereCols.add("KeyString", "deskDefaultHeight");
		try {
			PAControlParam paramObj = ctrlParamDAO.find(database, whereCols);
			result.TerminalHeight = NumberUtils.parseInt(paramObj.DefaultValue);

			//
			TBBoxTypeDAO boxTypeDAO = DAOFactory.getTBBoxTypeDAO();
			JDBCFieldArray whereColsDummy = new JDBCFieldArray();
			List<TBBoxType> list = boxTypeDAO.executeQuery(database, whereColsDummy);
			for (TBBoxType obj : list) {
				if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_SMALL))
					result.SmallHeight = (int) obj.BoxHeight;
				else if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_MEDIAL))
					result.MediumHeight = (int) obj.BoxHeight;
				else if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_BIG))
					result.LargeHeight = (int) obj.BoxHeight;
				else if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_HUGE))
					result.SuperHeight = (int) obj.BoxHeight;
				else if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_MASTER))
					result.MasterHeight = (int) obj.BoxHeight;
				else if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_SUPERSMALL))
					result.MiniHeight = (int) obj.BoxHeight;
				else if (obj.BoxType.equalsIgnoreCase(SysDict.BOX_TYPE_ADVI))
					result.AdvertisingHeight = (int) obj.BoxHeight;

			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
};
