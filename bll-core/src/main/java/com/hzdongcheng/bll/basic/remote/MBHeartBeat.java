package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBHeartBeat;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;


/**
 * 设备心跳包检测
 *
 * @author Shalom
 */
public class MBHeartBeat extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut)
            throws DcdzSystemException {
        isUseDB = true;
        return super.doBusiness(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) throws DcdzSystemException {
        String result = "";
        InParamMBHeartBeat inParam = (InParamMBHeartBeat) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.TerminalNo))
            inParam.TerminalNo = DBSContext.terminalUid;

        // 查询柜体信息
        TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        terminal.TerminalNo = inParam.TerminalNo;
        try {
            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("TerminalNo", inParam.TerminalNo);
            terminal = terminalDAO.find(database, whereCols);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e.getMessage());
        }
        inParam.SignIP = NetUtils.getIP();
        inParam.MBDeviceNo = terminal.MBDeviceNo;
        inParam.SignMac = terminal.MacAddr;
        inParam.TradeWaterNo = CommonDAO.buildTradeNo();

        if (responder != null)
            netProxy.sendRequest(request, responder, timeOut,
                    DBSContext.secretKey);

        return result;
    }
}
