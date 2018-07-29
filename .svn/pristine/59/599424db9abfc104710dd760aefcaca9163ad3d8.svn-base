package com.hzdongcheng.bll.basic.remote;


import com.hzdongcheng.bll.AbstractRemoteBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTPostmanLogin;
import com.hzdongcheng.bll.basic.dto.OutParamPTPostmanLogin;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PMCorpBoxRightDAO;
import com.hzdongcheng.persistent.dao.PMPostmanBoxRightDAO;
import com.hzdongcheng.persistent.dao.PMPostmanDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.PMCorpBoxRight;
import com.hzdongcheng.persistent.dto.PMPostman;
import com.hzdongcheng.persistent.dto.PMPostmanBoxRight;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.common.CommonDAO;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.List;

/**
 * <summary> 投递员登陆验证
 **/
public class PTPostmanLogin extends AbstractRemoteBusiness {
    @Override
    public String doBusiness(IRequest request, IResponder responder, int timeOut)
            throws DcdzSystemException {
        if (timeOut == 0)
            timeOut = 20;
        isUseDB = true;

        return super.doBusiness(request, responder, timeOut);
    }

    @Override
    protected String handleBusiness(IRequest request, IResponder responder,
                                    int timeOut) throws DcdzSystemException {
        String result = "";
        InParamPTPostmanLogin inParam = (InParamPTPostmanLogin) request;

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.PostmanID)
                //|| StringUtils.isEmpty(inParam.Password)
                || StringUtils.isEmpty(inParam.VerifyFlag))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        inParam.TerminalNo = DBSContext.terminalUid;
        inParam.TradeWaterNo = CommonDAO.buildTradeNo();

        if (inParam.LoginType.equals(SysDict.LOGIN_TYPE_MOBILE)
                && StringUtils.isEmpty(inParam.Password))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

        // 本地网络验证
        if (SysDict.POSTMAN_VERIFY_PWD.equals(inParam.VerifyFlag))
            inParam.Password = CommonDAO.localVerifyPostmanPwd(
                    inParam.PostmanID, inParam.Password, inParam.VerifyFlag);

        // 判断是否为本地验证
        if (SysDict.POSTMAN_CHECKSOURCE_LOCAL.equals(DBSContext.ctrlParam.postmanCheckSource)) {
            String postmanid = "";
            PMPostmanDAO postmanDAO = DAOFactory.getPMPostmanDAO();
            //try {
            JDBCFieldArray whereCols0 = new JDBCFieldArray();
            whereCols0.add("PostmanID", inParam.PostmanID);
            List<PMPostman> listMan = postmanDAO.executeQuery(database, whereCols0);
            if (listMan.size() > 0) {
                postmanid = listMan.get(0).PostmanID;
            } else {
                whereCols0 = new JDBCFieldArray();
                whereCols0.add("BindCardID", inParam.PostmanID);
                listMan = postmanDAO.executeQuery(database, whereCols0);

                if (listMan.size() > 0) {
                    postmanid = listMan.get(0).PostmanID;
                } else {
                    whereCols0 = new JDBCFieldArray();
                    whereCols0.add("BindMobile", inParam.PostmanID);
                    listMan = postmanDAO.executeQuery(database, whereCols0);

                    if (listMan.size() > 0) {
                        postmanid = listMan.get(0).PostmanID;
                    } else {
                        // 用户名或密码错误
                        throw new DcdzSystemException(
                                DBSErrorCode.ERR_BUSINESS_WRONGPWD);
                    }
                }
            }

            // 柜体权限是否需要验证
            JDBCFieldArray whereColsFind = new JDBCFieldArray();
            whereColsFind.add("PostmanID", postmanid);
            PMPostman postman = postmanDAO.find(database, whereColsFind);

            if (postman.Password.toLowerCase().equals(
                    inParam.Password.toLowerCase())) {
                OutParamPTPostmanLogin outParam = new OutParamPTPostmanLogin();
                outParam.PostmanID = postman.PostmanID;
                outParam.PostmanName = postman.PostmanName;
                outParam.PostmanType = postman.PostmanType;
                outParam.InputMobileFlag = postman.InputMobileFlag;
                outParam.CompanyID = postman.CompanyID;
                outParam.DynamicCode = "";
                outParam.VerifyFlag = SysDict.POSTMAN_CHECKMODEL_STATIC;

                // 查询格口权限
                outParam.BoxList = "0";
                if (DBSContext.ctrlParam.manBoxRightCheck
                        .equals(SysDict.POSTMAN_BOXRIGHT_JUDGE_NO)) {
                    outParam.BoxList = "0";
                } else if (DBSContext.ctrlParam.manBoxRightCheck
                        .equals(SysDict.POSTMAN_BOXRIGHT_JUDGE_COMPANY)) {
                    PMCorpBoxRightDAO corpRightDAO = DAOFactory.getPMCorpBoxRightDAO();
                    JDBCFieldArray whereCols = new JDBCFieldArray();
                    whereCols.add("CompanyID", postman.CompanyID);

                    List<PMCorpBoxRight> list = corpRightDAO.executeQuery(database, whereCols);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        if (i > 0)
                            sb.append(",");
                        else
                            sb.append(list.get(i).BoxNo);
                    }

                    if (list.size() > 0) {
                        outParam.BoxList = sb.toString();
                    }
                } else if (DBSContext.ctrlParam.manBoxRightCheck
                        .equals(SysDict.POSTMAN_BOXRIGHT_JUDGE_PERSON)) {
                    PMPostmanBoxRightDAO manRightDAO = DAOFactory
                            .getPMPostmanBoxRightDAO();
                    JDBCFieldArray whereCols = new JDBCFieldArray();
                    whereCols.add("PostmanID", postman.PostmanID);

                    List<PMPostmanBoxRight> list = manRightDAO.executeQuery(database, whereCols);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < list.size(); i++) {
                        if (i > 0)
                            sb.append(",");
                        else
                            sb.append(list.get(i).BoxNo);
                    }

                    if (list.size() > 0) {
                        outParam.BoxList = sb.toString();
                    }
                }

                // 打包返回
                if (responder != null) {
                    responder.result(PacketUtils
                            .buildLocalJsonResult(outParam));
                }
            } else {
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGPWD);
            }
        } else // 网络验证
        {
            if (responder != null) {
                netProxy.sendRequest(request, responder, timeOut,
                        DBSContext.secretKey);
            }
        }

        return result;
    }
}
