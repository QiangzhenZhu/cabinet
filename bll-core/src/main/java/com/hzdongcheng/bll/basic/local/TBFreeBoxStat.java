package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBFreeBoxStat;
import com.hzdongcheng.bll.basic.dto.OutParamTBFreeBoxStat;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxInfo;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox4Detect;

import java.util.List;


public class TBFreeBoxStat extends AbstractLocalBusiness {
	public OutParamTBFreeBoxStat doBusiness(InParamTBFreeBoxStat inParam)
			throws DcdzSystemException {
		OutParamTBFreeBoxStat result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamTBFreeBoxStat handleBusiness(IRequest request)
			throws DcdzSystemException {
		// InParamTBFreeBoxStat inParam = (InParamTBFreeBoxStat) request;
		OutParamTBFreeBoxStat result = new OutParamTBFreeBoxStat();

		JDBCFieldArray whereSQL = new JDBCFieldArray();
		List<TBBox4Detect> boxList = DbUtils.executeQuery(database, "V_TBBox4Detect",whereSQL, TBBox4Detect.class, "DeskNo,DeskBoxNo");
		// ///////////////////////////////////////////////////////////////////////////

		result.SuperSmallBoxNum = 0;
		result.SmallBoxNum = 0;
		result.MedialNum = 0;
		result.LargeNum = 0;
		result.MedialBNum = 0;
		result.SuperLargeNum = 0;
		result.FreshNum = 0;
		result.FastFoodNum = 0;
		result.ShowingNum = 0;
		String boxType = "";
		try {
			for(TBBox4Detect tbBox : boxList){
				boxType=tbBox.BoxType;
				if (SysDict.BOX_TYPE_FRESH.equals(boxType)
						&& SysDict.BOX_STATUS_NORMAL.equals(tbBox.BoxStatus)) {
					result.FreshNum++;
					continue;
				}

				if (SysDict.BOX_STATUS_NORMAL.equals(tbBox.BoxStatus)){//dt.getString(dt.getColumnIndex("BoxStatus"))
					if (SysDict.BOX_TYPE_SMALL.equals(boxType))
						result.SmallBoxNum++;
					else if (SysDict.BOX_TYPE_MEDIAL.equals(boxType))
						result.MedialNum++;
					else if (SysDict.BOX_TYPE_BIG.equals(boxType))
						result.LargeNum++;
					else if (SysDict.BOX_TYPE_HUGE.equals(boxType))
						result.SuperLargeNum++;
					else if (SysDict.BOX_TYPE_FASTFOOD.equals(boxType))
						result.FastFoodNum++;
					else if (SysDict.BOX_TYPE_MEDIALB.equals(boxType))
						result.MedialBNum++;
					else if (SysDict.BOX_TYPE_SUPERSMALL.equals(boxType))
						result.SuperSmallBoxNum++;
					else if (SysDict.BOX_TYPE_SHOWING.equals(boxType))
						result.ShowingNum++;
				}
			}

			// 判断是否存在超小箱体
			if (result.SuperSmallBoxNum == 0) {
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				whereCols.add("BoxType", SysDict.BOX_TYPE_SUPERSMALL);

				if (boxDAO.isExist(database, whereCols) == 0)
					result.SuperSmallBoxNum = -1;
			}

			// 判断是否存在中大箱体
			if (result.MedialBNum == 0) {
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				whereCols.add("BoxType", SysDict.BOX_TYPE_MEDIALB);

				if (boxDAO.isExist(database, whereCols) == 0)
					result.MedialBNum = -1;
			}

			// 判断是否存在超大箱体
			if (result.SuperLargeNum == 0) {
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				whereCols.add("BoxType", SysDict.BOX_TYPE_HUGE);

				if (boxDAO.isExist(database, whereCols) == 0)
					result.SuperLargeNum = -1;
			}

			// 判断是否存在生鲜箱体
			if (result.FreshNum == 0) {
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				whereCols.add("BoxType", SysDict.BOX_TYPE_FRESH);

				if (boxDAO.isExist(database, whereCols) == 0)
					result.FreshNum = -1;
			}

			// 判断是否存在快餐箱体
			if (result.FastFoodNum == 0) {
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				whereCols.add("BoxType", SysDict.BOX_TYPE_FASTFOOD);

				if (boxDAO.isExist(database, whereCols) == 0)
					result.FastFoodNum = -1;
			}

			// 判断是否存在展示箱
			if (result.ShowingNum == 0) {
				TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
				JDBCFieldArray whereCols = new JDBCFieldArray();

				whereCols.add("BoxType", SysDict.BOX_TYPE_SHOWING);

				if (boxDAO.isExist(database, whereCols) == 0)
					result.ShowingNum = -1;
			}
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
