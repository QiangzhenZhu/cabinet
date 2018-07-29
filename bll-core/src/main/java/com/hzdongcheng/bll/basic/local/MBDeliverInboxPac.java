package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamMBDeliverInboxPac;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryPackage;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.SysDict;


import java.util.List;

public class MBDeliverInboxPac extends AbstractLocalBusiness {
    public String doBusiness(InParamMBDeliverInboxPac inParam)
            throws DcdzSystemException {

        String result;
        result = callMethod(inParam);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String handleBusiness(IRequest request) {
        InParamMBDeliverInboxPac inParam = (InParamMBDeliverInboxPac) request;
        String result = "";

        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereColsDummy = new JDBCFieldArray();
        //List<String> orderByField = new ArrayList<String>();
        //orderByField.add("StoredTime DESC");

        List<PTInBoxPackage> listLog = null;
        try {
            listLog = inboxPackDAO.executeQuery(database, whereColsDummy, "StoredTime DESC");

            for (PTInBoxPackage item : listLog) {
                InParamPTDeliveryPackage inParamDelivery = new InParamPTDeliveryPackage();

                inParamDelivery.TerminalNo = item.TerminalNo;
                inParamDelivery.TradeWaterNo = item.TradeWaterNo;
                inParamDelivery.PostmanID = item.PostmanID;
                inParamDelivery.DynamicCode = item.DynamicCode;
                inParamDelivery.CompanyID = item.CompanyID;
                inParamDelivery.PackageID = item.PackageID;
                inParamDelivery.BoxNo = item.BoxNo;
                inParamDelivery.CustomerID = item.CustomerID;
                inParamDelivery.StoredTime = item.StoredTime;
                inParamDelivery.CustomerMobile = item.CustomerMobile;
                inParamDelivery.LeftFlag = item.LeftFlag;
                inParamDelivery.PosPayFlag = item.PosPayFlag;

                // DBSClient.GetIntance().BusinessProxy.doBusiness(inParam);

                // 判读是否需要生成开箱密码
                String pwd = "";
                if (SysDict.TAKEOUTPWD_SOURCE_LOCAL_YES.equals(DBSContext.ctrlParam.takeOutPWDSource)) // 本地生成开箱密码
                {
                    int pwdLen = NumberUtils.parseInt(DBSContext.ctrlParam.takeOutPwdLen);
                    if (SysDict.TAKEOUTPWD_FORM_NUMBER.equals(DBSContext.ctrlParam.takeOutPwdFormat))
                        pwd = RandUtils.generateNumber(pwdLen);
                    else if (SysDict.TAKEOUTPWD_FORM_CHAR.equals(DBSContext.ctrlParam.takeOutPwdFormat))
                        pwd = RandUtils.generateCharacter(pwdLen);
                    else if (SysDict.TAKEOUTPWD_FORM_NUMBERCHAR.equals(DBSContext.ctrlParam.takeOutPwdFormat))
                        pwd = RandUtils.generateString(pwdLen);

                    // md5
                    inParamDelivery.OpenBoxKey = pwd;

                    // pwd = EncryptHelper.MD5Encrypt(pwd);
                }

                // 插入数据上传队列
                result = CommonDAO.insertUploadDataQueue(database, inParamDelivery);
            }

            // 调用CommonDAO.AddOperatorLog(OperID，功能编号，系统日期时间，“”)
            OPOperatorLog log = new OPOperatorLog();
            log.FunctionID = inParam.FunctionID;
            log.OccurTime = DateUtils.nowDate();
            log.Remark = "";

            CommonDAO.addOperatorLog(database, log);
        } catch (DcdzSystemException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
