package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamAPStoreInOpenBox;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.bll.monitors.BoxDetectInfo;
import com.hzdongcheng.device.bean.ICallBack;
import com.hzdongcheng.bll.monitors.TimingDetect;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.Date;

public class APStoreInOpenBox extends AbstractLocalBusiness {

    public Boolean doBusiness(InParamAPStoreInOpenBox inParam) throws DcdzSystemException, SQLException {
        Boolean result;

        result = callMethod(inParam);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Boolean handleBusiness(IRequest request) throws DcdzSystemException {

        Boolean result = true;

        InParamAPStoreInOpenBox inParam = (InParamAPStoreInOpenBox) request;
        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.BoxNo)
                || StringUtils.isEmpty(inParam.TerminalNo)
                || StringUtils.isEmpty(inParam.TradeWaterNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BoxNo", inParam.BoxNo);
        try {
            final TBBox box = boxDAO.find(database, whereCols0);
            HAL.openBox(box.BoxName);
            BoxDetectInfo boxDetectInfo=new BoxDetectInfo();
            boxDetectInfo.BoxNo=box.BoxName;
            boxDetectInfo.OperID=inParam.OperID;
            boxDetectInfo.TradeWaterNo=inParam.TradeWaterNo;
            boxDetectInfo.OperationType="1";
            TimingDetect.boxInfoList.add(boxDetectInfo);
            TimingDetect.detectStart(1);
        } catch (SQLException e) {
            result=false;
        }
        Date sysDateTime = DateUtils.nowDate();
        OPOperatorLog log = new OPOperatorLog();
        log.OperID = inParam.OperID;
        log.FunctionID = inParam.FunctionID;
        log.OccurTime = sysDateTime;
        log.Remark = "";
        try {
            CommonDAO.addOperatorLog(database, log);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
