package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;


import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBDeviceSign;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.NetUtils;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.dao.TBTerminalDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.persistent.dto.TBTerminal;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.List;


/// <summary>
/// 设备签到
/// </summary>
public class MBDeviceSign extends AbstractRemoteBusiness {

    public MBDeviceSign() {
        isUseDB = true;
    }

    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut)
            throws DcdzSystemException {
        // 本地数据已经提交
        String result = callMethod(request, responder, timeOut);

        // ////////////////////////////////////////////////////////////////////
        DBSContext.registerFlag = SysDict.TERMINAL_REGISTERFLAG_YES;

        // 同步向服务端发送数据
        SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
        ThreadPool.QueueUserWorkItem(new SyncDataProc.SendDeviceSignInfo(syncData));

        return result;
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) throws DcdzSystemException {
        String result = "";
        InParamMBDeviceSign inParam = (InParamMBDeviceSign) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.TerminalNo))
            inParam.TerminalNo = DBSContext.terminalUid;

        // //查询柜体信息
        TBTerminalDAO terminalDAO = DAOFactory.getTBTerminalDAO();
        TBTerminal terminal = new TBTerminal();
        try {
            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("TerminalNo", inParam.TerminalNo);
            terminal = terminalDAO.find(database, whereCols);
            // 箱体或柜体为空的时候，禁止签到
            if (terminal.DeskNum == 0 || terminal.BoxNum == 0)
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_DESKNOTALLOWEDEMPTY);

            // 查询系统信息
            SMSystemInfo systemInfo = CommonDAO.findSystemInfo(database);

            // 查询箱体信息
            TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
            JDBCFieldArray whereColsDummy = new JDBCFieldArray();
            List<TBBox> boxList = boxDAO.executeQuery(database, whereColsDummy);

            JDBCFieldArray setCols1 = new JDBCFieldArray();
            JDBCFieldArray whereCols1 = new JDBCFieldArray();

            if (!SysDict.TERMINAL_REGISTERFLAG_YES
                    .equals(terminal.RegisterFlag))
            // 修改RegisterFlag(防止服务端更新失败后，不能进行业务)
            {
                setCols1.add("RegisterFlag", SysDict.TERMINAL_REGISTERFLAG_YES);
                setCols1.add("BoxNum", boxList.size());

                whereCols1.add("TerminalNo", inParam.TerminalNo);

                terminalDAO.update(database, setCols1, whereCols1);
            } else {
                setCols1.add("BoxNum", boxList.size());
                whereCols1.add("TerminalNo", inParam.TerminalNo);

                terminalDAO.update(database, setCols1, whereCols1);
            }

            // 重新查询
            terminal = terminalDAO.find(database, whereCols);

            terminal.BoxNum = boxList.size();

            // 赋值
            inParam.TerminalNo = terminal.TerminalNo;
            inParam.RegisterFlag = terminal.RegisterFlag; // 采用原始的注册标志
            inParam.SoftwareVersion = systemInfo.SoftwareVersion+","+DBSContext.driverVersion;
            // ?????(AppConfig读取)
            inParam.InitPasswd = systemInfo.InitPasswd;
            inParam.SignMac = terminal.MacAddr;
            //inParam.SignMac = NetUtils.getMac();
            inParam.SignIP = NetUtils.getIP();

            inParam.TerminalInfo = PacketUtils.serializeObject(terminal);
            // inParam.BoxInfo = PacketUtils.SerializeObject(boxList);
            StringBuilder sb = new StringBuilder(512);
            for (TBBox obj : boxList) {
                sb.append(String.format("%s,%s,%s,%s,%s,%s", obj.BoxNo,
                        escapeStr(obj.BoxName), obj.DeskNo, obj.DeskBoxNo,
                        obj.BoxType, obj.BoxStatus));
                sb.append("~");
            }

            inParam.BoxInfo = sb.toString();
        } catch (SQLException e) {

            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
        }

        return result;
    }

    private String escapeStr(String str) {
        if (StringUtils.isEmpty(str))
            return "";

        char[] cnames = str.toCharArray();
        for (int i = 0; i < cnames.length; i++) {
            if (cnames[i] == ',')
                cnames[i] = ' ';
            else if (cnames[i] == '~')
                cnames[i] = ' ';
        }

        return new String(cnames);
    }

}
