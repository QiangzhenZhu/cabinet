package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamPTPackLocalFilter;
import com.hzdongcheng.bll.basic.dto.OutParamPTExpiredPackQry;
import com.hzdongcheng.bll.basic.dto.OutParamPTVerfiyUser;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Shalom on 2017/11/28.
 */

public class PTPackLocalFilter extends AbstractLocalBusiness {
    public String doBusiness(InParamPTPackLocalFilter inParam) throws DcdzSystemException {
        String result;
        result = callMethod(inParam);
        return result;
    }

    /// <summary>
    /// 具体业务处理函数
    /// </summary>
    /// <param name="request"></param>
    /// <returns></returns>
    protected String handleBusiness(IRequest request) {
        InParamPTPackLocalFilter inParam = (InParamPTPackLocalFilter) request;
        String result = "";

        //1.	验证输入参数是否有效，如果无效返回-1。
        //if (StringUtils.IsEmpty(inParam.PostmanID))
        //    throw new DBSException(ErrorMessage.ERR_SYSTEM_PARAMTER);
        Map<String, String> pcakageMap = new HashMap<>();


        //查询所有在箱记录
        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        List<PTInBoxPackage> inboxList = null;
        try {
            inboxList = inboxPackDAO.executeQuery(database, whereCols);
        } catch (DcdzSystemException e) {
            e.printStackTrace();
        }
        for (PTInBoxPackage obj : inboxList) {
            pcakageMap.put(obj.PackageID, obj.BoxNo);
        }

        if (inParam.PTVerfiyUserList != null) {
            Iterator<OutParamPTVerfiyUser> it = inParam.PTVerfiyUserList.iterator();
            while (it.hasNext()) {
                OutParamPTVerfiyUser param = it.next();
                if (!pcakageMap.containsKey(param.PackageID))
                    it.remove();
                else
                    param.BoxNo = pcakageMap.get(param.PackageID);
            }
        }
        if (inParam.ExpiredPackList != null) {
            Iterator<OutParamPTExpiredPackQry> it = inParam.ExpiredPackList.iterator();
            while (it.hasNext()) {
                OutParamPTExpiredPackQry param = it.next();
                if (!pcakageMap.containsKey(param.PID))
                    it.remove();
                else
                    param.BNO = pcakageMap.get(param.PID);
            }
        }


        return result;
    }

}
