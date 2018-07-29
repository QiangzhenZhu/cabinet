package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTReadyPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTReadyPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTPushSubscribeOrder;
import com.hzdongcheng.bll.basic.dto.OutParamPTPushSubscribeOrder;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class PTPushSubscribeOrder extends AbstractLocalBusiness {
	public String doBusiness(InParamPTPushSubscribeOrder inParam)
			throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String handleBusiness(IRequest request) {
		InParamPTPushSubscribeOrder inParam = (InParamPTPushSubscribeOrder) request;
		OutParamPTPushSubscribeOrder result = new OutParamPTPushSubscribeOrder();

		// 1. 验证输入参数是否有效，如果无效返回-1。

		// 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
		Date sysDateTime = DateUtils.nowDate();

		// 解包
		List<OutParamPTPushSubscribeOrder> orderList = null;// =
															// PacketUtils.Eval(inParam.OrderList,);

		// 插入待投递列表
		PTReadyPackageDAO readyPackageDAO = DAOFactory
				.getPTReadyPackageDAO();
		PTReadyPackage readyPackage = new PTReadyPackage();
		try {
			// 箱号 0:由设备分配箱门；否则系统分配
			for (OutParamPTPushSubscribeOrder order : orderList) {
				readyPackage.PackageID = order.PID;
				readyPackage.BoxNo = order.BNO;
				readyPackage.OrderTime = order.OTM;
				readyPackage.ExpiredTime = order.ETM;
				readyPackage.CompanyID = order.CID;
				readyPackage.PostmanID = order.MID;
				readyPackage.CustomerID = order.TID;
				readyPackage.CustomerMobile = order.MOB;
				readyPackage.PosPayFlag = order.AMT > 0 ? SysDict.PAY_FLAG_YES
						: SysDict.PAY_FLAG_NO;
				readyPackage.PayAmt = order.AMT;
				readyPackage.PackageStatus = order.STS;
				readyPackage.LastModifyTime = DateUtils.nowDate();

				JDBCFieldArray whereCols = new JDBCFieldArray();
				whereCols.add("PackageID", order.PID);
				if (readyPackageDAO.isExist(database, whereCols) > 0) {
					JDBCFieldArray setCols1 = new JDBCFieldArray();
					JDBCFieldArray whereCols1 = new JDBCFieldArray();

					setCols1.add("BoxNo", readyPackage.BoxNo);
					setCols1.add("OrderTime", readyPackage.OrderTime);
					setCols1.add("ExpiredTime", readyPackage.ExpiredTime);
					setCols1.add("CompanyID", readyPackage.CompanyID);
					setCols1.add("PostmanID", readyPackage.PostmanID);
					setCols1.add("CustomerID", readyPackage.CustomerID);
					setCols1.add("CustomerMobile", readyPackage.CustomerMobile);
					setCols1.add("PosPayFlag", readyPackage.PosPayFlag);
					setCols1.add("PayAmt", readyPackage.PayAmt);
					setCols1.add("PackageStatus", readyPackage.PackageStatus);
					setCols1.add("LastModifyTime", readyPackage.LastModifyTime);
					setCols1.add("Remark", readyPackage.Remark);

					whereCols1.add("PackageID", readyPackage.PackageID);

					readyPackageDAO.update(database, setCols1, whereCols1);
				} else {
					readyPackageDAO.insert(database, readyPackage);
				}
			}

			// 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
			OPOperatorLog log = new OPOperatorLog();
			log.OperID = inParam.OperID;
			log.FunctionID = inParam.FunctionID;
			log.OccurTime = sysDateTime;
			log.Remark = inParam.RemoteFlag;

			CommonDAO.addOperatorLog(database, log);
		} catch (SQLException e) {

		} catch (DcdzSystemException e) {
			e.printStackTrace();
		}

		return "";
	}
}
