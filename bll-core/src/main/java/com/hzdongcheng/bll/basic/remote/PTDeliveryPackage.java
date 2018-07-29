package com.hzdongcheng.bll.basic.remote;

import android.database.SQLException;

import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTDeliveryPackage;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.monitors.SyncDataProc;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.bll.utils.ThreadPool;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;


/**
 * 投递包裹
 **/
public class PTDeliveryPackage extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        isUseDB = true;
        // 本地数据已经提交
        String result = callMethod(request, responder, timeOut);

        // 同步向服务端发送数据
        if (StringUtils.isNotEmpty(result)) {
            SyncDataProc.SyncData syncData = new SyncDataProc.SyncData(request, result);
            ThreadPool.QueueUserWorkItem(new SyncDataProc.SendDeliveryRecord(syncData));
        }

        return result;
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder, int timeOut) throws DcdzSystemException {
        String result = "";
        InParamPTDeliveryPackage inParam = (InParamPTDeliveryPackage) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PostmanID) || StringUtils.isEmpty(inParam.PackageID)
                || StringUtils.isEmpty(inParam.BoxNo)) {
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);
        }

        inParam.PackageID = CommonDAO.normalizePackageID(inParam.PackageID);

        // 参数赋初值
        inParam.TerminalNo = DBSContext.terminalUid;
        if (StringUtils.isEmpty(inParam.LeftFlag))
            inParam.LeftFlag = SysDict.PACKAGE_LEFT_FLAG_N0;

        if (StringUtils.isEmpty(inParam.PosPayFlag))
            inParam.PosPayFlag = SysDict.PAY_FLAG_NO;

        if (DateUtils.isNull(inParam.StoredTime))
            inParam.StoredTime = DateUtils.nowDate();

        inParam.TradeWaterNo = CommonDAO.buildTradeNo();

        // 判读是否需要生成开箱密码
        String pwd = "";
        if (SysDict.TAKEOUTPWD_SOURCE_LOCAL_YES.equals(DBSContext.ctrlParam.takeOutPWDSource)&&inParam.OpenBoxKey=="") // 本地生成开箱密码
        {
            int pwdLen = NumberUtils.parseInt(DBSContext.ctrlParam.takeOutPwdLen);
            if (SysDict.TAKEOUTPWD_FORM_NUMBER.equals(DBSContext.ctrlParam.takeOutPwdFormat)) {
                pwd = RandUtils.generateNumber(pwdLen);
            } else if (SysDict.TAKEOUTPWD_FORM_CHAR.equals(DBSContext.ctrlParam.takeOutPwdFormat)) {
                pwd = RandUtils.generateCharacter(pwdLen);
            } else if (SysDict.TAKEOUTPWD_FORM_NUMBERCHAR.equals(DBSContext.ctrlParam.takeOutPwdFormat)) {
                pwd = RandUtils.generateNumber(pwdLen);
            }
            inParam.OpenBoxKey = pwd;
        }
        pwd = EncryptHelper.md5Encrypt(inParam.OpenBoxKey);

        PTInBoxPackageDAO inboxPackDAO = DAOFactory.getPTInBoxPackageDAO();
        // ////////////////////////////////////////////////////////////////////////
        // /以下判断应该移到本地订单判断里面

        // 判断是否已经投递过
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("PackageID", inParam.PackageID);
        try {
            if (inboxPackDAO.isExist(database, whereCols) > 0) {
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_PACKDELIVERYD);
            }

            // 判断箱门是否已经再使用
            JDBCFieldArray whereCols1 = new JDBCFieldArray();
            whereCols1.add("BoxNo", inParam.BoxNo);

            if (inboxPackDAO.isExist(database, whereCols1) > 0) {
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_BOXHAVEUSED);
            }

            // ////////////////////////////////////////////////////////////////////////

            // 插入在箱信息
            PTInBoxPackage inboxPack = new PTInBoxPackage();
            inboxPack.TerminalNo = inParam.TerminalNo;
            inboxPack.BoxNo = inParam.BoxNo;
            inboxPack.PackageID = inParam.PackageID;
            inboxPack.PostmanID = inParam.PostmanID;
            inboxPack.CompanyID = inParam.CompanyID;
            inboxPack.StoredTime = inParam.StoredTime;
            inboxPack.StoredDate = DateUtils.nowDate();
            inboxPack.ExpiredTime = inParam.ExpiredTime;
            inboxPack.CustomerID = inParam.CustomerID;
            inboxPack.CustomerMobile = inParam.CustomerMobile;
            inboxPack.CustomerName = "";
            inboxPack.CustomerAddress = "";

            inboxPack.LeftFlag = inParam.LeftFlag;
            inboxPack.PosPayFlag = inParam.PosPayFlag;
            inboxPack.PackageStatus = SysDict.PACKAGE_STATUS_NORMAL;
            inboxPack.LastModifyTime = inboxPack.StoredTime;
            inboxPack.UploadFlag = SysDict.UPLOAD_FLAG_NO;
            inboxPack.TradeWaterNo = inParam.TradeWaterNo;
            inboxPack.Remark = inParam.Remark;
            inboxPack.DynamicCode = inParam.DynamicCode;
            inboxPack.OpenBoxKey = pwd;

            inboxPackDAO.insert(database, inboxPack);

            // 插入数据上传队列
            result = CommonDAO.insertUploadDataQueue(database, request);
        } catch (SQLException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_DATABASE_DATABASELAYER, e.getMessage());
        }
        return result;
    }
}
