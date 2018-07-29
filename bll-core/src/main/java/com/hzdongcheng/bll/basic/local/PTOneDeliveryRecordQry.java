package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PADictionaryDAO;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PADictionary;
import com.hzdongcheng.persistent.dto.PTDeliverHistory;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTOneDeliveryRecordQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTOneDeliveryRecordQry;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.List;

public class PTOneDeliveryRecordQry extends AbstractLocalBusiness {
    public OutParamPTOneDeliveryRecordQry doBusiness(
            InParamPTOneDeliveryRecordQry inParam) throws DcdzSystemException {
        OutParamPTOneDeliveryRecordQry result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OutParamPTOneDeliveryRecordQry handleBusiness(IRequest request)
            throws DcdzSystemException {
        InParamPTOneDeliveryRecordQry inParam = (InParamPTOneDeliveryRecordQry) request;
        OutParamPTOneDeliveryRecordQry result = new OutParamPTOneDeliveryRecordQry();

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PackageID))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

        // 验证订单是否存在
        // PTInBoxPackageDAO inboxPackDAO =
        // daoFactory.getPTInBoxPackageDAO();
        PTInBoxPackage inboxPack = null;

        try {
            inboxPack = CommonDAO.findPackageByID(database, inParam.PackageID);

            result.PackageID = inboxPack.PackageID;
            result.StoredTime = inboxPack.StoredTime;
            result.PackageStatus = inboxPack.PackageStatus;

        } catch (DcdzSystemException e) {

            PTDeliverHistoryDAO historyPackDAO = DAOFactory
                    .getPTDeliverHistoryDAO();
            JDBCFieldArray whereCols = new JDBCFieldArray();
            whereCols.add("PackageID", inParam.PackageID);

            try {
                List<PTDeliverHistory> list = historyPackDAO.executeQuery(database, whereCols,
                        "StoredTime DESC");

                if (list.size() > 0) {
                    result.PackageID = list.get(0).PackageID;
                    result.StoredTime = list.get(0).StoredTime;
                    result.TakedTime = list.get(0).TakedTime;
                    result.PackageStatus = list.get(0).PackageStatus;
                }

            } catch (SQLException e1) {

                e1.printStackTrace();
            }

        }

        // 查询包裹状态
        PADictionaryDAO dictionaryDAO = DAOFactory.getPADictionaryDAO();
        PADictionary dict = new PADictionary();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("DictTypeID", 33001);
        whereCols.add("DictID", result.PackageStatus);
        try {
            dict = dictionaryDAO.find(database, whereCols);

            result.PackageStatusName = dict.DictName;
        } catch (DcdzSystemException e) {

        }

        return result;
    }
}
