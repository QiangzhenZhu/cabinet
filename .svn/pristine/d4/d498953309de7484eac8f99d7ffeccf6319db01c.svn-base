package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Recovery;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox;

public class TBOpenBox4Recovery extends AbstractLocalBusiness {
    public String doBusiness(InParamTBOpenBox4Recovery inParam)
            throws DcdzSystemException {
        String result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamTBOpenBox4Recovery inParam = (InParamTBOpenBox4Recovery) request;
        String result = "";

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.BoxNo))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        whereCols0.add("BoxNo", inParam.BoxNo);
        try {
            TBBox box = boxDAO.find(database, whereCols0);
            HAL.openBox(box.BoxName);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER,
                    e.getMessage());
        }

        return result;
    }
}
