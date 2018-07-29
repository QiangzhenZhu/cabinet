package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBInboxStatus;
import com.hzdongcheng.bll.basic.dto.InParamMBReportBoxStatus;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备格口状态报告
 *
 * @author Shalom
 */
public class MBReportBoxStatus extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut)
            throws DcdzSystemException {
        if (timeOut == 0)
            timeOut = 20;
        isUseDB = true;
        return super.doBusiness(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) throws DcdzSystemException {
        String result = "";
        InParamMBReportBoxStatus inParam = (InParamMBReportBoxStatus) request;

        // //1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.TerminalNo))
            inParam.TerminalNo = DBSContext.terminalUid;

        // 在箱信息
        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();

        // 查询箱体信息
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereColsDummy = new JDBCFieldArray();
        List<TBBox> boxList;
        try {
            boxList = boxDAO.executeQuery(database, whereColsDummy, "DeskNo,DeskBoxNo");
        } catch (SQLException e1) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e1.getMessage());
        }

        List<InParamMBInboxStatus> listStatus = new ArrayList<InParamMBInboxStatus>();

        for (TBBox obj : boxList) {
            InParamMBInboxStatus inboxStatus = new InParamMBInboxStatus();

            PTInBoxPackage inboxPack = new PTInBoxPackage();
            JDBCFieldArray whereCols = new JDBCFieldArray();

            inboxStatus.BoxNo = obj.BoxNo;

            try {
                whereCols.add("BoxNo", obj.BoxNo);
                inboxPack = inboxPackDAO.find(database, whereCols);

                inboxStatus.BoxStatus = "1"; // 占用
                inboxStatus.PackageID = inboxPack.PackageID;
                inboxStatus.Mobile = inboxPack.CustomerMobile;
                inboxStatus.PostmanID = inboxPack.PostmanID;
                inboxStatus.CompanyID = inboxPack.CompanyID;
                inboxStatus.StoredTime = inboxPack.StoredTime;
                inboxStatus.TradeWaterNo = inboxPack.TradeWaterNo;
            } catch (SQLException e) {
                if (SysDict.BOX_STATUS_NORMAL.equals(obj.BoxStatus))
                    inboxStatus.BoxStatus = "0"; // 空闲
                else
                    inboxStatus.BoxStatus = "2"; // 故障
            }

            listStatus.add(inboxStatus);
        }

        for (InParamMBInboxStatus mbStatus : listStatus) {
            BoxStatus boxInfo = HAL.getBoxStatus(mbStatus.BoxNo);
            if (boxInfo == null) {
                continue;
            }
            mbStatus.ArticleStatus = String.valueOf(boxInfo.getGoodsStatus());// 物品状态(0无物，1有物,-1未知默认)
            mbStatus.OpenStatus = String.valueOf(boxInfo.getOpenStatus());// 箱门状态(0开，1关,-1未知默认)
        }
        inParam.BoxInfo = PacketUtils.serializeObject(listStatus);

        if (responder != null)
            netProxy.sendRequest(request, responder, timeOut,
                    DBSContext.secretKey);

        return result;
    }
}
