package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBBoxQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import java.util.ArrayList;
import java.util.List;


public class TBBoxQry extends AbstractLocalBusiness {
    public List<OutParamTBBoxQry> doBusiness(InParamTBBoxQry inParam) throws DcdzSystemException {
        List<OutParamTBBoxQry> result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<OutParamTBBoxQry> handleBusiness(IRequest request) throws DcdzSystemException {
        InParamTBBoxQry inParam = (InParamTBBoxQry) request;
        //1.	验证输入参数是否有效，如果无效返回-1。
        JDBCFieldArray whereSQL = new JDBCFieldArray();

        if (inParam.DeskNo != -1)
            whereSQL.add("DeskNo", inParam.DeskNo);

        whereSQL.checkAdd("BoxNo", inParam.BoxNo);
        whereSQL.checkAdd("BoxStatus", inParam.BoxStatus);

        String sql = "SELECT * FROM V_TBBox WHERE 1=1 " + whereSQL.toLogSetSQL();

        List<String> orderByField = new ArrayList<String>();
        orderByField.add("DeskNo");
        orderByField.add("DeskBoxNo");


        //Cursor cs = dbWrapper.executeQuery(sql, whereSQL, orderByField, inParam.RecordBegin, inParam.RecordNum);
        ///return MyDbHelper.readEntityListByReader(cs, TBBox.class);
        return DbUtils.executeQuery(database,"V_TBBox", whereSQL, OutParamTBBoxQry.class, "DeskNo,DeskBoxNo", inParam.RecordBegin, inParam.RecordNum);
    }
}