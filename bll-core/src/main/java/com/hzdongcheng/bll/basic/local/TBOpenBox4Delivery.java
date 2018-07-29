package com.hzdongcheng.bll.basic.local;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBOpenBox4Delivery;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.device.HAL;
import com.hzdongcheng.device.bean.BoxInfo;
import com.hzdongcheng.device.bean.BoxStatus;
import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.TBBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TBOpenBox4Delivery extends AbstractLocalBusiness {
    public String doBusiness(InParamTBOpenBox4Delivery inParam)
            throws DcdzSystemException {
        String result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) throws DcdzSystemException {
        InParamTBOpenBox4Delivery inParam = (InParamTBOpenBox4Delivery) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.BoxList))
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);

        List<String> scopelist = new ArrayList();
        String[] boxRightList = inParam.BoxList.split(",");
        try {
            // 查询业务层可用箱体(根据输入的箱类型)
            JDBCFieldArray whereSQL = new JDBCFieldArray();
            whereSQL.checkAdd("BoxType", inParam.BoxType);
            List<TBBox> boxList = DbUtils.executeQuery(database, "V_FreeBox", whereSQL, TBBox.class, "DeskNo,DeskBoxNo");

            for (TBBox tbBox : boxList) {
                if (SysDict.POSTMAN_BOXRIGHT_JUDGE_NO.equals(inParam.BoxList)) {
                    scopelist.add(tbBox.BoxNo);
                } else if (CommonDAO.isAvailableBox(boxRightList, tbBox.BoxNo)) {
                    scopelist.add(tbBox.BoxNo);
                }
            }
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);
        }

        if (scopelist.size() == 0)
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);
        String boxName = "";
        for (String no : scopelist) {
            boxName = no;
            BoxStatus boxStatus = HAL.getBoxStatus(no);
            if (boxStatus.getGoodsStatus() == 0 && boxStatus.getOpenStatus() == 0) {
                break;
            }
            boxName = "";
        }
        if (StringUtils.isEmpty(boxName)) {
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_NOFREEDBOX);
        }
        HAL.openBox(boxName);
        try {
            Thread.sleep(100);
            BoxStatus boxStatus = HAL.getBoxStatus(boxName);
            if (boxStatus.getOpenStatus() == 0) {
                HAL.openBox(boxName);
                Thread.sleep(100);
                boxStatus = HAL.getBoxStatus(boxName);
                if (boxStatus.getOpenStatus() == 0) {
                    throw new DcdzSystemException(DBSErrorCode.ERR_DRIVER_OPENBOXFAILD);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return boxName;
    }
}
