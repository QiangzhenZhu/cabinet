package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTManagerPickupPack;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.Date;

import com.hzdongcheng.bll.common.CommonDAO;

import com.hzdongcheng.persistent.dto.OPOperatorLog;

import com.hzdongcheng.persistent.db.JDBCFieldArray;


/**
 * 管理员取件
 *
 * @author Shalom
 */
public class PTManagerPickupPack extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        isUseDB = true;
        // 本地数据已经提交
        String result = callMethod(request, responder, timeOut);

        // 同步向服务端发送数据
        if (StringUtils.isNotEmpty(result)) {
            SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
            ThreadPool.QueueUserWorkItem(new SyncDataProc.SendPickupRecord(syncData));
        }

        return result;
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        String result = "";
        InParamPTManagerPickupPack inParam = (InParamPTManagerPickupPack) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PackageID) && StringUtils.isEmpty(inParam.OperID))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

        inParam.TerminalNo = DBSContext.terminalUid;
        inParam.TradeWaterNo = CommonDAO.buildTradeNo();

        Date sysDateTime = DateUtils.nowDate();

        // 验证包裹单号
        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = new PTInBoxPackage();

        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("PackageID", inParam.PackageID);

        try {
            inboxPack = inboxPackDAO.find(database, whereCols);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
        }

        // 验证订单状态
        // 判断包裹状态不????(判断锁定，不判断超时)
        if (inboxPack.PackageStatus.equals(SysDict.PACKAGE_STATUS_LOCKED)) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKAGELOCKED);
        }

        // ////开箱
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BoxNo", inboxPack.BoxNo);
        try {
            TBBox box = boxDAO.find(database, whereCols0);

            // ////////////////////////////////////////////////////////////////////////
            HAL.openBox(box.BoxName);

            // /////////////////////////////////////////////////////////////////////////
            // 判断是否需要取消加热
            //if (box.BoxType == SysDict.BOX_TYPE_FASTFOOD)
            //	dbsClient.driverBoard.setHeater(false, box.BoxNo);

            // 从在箱信息里面删除
            inboxPackDAO.delete(database, whereCols);

            PTDeliverHistoryDAO historyPackDAO = DAOFactory.getPTDeliverHistoryDAO();

            // 先删除，保证数据不重复
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("PackageID", inboxPack.PackageID);
            whereCols1.add("StoredTime", inboxPack.StoredTime);

            historyPackDAO.delete(database, whereCols1);

            // ////////////////////////////////////////////////////////////////////////
            // 插入历史记录
            PTDeliverHistory historyPack = new PTDeliverHistory();

            historyPack.TerminalNo = inParam.TerminalNo;
            historyPack.PackageID = inboxPack.PackageID;
            historyPack.BoxNo = inboxPack.BoxNo;
            historyPack.StoredTime = inboxPack.StoredTime;
            historyPack.StoredDate = inboxPack.StoredDate;
            historyPack.PostmanID = inboxPack.PostmanID;
            historyPack.CompanyID = inboxPack.CompanyID;
            historyPack.ExpiredTime = inboxPack.ExpiredTime;
            historyPack.CustomerID = inboxPack.CustomerID;
            historyPack.CustomerMobile = inboxPack.CustomerMobile;
            historyPack.CustomerName = inboxPack.CustomerName;
            historyPack.CustomerAddress = inboxPack.CustomerAddress;
            historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
            historyPack.HireAmt = inboxPack.HireAmt;
            historyPack.HireWhoPay = inboxPack.HireWhoPay;
            historyPack.PayedAmt = inboxPack.PayedAmt;
            historyPack.TakedTime = DateUtils.nowDate();
            historyPack.LeftFlag = inboxPack.LeftFlag;
            historyPack.PosPayFlag = inboxPack.PosPayFlag;
            historyPack.PackageStatus = SysDict.PACKAGE_STATUS_OUT4MANAGER;
            historyPack.LastModifyTime = sysDateTime; // 服务器时间???
            historyPack.UploadFlag = SysDict.UPLOAD_FLAG_NO;
            historyPack.TradeWaterNo = inParam.TradeWaterNo;
            historyPack.Remark = inboxPack.DynamicCode + "," + inboxPack.TradeWaterNo;

            historyPackDAO.insert(database, historyPack);

            // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
            OPOperatorLog log = new OPOperatorLog();
            log.OperID = inParam.OperID;
            log.FunctionID = inParam.FunctionID;
            log.OccurTime = sysDateTime;
            log.Remark = "[packid]=" + inboxPack.PackageID;

            CommonDAO.addOperatorLog(database, log);

            // 上传数据赋值
            inParam.BoxNo = inboxPack.BoxNo;
            inParam.CustomerMobile = inboxPack.CustomerMobile;
            inParam.CustomerName = inboxPack.CustomerName;
            inParam.StoredTime = inboxPack.StoredTime;
            inParam.OccurTime = sysDateTime;
            inParam.CompanyID = inboxPack.CompanyID;
            inParam.PostmanID = inboxPack.PostmanID;
            // inParam.DynamicCode = inboxPack.Remark;
            inParam.DynamicCode = inboxPack.DynamicCode;
            inParam.Remark = inboxPack.TradeWaterNo;// 海尔使用流水号作为投递记录主键
            inParam.CustomerID = inParam.OperID;
            inParam.LeftFlag = inboxPack.LeftFlag;

            // 插入数据上传队列
            result = CommonDAO.insertUploadDataQueue(database, request);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
        }
        return result;
    }
}
