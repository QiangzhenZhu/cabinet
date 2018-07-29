package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxQry4Open;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox4Open;

import java.util.List;


public class TBBoxQry4Open extends AbstractLocalBusiness
    {
        public List<TBBox4Open> doBusiness(InParamTBBoxQry4Open inParam) throws DcdzSystemException
        {
        	List<TBBox4Open> result;
            result = callMethod(inParam);
            return result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected List<TBBox4Open> handleBusiness(IRequest request) throws DcdzSystemException
        {
            InParamTBBoxQry4Open inParam = (InParamTBBoxQry4Open)request;

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.OperID))
                throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);


            //String sql = "SELECT * FROM V_TBBox4Open ORDER BY DeskNo,DeskBoxNo";
            //Cursor cs = dbWrapper.executeQuery(sql);
            //return MyDbHelper.readEntityListByReader(cs,TBBox4Open.class);
            JDBCFieldArray whereCols = new JDBCFieldArray();
            return DbUtils.executeQuery(database,"V_TBBox4Open", whereCols,TBBox4Open.class,"DeskNo,DeskBoxNo");
        }
    }
