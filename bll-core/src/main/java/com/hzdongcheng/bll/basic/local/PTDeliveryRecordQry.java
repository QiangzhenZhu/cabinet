package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryRecordQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTDeliveryRecordQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.List;

public class PTDeliveryRecordQry extends AbstractLocalBusiness {
    public List<OutParamPTDeliveryRecordQry> doBusiness(InParamPTDeliveryRecordQry inParam)
            throws DcdzSystemException {
        List<OutParamPTDeliveryRecordQry> result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<OutParamPTDeliveryRecordQry> handleBusiness(IRequest request) throws DcdzSystemException {
        InParamPTDeliveryRecordQry inParam = (InParamPTDeliveryRecordQry) request;
        List<OutParamPTDeliveryRecordQry> result = null;

        String viewName = "";
        if (SysDict.INBOX_FLAG_YES.equals(inParam.InboxFlag)) {
            viewName = "V_InboxPackage";
        } else {
            if (StringUtils.isEmpty(inParam.PackageStatus))
                viewName = "V_AllOrder";
            else if (SysDict.PACKAGE_STATUS_NORMAL.equals(inParam.PackageStatus)
                    || SysDict.PACKAGE_STATUS_LOCKED.equals(inParam.PackageStatus)
                    || SysDict.PACKAGE_STATUS_TIMEOUT.equals(inParam.PackageStatus))
                viewName = "V_InboxPackage";
            else
                viewName = "V_HistoryPackage";
        }

        JDBCFieldArray whereSQL = new JDBCFieldArray();
        whereSQL.checkAdd("StoredDate", ">=", inParam.BeginDate);
        whereSQL.checkAdd("StoredDate", "<=", inParam.EndDate);
        whereSQL.checkAdd("BoxNo", inParam.BoxNo);
        whereSQL.checkAdd("PackageStatus", inParam.PackageStatus);
        whereSQL.checkAdd("LeftFlag", SysDict.PACKAGE_LEFT_FLAG_N0);
        if (StringUtils.isNotEmpty(inParam.PostmanID)) {
            whereSQL.addSQL(" AND (PostmanID = "
                    + StringUtils.addQuote(inParam.PostmanID)
                    + " OR TakedPersonID = "
                    + StringUtils.addQuote(inParam.PostmanID) + ")");
        }
        whereSQL.checkAdd("CompanyID", inParam.CompanyID);

        if (StringUtils.isNotEmpty(inParam.PackageID)) {
            if (inParam.PackageID.length() <= 3) {
                whereSQL.add("BoxNo", inParam.PackageID);
            } else {
                whereSQL.add("PackageID", "LIKE",
                        "%" + inParam.PackageID.toUpperCase() + "%");
            }
        }

        if (StringUtils.isNotEmpty(inParam.CustomerMobile))
            whereSQL.add("CustomerMobile", "LIKE",
                    "%" + inParam.CustomerMobile.toUpperCase() + "%");

        result = DbUtils.executeQuery(database, viewName, whereSQL, OutParamPTDeliveryRecordQry.class, "StoredTime DESC", inParam.RecordBegin, inParam.RecordNum);
        return result;
    }
}
