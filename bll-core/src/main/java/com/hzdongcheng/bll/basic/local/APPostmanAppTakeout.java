package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.device.HAL;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamAPPostmanAppTakeout;
import com.hzdongcheng.bll.basic.dto.InParamPTWithdrawExpiredPack;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.Date;


public class APPostmanAppTakeout extends AbstractLocalBusiness {
    private InParamPTWithdrawExpiredPack inParamPick = new InParamPTWithdrawExpiredPack();

    public String doBusiness(InParamAPPostmanAppTakeout inParam) throws DcdzSystemException, SQLException {
        String result;

        result = callMethod(inParam);

        // 同步向服务端发送数据
        if (StringUtils.isNotEmpty(result)) {
            SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(inParamPick, result);
            ThreadPool.QueueUserWorkItem(new SyncDataProc.SendPickupRecord(syncData));
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamAPPostmanAppTakeout inParam = (InParamAPPostmanAppTakeout) request;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PostmanID) || StringUtils.isEmpty(inParam.CustomerMobile)
                || StringUtils.isEmpty(inParam.PackageID) || StringUtils.isEmpty(inParam.TerminalNo)
                || StringUtils.isEmpty(inParam.TradeWaterNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        // 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        Date sysDateTime = DateUtils.nowDate();

        // 验证包裹单号
        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = new PTInBoxPackage();

        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("PackageID", inParam.PackageID);

        try {
            inboxPack = inboxPackDAO.find(database, whereCols);
        } catch (DcdzSystemException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
        }

        if (!inboxPack.CustomerMobile.equals(inParam.CustomerMobile) || !inboxPack.TradeWaterNo.equals(inParam.TradeWaterNo)
                || !inboxPack.PostmanID.equals(inParam.PostmanID))
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);

        // /////////////开箱////////////////////////////////////////////////////////
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BoxNo", inboxPack.BoxNo);
        try {
            TBBox box = boxDAO.find(database, whereCols0);

            HAL.openBox(box.BoxName);

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
            historyPack.BoxNo = inboxPack.BoxNo;
            historyPack.StoredTime = inboxPack.StoredTime;
            historyPack.StoredDate = inboxPack.StoredDate;
            historyPack.PackageID = inboxPack.PackageID;
            historyPack.PostmanID = inboxPack.PostmanID;
            historyPack.CompanyID = inboxPack.CompanyID;
            historyPack.DynamicCode = "";
            historyPack.ExpiredTime = inboxPack.ExpiredTime;
            historyPack.CustomerID = inboxPack.CustomerID;
            historyPack.CustomerMobile = inboxPack.CustomerMobile;
            historyPack.CustomerName = inboxPack.CustomerName;
            historyPack.CustomerAddress = inboxPack.CustomerAddress;
            historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
            historyPack.TakedPersonID = inParam.PostmanID; // 邮政要求投递员可以取同一个公司的
            historyPack.HireAmt = inboxPack.HireAmt;
            historyPack.HireWhoPay = inboxPack.HireWhoPay;
            historyPack.PayedAmt = inboxPack.PayedAmt;
            historyPack.TakedTime = DateUtils.nowDate();
            historyPack.LeftFlag = inboxPack.LeftFlag;
            historyPack.PosPayFlag = inboxPack.PosPayFlag;
            historyPack.UploadFlag = SysDict.UPLOAD_FLAG_NO;
            historyPack.TradeWaterNo = inParam.TradeWaterNo;
            historyPack.Remark = inboxPack.DynamicCode + "," + inboxPack.TradeWaterNo;

            historyPack.LastModifyTime = DateUtils.nowDate(); // 服务器时间
            historyPack.PackageStatus = SysDict.PACKAGE_STATUS_OUT4POSTMAN;

            // /////////////////////////////////////////////////
            historyPack.TakedWay = SysDict.TAKEOUT_WAY_APPREMOTE;

            historyPackDAO.insert(database, historyPack);

            // 上传数据赋值
            inParamPick.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);
            inParamPick.CustomerMobile = inParam.CustomerMobile;
            inParamPick.TerminalNo = DBSContext.terminalUid;
            inParamPick.TradeWaterNo = CommonDAO.buildTradeNo();
            inParamPick.BoxNo = inboxPack.BoxNo;
            inParamPick.StoredTime = inboxPack.StoredTime;
            inParamPick.OccurTime = historyPack.LastModifyTime;
            inParamPick.CompanyID = inboxPack.CompanyID;
            inParamPick.PostmanID = inboxPack.PostmanID;
            inParamPick.Remark = inboxPack.TradeWaterNo;

            inParamPick.DynamicCode = inboxPack.DynamicCode;

            // 插入数据上传队列
            result = CommonDAO.insertUploadDataQueue(database, inParamPick);

            // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
            OPOperatorLog log = new OPOperatorLog();
            log.OperID = inParam.PostmanID;
            log.FunctionID = inParam.FunctionID;
            log.OccurTime = sysDateTime;
            log.Remark = "[packid]=" + inboxPack.PackageID;

            CommonDAO.addOperatorLog(database, log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
