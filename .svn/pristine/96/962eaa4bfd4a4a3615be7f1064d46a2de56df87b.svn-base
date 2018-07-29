package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryRecordQryCount;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;

public class PTDeliveryRecordQryCount extends AbstractLocalBusiness
    {
        public int doBusiness(InParamPTDeliveryRecordQryCount inParam) throws DcdzSystemException
        {
            int result;
            result = callMethod( inParam);
            return result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected Object handleBusiness(IRequest request) throws DcdzSystemException
        {
            InParamPTDeliveryRecordQryCount inParam = (InParamPTDeliveryRecordQryCount)request;
            int result = 0;

            //1.	验证输入参数是否有效，如果无效返回-1。

            String viewName = "";
            if ("1".equals(inParam.InboxFlag))
            {
                viewName = "V_InboxPackage";
            }
            else
            {
                if (StringUtils.isEmpty(inParam.PackageStatus))
                    viewName = "V_AllOrder";
                else if (SysDict.PACKAGE_STATUS_NORMAL.equals(inParam.PackageStatus) ||
                       SysDict.PACKAGE_STATUS_LOCKED.equals(inParam.PackageStatus) ||
                       SysDict.PACKAGE_STATUS_TIMEOUT.equals(inParam.PackageStatus))
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
            //whereSQL.checkAdd("PostmanID", inParam.PostmanID);
            if (StringUtils.isNotEmpty(inParam.PostmanID))
            {
                whereSQL.addSQL(" AND (PostmanID = " + StringUtils.addQuote(inParam.PostmanID) + " OR TakedPersonID = " + StringUtils.addQuote(inParam.PostmanID) + ")");
            }
            whereSQL.checkAdd("CompanyID", inParam.CompanyID);

            if (StringUtils.isNotEmpty(inParam.PackageID))
            {
                if (inParam.PackageID.length() <= 3)
                {
                    whereSQL.add("BoxNo", inParam.PackageID);
                }
                else
                {
                    whereSQL.add("PackageID", "LIKE", "%" + inParam.PackageID.toUpperCase() + "%");
                }
            }

            if (StringUtils.isNotEmpty(inParam.CustomerMobile))
                whereSQL.add("CustomerMobile", "LIKE", "%" + inParam.CustomerMobile.toUpperCase() + "%");

            if (viewName.equals("V_AllOrder"))
            {
                //String sql1 = "SELECT COUNT(PackageID) FROM V_InboxPackage" + " WHERE 1=1 " + whereSQL.PreparedWhereSQL();
                //result = dbWrapper.executeQueryCount(sql1, whereSQL);
                result = DbUtils.executeCount(database, "V_InboxPackage", whereSQL);

			    //String sql2 = "SELECT COUNT(PackageID) FROM V_HistoryPackage"
				//	+ " WHERE 1=1 " + whereSQL.ToLogWhereSQL();
                result = result + DbUtils.executeCount(database, "V_HistoryPackage", whereSQL);
            }
            else
            {
                //String sql = "SELECT COUNT(PackageID) FROM " + viewName + " WHERE 1=1 " + whereSQL.ToLogWhereSQL();
                //result = dbWrapper.executeQueryCount(sql, whereSQL);
                result = DbUtils.executeCount(database, viewName, whereSQL);
            }

            return result;
        }
    }
