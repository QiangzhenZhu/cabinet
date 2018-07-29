package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBBoxQry4Detect;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxInfo;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.device.bean.SlaveStatus;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.TBDeskDAO;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox4Detect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TBBoxQry4Detect extends AbstractLocalBusiness
    {
        public List<TBBox4Detect> doBusiness(InParamTBBoxQry4Detect inParam) throws DcdzSystemException
        {
            List<TBBox4Detect> result;
            result = callMethod( inParam);
            return result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected List<TBBox4Detect> handleBusiness(IRequest request) throws DcdzSystemException {
            InParamTBBoxQry4Detect inParam = (InParamTBBoxQry4Detect) request;

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.OperID))
                throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);


            JDBCFieldArray whereCols = new JDBCFieldArray();
            List<TBBox4Detect> list = DbUtils.executeQuery(database, "V_TBBox4Detect", whereCols, TBBox4Detect.class, "DeskNo,DeskBoxNo");
            TBDeskDAO deskDAO = DAOFactory.getTBDeskDAO();
            int desks = deskDAO.isExist(database, whereCols);

            Map<Integer, SlaveStatus> boxStatusMap = new HashMap<>();

            while (desks > 0) {
                try {
                    boxStatusMap.put(desks - 1, HAL.getSlaveStatus(desks - 1));
                } catch (DcdzSystemException e) {
                    log.error("获取箱门状态错误 " + e.getErrorTitle());
                }
                desks--;
            }
            for (TBBox4Detect box : list) {
                if(boxStatusMap.containsKey(box.DeskNo)) {
                    BoxStatus boxInfo = boxStatusMap.get(box.DeskNo).getBoxStatusArray(box.DeskBoxNo - 1);
                    if (boxInfo != null) {
                        if (SysDict.BOX_TYPE_FRESH.equals(box.BoxType)
                                || SysDict.BOX_TYPE_FASTFOOD.equals(box.BoxType)) {
                            box.ArticeStatus = SysDict.BOX_ARTICLE_STATUS_NON;
                        } else {
                            box.ArticeStatus = String.valueOf(boxInfo.getGoodsStatus());
                        }
                        box.OpenStatus = String.valueOf(boxInfo.getOpenStatus());
                    }
                }
            }
            return list;
        }
    }
