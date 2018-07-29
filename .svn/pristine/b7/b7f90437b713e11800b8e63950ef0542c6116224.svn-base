package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamAPStoreInOpenBox;
import com.hzdongcheng.bll.basic.dto.InParamAPTakeOutOpenBox;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.monitors.BoxDetectInfo;
import com.hzdongcheng.bll.monitors.TimingDetect;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
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

import java.util.Date;

public class APTakeOutOpenBox extends AbstractLocalBusiness {
    public Boolean doBusiness(InParamAPTakeOutOpenBox inParam) throws DcdzSystemException, SQLException {
        Boolean result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Boolean handleBusiness(IRequest request) throws DcdzSystemException {

        Boolean result = true;

        InParamAPTakeOutOpenBox inParam = (InParamAPTakeOutOpenBox) request;
        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.BoxNo)
                || StringUtils.isEmpty(inParam.TerminalNo)
                || StringUtils.isEmpty(inParam.TradeWaterNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = new PTInBoxPackage();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("PackageID", inParam.PackageID);

        try {
            inboxPack = inboxPackDAO.find(database, whereCols);
        } catch (DcdzSystemException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKNOTEXIST);
        }
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BoxNo", inParam.BoxNo);
        if (inboxPack==null) {
            try {
                TBBox box = boxDAO.find(database, whereCols0);
                HAL.openBox(box.BoxName);
                BoxDetectInfo boxDetectInfo=new BoxDetectInfo();
                boxDetectInfo.BoxNo=box.BoxName;
                boxDetectInfo.OperID=inParam.OperID;
                boxDetectInfo.OperationType="0";
                boxDetectInfo.TradeWaterNo=inParam.TradeWaterNo;
                TimingDetect.boxInfoList.add(boxDetectInfo);
                TimingDetect.detectStart(500);
            } catch (SQLException e) {
                result = false;
            }
        }
        else {
            try {
                TBBox box = boxDAO.find(database, whereCols);
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
                if (StringUtils.isNotEmpty(inboxPack.OpenBoxKey))
                    historyPack.OpenBoxKey = inboxPack.OpenBoxKey;
                historyPack.HireAmt = inboxPack.HireAmt;
                historyPack.HireWhoPay = inboxPack.HireWhoPay;
                historyPack.PayedAmt = inboxPack.PayedAmt;
                historyPack.TakedTime = DateUtils.nowDate();
                historyPack.LeftFlag = inboxPack.LeftFlag;
                historyPack.PosPayFlag = inboxPack.PosPayFlag;
                historyPack.PackageStatus = SysDict.PACKAGE_STATUS_OUTNORMAL;
                historyPack.UploadFlag = SysDict.UPLOAD_FLAG_NO;
                historyPack.TradeWaterNo = inParam.TradeWaterNo;
                historyPack.LastModifyTime = historyPack.TakedTime; // 服务器时间
                historyPack.Remark = inboxPack.DynamicCode + "," + inboxPack.TradeWaterNo;
                historyPack.TakedWay = SysDict.TAKEOUT_WAY_APPREMOTE;
                historyPackDAO.insert(database, historyPack);
            } catch (SQLException e) {
                result=false;
            }
        }
        Date sysDateTime = DateUtils.nowDate();
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = inParam.OperID;
        log.FunctionID = inParam.FunctionID;
        log.OccurTime = sysDateTime;
        log.Remark = "[packid]=" + inboxPack.PackageID;
        try {
            CommonDAO.addOperatorLog(database, log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
