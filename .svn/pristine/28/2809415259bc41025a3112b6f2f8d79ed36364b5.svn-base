package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBFaultStatusMod;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxInfo;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.Date;

public class TBFaultStatusMod extends AbstractLocalBusiness {
    public String doBusiness(InParamTBFaultStatusMod inParam) throws DcdzSystemException {
        String result = callMethod(inParam);

        // ////////////////////////////////////////////////////////////////////////
        // /放到队列里面统一上传
        if (StringUtils.isNotEmpty(result)) {
            SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(inParam, result);
            ThreadPool.QueueUserWorkItem(new SyncDataProc.SendSyncDataToServer(syncData));
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamTBFaultStatusMod inParam = (InParamTBFaultStatusMod) request;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.BoxNo) || StringUtils.isEmpty(inParam.FaultStatus))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        if (StringUtils.isEmpty(inParam.TerminalNo))
            inParam.TerminalNo = DBSContext.terminalUid;

        if (StringUtils.isEmpty(inParam.RemoteFlag))
            inParam.RemoteFlag = SysDict.OPER_FLAG_LOCAL;

        // 3. 调用UtilDAO.getCurrentDateTime()获得系统日期时间。
        Date sysDateTime = DateUtils.nowDate();

        // ////////////////////////////////////////////////////////////////////////
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BoxNo", inParam.BoxNo);
        try {
            TBBox box = boxDAO.find(database, whereCols0);

            int boxStatus = NumberUtils.parseInt(box.BoxStatus);

            if (SysDict.BOX_FAULTSTATUS_NO.equals(inParam.FaultStatus)) // 正常
                boxStatus = boxStatus & 1;
            else
                // 设为故障
                boxStatus = boxStatus | 2;

            // ////////////////////////////////////////////////////////////////////////
            JDBCFieldArray setCols = new JDBCFieldArray();
            JDBCFieldArray whereCols = new JDBCFieldArray();

            setCols.add("BoxStatus", boxStatus);
            whereCols.add("BoxNo", inParam.BoxNo);

            boxDAO.update(database, setCols, whereCols);

            //////////////////////////////////////////////////////////
            BoxStatus boxinfo = HAL.getBoxStatus(box.BoxNo);
            if (boxinfo != null) {
                inParam.ArticleStatus = String.valueOf(boxinfo.getGoodsStatus());
                inParam.OpenStatus = String.valueOf(boxinfo.getOpenStatus());
            }
            //////////////////////////////////////////////////////////
            PTInBoxPackageDAO inboxPackageDAO = DAOFactory.getPTInBoxPackageDAO();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("BoxNo", inParam.BoxNo);

            if (inboxPackageDAO.isExist(database, whereCols1) > 0)
                inParam.InboxStatus = "1";
            else
                inParam.InboxStatus = "0";

            // 调用CommonDAO.addOperatorLog(OperID，功能编号，系统日期时间，“”)
            OPOperatorLog log = new OPOperatorLog();
            log.OperID = inParam.OperID;
            log.FunctionID = inParam.FunctionID;
            log.OccurTime = sysDateTime;
            log.Remark = inParam.RemoteFlag + ",boxNo=" + box.BoxNo + ",oldStatus=" + box.BoxStatus + ",newStatus="
                    + boxStatus;

            CommonDAO.addOperatorLog(database, log);

            // 插入数据上传队列
            if (SysDict.TERMINAL_REGISTERFLAG_YES.equals(DBSContext.registerFlag)
                    && SysDict.OPER_FLAG_LOCAL.equals(inParam.RemoteFlag)) {
                inParam.TradeWaterNo = CommonDAO.buildTradeNo();
                inParam.BoxType = box.BoxType;
                result = CommonDAO.insertUploadDataQueue(database, request);
            }
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
        }
        return result;
    }
}
