package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PAControlParamDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PAControlParam;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalParamMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;

/**
 * 修改设备参数配置
 * 1、ScreensoundFlag  语音提示开关
 * 2、RefuseCloseDoor  拒关箱门次数
 * 3、ArticleInspectFlag 物品检测开关
 * 4、DoorInspectFlag  箱门检测开关
 * 5、PowerInspectFlag 电源检测开关
 * 6、ShockInspectFlag 震动检测开关
 * 7、ExistSuperLargeBox 是否存在超大箱
 * 8、ExistFreshBox  是否存在生鲜箱
 */
public class TBTerminalParamMod extends AbstractLocalBusiness {
	public String doBusiness(InParamTBTerminalParamMod inParam)
			throws DcdzSystemException {
		String result = callMethod(inParam);

		// /修改缓存中的值,通知设备层开始监测 (???)
		DBSContext.modifyMonitorFlag(inParam);
		// dbsClient.ReLoadSysInfo();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) throws DcdzSystemException {
		InParamTBTerminalParamMod inParam = (InParamTBTerminalParamMod) request;
		String result = "";

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID)
				|| StringUtils.isEmpty(inParam.ScreensoundFlag)
				|| StringUtils.isEmpty(inParam.ArticleInspectFlag)
				|| StringUtils.isEmpty(inParam.DoorInspectFlag)
				|| StringUtils.isEmpty(inParam.PowerInspectFlag)
				|| StringUtils.isEmpty(inParam.ShockInspectFlag))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// /改控制参数信息
		PAControlParamDAO ctrlParamDAO = DAOFactory.getPAControlParamDAO();

		try {
			// /ScreensoundFlag
			JDBCFieldArray setCols0 = new JDBCFieldArray();
			JDBCFieldArray whereCols0 = new JDBCFieldArray();
			setCols0.add("DefaultValue", inParam.ScreensoundFlag);
			whereCols0.add("CtrlTypeID", 11031);
			whereCols0.add("KeyString", "screensoundFlag");

			ctrlParamDAO.update(database,setCols0, whereCols0);

			// /ArticleInspectFlag
			JDBCFieldArray setCols1 = new JDBCFieldArray();
			JDBCFieldArray whereCols1 = new JDBCFieldArray();
			setCols1.add("DefaultValue", inParam.ArticleInspectFlag);
			whereCols1.add("CtrlTypeID", 21001);
			whereCols1.add("KeyString", "articleInspectFlag");

			ctrlParamDAO.update(database,setCols1, whereCols1);

			// /doorInspectFlag
			JDBCFieldArray setCols2 = new JDBCFieldArray();
			JDBCFieldArray whereCols2 = new JDBCFieldArray();
			setCols2.add("DefaultValue", inParam.DoorInspectFlag);
			whereCols2.add("CtrlTypeID", 21001);
			whereCols2.add("KeyString", "doorInspectFlag");

			ctrlParamDAO.update(database,setCols2, whereCols2);

			// /powerInspectFlag
			JDBCFieldArray setCols3 = new JDBCFieldArray();
			JDBCFieldArray whereCols3 = new JDBCFieldArray();
			setCols3.add("DefaultValue", inParam.PowerInspectFlag);
			whereCols3.add("CtrlTypeID", 21001);
			whereCols3.add("KeyString", "powerInspectFlag");

			ctrlParamDAO.update(database,setCols3, whereCols3);

			// /shockInspectFlag
			JDBCFieldArray setCols4 = new JDBCFieldArray();
			JDBCFieldArray whereCols4 = new JDBCFieldArray();
			setCols4.add("DefaultValue", inParam.ShockInspectFlag);
			whereCols4.add("CtrlTypeID", 21001);
			whereCols4.add("KeyString", "shockInspectFlag");

			ctrlParamDAO.update(database,setCols4, whereCols4);

			// /refuseCloseDoor
			JDBCFieldArray setCols5 = new JDBCFieldArray();
			JDBCFieldArray whereCols5 = new JDBCFieldArray();
			setCols5.add("DefaultValue",
					String.valueOf(inParam.RefuseCloseDoor));
			whereCols5.add("CtrlTypeID", 33031);
			whereCols5.add("KeyString", "refuseCloseDoor");

			ctrlParamDAO.update(database,setCols5, whereCols5);

			// /ExistSuperLargeBox
			JDBCFieldArray setCols6 = new JDBCFieldArray();
			JDBCFieldArray whereCols6 = new JDBCFieldArray();
			setCols6.add("DefaultValue", inParam.ExistSuperLargeBox);
			whereCols6.add("CtrlTypeID", 21011);
			whereCols6.add("KeyString", "existSuperLargeBox");

			ctrlParamDAO.update(database,setCols6, whereCols6);

			// /existSameTypeBox
			if (StringUtils.isEmpty(inParam.ExistFreshBox))
				inParam.ExistFreshBox = "0";

			JDBCFieldArray setCols7 = new JDBCFieldArray();
			JDBCFieldArray whereCols7 = new JDBCFieldArray();
			setCols7.add("DefaultValue", inParam.ExistFreshBox);
			whereCols7.add("CtrlTypeID", 21011);
			whereCols7.add("KeyString", "existSameTypeBox");

			ctrlParamDAO.update(database,setCols7, whereCols7);

			JDBCFieldArray setCols8 = new JDBCFieldArray();
			JDBCFieldArray whereCols8 = new JDBCFieldArray();
			whereCols8.add("CtrlTypeID", 21021);
			whereCols8.add("KeyString", "lampOnTime");
			if(ctrlParamDAO.isExist(database, whereCols8)>0){
				setCols8.add("DefaultValue", inParam.LampOnTime);
				ctrlParamDAO.update(database,setCols8, whereCols8);
			}else{
				PAControlParam obj = new PAControlParam();
				obj.CtrlTypeID = 21021;
				obj.KeyString = "lampOnTime";
				obj.DefaultValue = inParam.LampOnTime;
				obj.LastModifyTime = sysDateTime;
				obj.CtrlTypeName = "灯箱控制";
				obj.Remark = "灯箱亮灯时间";
				ctrlParamDAO.insert(database, obj);
			}

			JDBCFieldArray setCols9 = new JDBCFieldArray();
			JDBCFieldArray whereCols9 = new JDBCFieldArray();
			whereCols9.add("CtrlTypeID", 21021);
			whereCols9.add("KeyString", "lampOffTime");
			if(ctrlParamDAO.isExist(database, whereCols9)>0){
				setCols9.add("DefaultValue", inParam.LampOffTime);
				ctrlParamDAO.update(database,setCols9, whereCols9);
			}else{
				PAControlParam obj = new PAControlParam();
				obj.CtrlTypeID = 21021;
				obj.KeyString = "lampOffTime";
				obj.DefaultValue = inParam.LampOffTime;
				obj.LastModifyTime = sysDateTime;
				obj.CtrlTypeName = "灯箱控制";
				obj.Remark = "灯箱灭灯时间";
				ctrlParamDAO.insert(database, obj);
			}

		

			// 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = "";

			CommonDAO.addOperatorLog(database,log);
		} catch (SQLException e) {
			throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
					e.getMessage());
		}
		return result;
	}
}
