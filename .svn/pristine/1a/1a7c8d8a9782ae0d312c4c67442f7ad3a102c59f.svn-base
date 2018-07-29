package com.hzdongcheng.bll.common;

import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.OPOperatorLogDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.persistent.ex.DBErrorCode;
import com.hzdongcheng.persistent.sequence.SequenceGenerator;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamPTPreDeliveryHandle;
import com.hzdongcheng.bll.basic.dto.InParamSCDelUploadDataQueue;
import com.hzdongcheng.bll.basic.dto.InParamSCModUploadDataQueue;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.OPOperatorLogDAO;
import com.hzdongcheng.persistent.dao.PTInBoxPackageDAO;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.dao.SMSystemInfoDAO;
import com.hzdongcheng.persistent.dao.TBBoxDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.OPOperatorLog;
import com.hzdongcheng.persistent.dto.PTInBoxPackage;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;
import com.hzdongcheng.persistent.dto.SMSystemInfo;
import com.hzdongcheng.persistent.dto.TBBox;
import com.hzdongcheng.persistent.ex.DBErrorCode;
import com.hzdongcheng.persistent.sequence.SequenceGenerator;
import com.google.gson.reflect.TypeToken;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.bll.utils.EncryptHelper;
import com.hzdongcheng.bll.utils.ObjectUtils;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.RandUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import android.database.SQLException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zxy on 2017/9/16.
 */

public class CommonDAO {
    private static Log4jUtils log = Log4jUtils.createInstanse(CommonDAO.class);
    private static SequenceGenerator seqGen = SequenceGenerator.GetIntance();
    private static String[] array36 = new String[]{"0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

    /**
     * 生成流水号
     *
     * @return
     */
    public static String buildTradeNo() {
        StringBuilder ret = new StringBuilder();

        if (DBSContext.ctrlParam.tradenoFormat
                .equals(SysDict.TRADENO_TYPE_HAIER)) {
            // 流水号 时间戳[精确到毫秒yyyyMMddHHmmssS] + 5位随机码[数字+大小写字母]
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            ret = new StringBuilder(format.format(new Date()) + RandUtils.generateCharacter(5));

            return ret.toString();
        } else {
            try {
                long WaterID = seqGen.GetNextKey(SequenceGenerator.SEQ_TRADEWATERID);
                long lNum = WaterID;
                long last = 0;
                while (lNum != 0) {
                    last = lNum % 36;
                    lNum = lNum / 36;
                    ret.insert(0, array36[(int) last]);
                }

                if (ret.length() < 6) {
                    ret.insert(0, "000000");
                    ret = new StringBuilder(ret.substring(ret.length() - 6));
                } else if (ret.length() > 6)
                    ret = new StringBuilder(ret.substring(0, 6));

                ret.insert(0, DBSContext.terminalUid);
            } catch (SQLException e) {
                log.error("[buildTradeNo]" + e.getMessage());
                e.printStackTrace();
            }

        }

        return ret.toString();
    }

    public static String normalizePackageID(String packageID) {
        return packageID.toUpperCase();
    }

    public static String insertUploadDataQueue(SQLiteDatabase database, IRequest request) throws DcdzSystemException, SQLException {
        SCUploadDataQueueDAO dataQueueDAO = DAOFactory.getSCUploadDataQueueDAO();
        SCUploadDataQueue dataQueue = new SCUploadDataQueue();

        dataQueue.MsgUid = StringUtils.createUUID();
        dataQueue.ServiceName = request.getClass().getSimpleName();
        dataQueue.TerminalNo = ObjectUtils.GetFieldStrValue(request,
                "TerminalNo");

        if (ObjectUtils.HaveField(request, "PackageID"))
            dataQueue.PackageID = ObjectUtils.GetFieldStrValue(request,
                    "PackageID");

        if (ObjectUtils.HaveField(request, "StoredTime"))
            dataQueue.StoredTime = ObjectUtils.GetFieldDateTimeValue(request,
                    "StoredTime");
        else
            dataQueue.StoredTime = DateUtils.getMinDate();

        dataQueue.MsgContent = PacketUtils.serializeObject(request);
        dataQueue.LastModifyTime = DateUtils.nowDate();

        dataQueueDAO.insert(database, dataQueue);

        return dataQueue.MsgUid;
    }

    public static void addOperatorLog(SQLiteDatabase database, OPOperatorLog log) throws DcdzSystemException {
        OPOperatorLogDAO opOperatorLogDAO = DAOFactory.getOPOperatorLogDAO();
        try {
            log.OperLogID = (int) SequenceGenerator.GetIntance().GetNextKey(SequenceGenerator.SEQ_OPERLOGID);
            log.OccurTime = DateUtils.nowDate();
            log.OccurDate = DateUtils.nowDate();
            opOperatorLogDAO.insert(database, log);
        } catch (Exception e) {
            throw new DcdzSystemException(DBErrorCode.ERR_DB_DATABASELAYER, e.getMessage());
        }
    }

    public static String localVerifyPostmanPwd(String postmanID, String password, String verifyFlag) throws DcdzSystemException {
        String md5 = "";
        if (DBSContext.ctrlParam.passwdCheckModel
                .equals(SysDict.PASSWD_CHECKMODEL_JINGDONG)) {
            // 京东MD5加密算法
            md5 = EncryptHelper.md5Encrypt(password);
        } else if (DBSContext.ctrlParam.passwdCheckModel
                .equals(SysDict.PASSWD_CHECKMODEL_HZYOUCHENG)) {
            md5 = password;

            String pattern = "^[0-9]*$";
            if (!postmanID.matches(pattern))
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGPWD);

            if (postmanID.length() != 11)
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGPWD);

            // 本地校验密码
            if (!password.equals(getValidCode(postmanID)))
                throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_WRONGCODE);
        } else {
            if (DBSContext.ctrlParam.postmanMD5Flag
                    .equals(SysDict.POSTMANPWD_NEED_MD5_YES)
                    && StringUtils.isNotEmpty(password)) {
                if (password.length() == 32)
                    md5 = password;
                else
                    md5 = EncryptHelper.md5Encrypt(password);
            } else
                md5 = password;
        }

        return md5;
    }

    public static String getValidCode(String postmanID) {
        int[] plus = {8, 6, 4, 2, 3, 5, 9, 7};
        int[] nums = new int[11];
        int k = 0;
        StringBuilder ret = new StringBuilder();
        for (char c : postmanID.toCharArray()) {
            nums[k] = Integer.parseInt(String.valueOf(c));
            k++;
        }

        for (int i = 0; i < 4; i++) {
            int res = 0;
            for (int j = 0; j < 8; j++) {
                res += nums[i + j] * plus[j];
            }
            int code = 11 - (res % 11);
            if (code == 10)
                code = 0;
            if (code == 11)
                code = 5;
            ret.append(String.valueOf(code));
        }

        return ret.toString();
    }

    public static boolean isAvailableBox(String[] boxRightList, String boxNo) {
        for (String aBoxRightList : boxRightList) {
            if (aBoxRightList.equals(boxNo))
                return true;
        }

        return false;
    }

    public static void buildBoxList4Desk(SQLiteDatabase database, int deskNo, int boxNum, String terminalUid) throws DcdzSystemException {
        // 查询箱门总数量
        TBBoxDAO boxDAO = DAOFactory.getTBBoxDAO();
        JDBCFieldArray whereColsDummy = new JDBCFieldArray();
        try {
            int boxCount = boxDAO.isExist(database, whereColsDummy);
            int deskBoxCount = 0;

            TBBox box = new TBBox();
            for (int i = 0; i < boxNum; i++) {
                boxCount++;
                deskBoxCount++;

                box.BoxNo = String.valueOf(boxCount);
                box.DeskBoxNo = deskBoxCount;
                box.DeskNo = deskNo;

                box.BoxName = box.BoxNo; // 显示名称
                box.BoxType = SysDict.BOX_TYPE_SMALL; // 默认都为小箱
                box.BoxStatus = SysDict.BOX_STATUS_NORMAL;

                boxDAO.insert(database, box);
            }
        } catch (SQLException e) {
            log.error("[buildBoxList4Desk]" + e.getMessage());
        }
    }

    public static PTInBoxPackage findPackageByID(SQLiteDatabase database, String inPackageID) throws DcdzSystemException {
        PTInBoxPackageDAO inBoxPackageDAO = DAOFactory.getPTInBoxPackageDAO();
        JDBCFieldArray whereCols = new JDBCFieldArray();
        whereCols.add("PackageID", inPackageID);
        return inBoxPackageDAO.find(database, whereCols);
    }

    public static SMSystemInfo findSystemInfo(SQLiteDatabase database) throws DcdzSystemException {
        SMSystemInfoDAO systemDAO = DAOFactory.getSMSystemInfoDAO();
        JDBCFieldArray whereCols0 = new JDBCFieldArray();
        return systemDAO.find(database, whereCols0);
    }

    public static String preDeliveryHandle4Cainiao(InParamPTPreDeliveryHandle inParam) {
        String result = "";

        if (inParam.inParamPTDeliveryPackage == null)
            return result;

        if (StringUtils.isEmpty(inParam.Remark))
            return result;

        //业务处理开始
        Type type = new TypeToken<CainiaoPackageDetail[]>() {
        }.getType();
        CainiaoPackageDetail[] details = (CainiaoPackageDetail[]) PacketUtils.deserializeObject(inParam.Remark, type);

        String straMobile = inParam.inParamPTDeliveryPackage.CustomerMobile;
        for (CainiaoPackageDetail item : details) {
            if (StringUtils.isNotEmpty(item.customerMobile)
                    && StringUtils.isNotEmpty(straMobile)
                    && straMobile.equals(item.customerMobile.trim())) {
                inParam.inParamPTDeliveryPackage.OfLogisticsID = item.ofLogisticsID;
                inParam.inParamPTDeliveryPackage.OfLogisticsName = item.ofLogisticsName;
                inParam.inParamPTDeliveryPackage.StaOrderID = item.staOrderID;
                break;
            }
        }


        log.info("[StaOrderID]:" + inParam.inParamPTDeliveryPackage.StaOrderID + "," + inParam.Remark);

        return result;
    }

    public static void delDataQueueTimestamp(String msgId, String errMsg) {
        try {
            InParamSCDelUploadDataQueue inParam = new InParamSCDelUploadDataQueue();
            inParam.MsgUid = msgId;
            inParam.ErrMsg = errMsg;

            DBSContext.localContext.doBusiness(inParam);
        } catch (Exception ex) {
            log.error("-->清除上传队列失败 " + ex.getMessage());

            modifyDataQueueTimestamp(msgId);
        }

    }

    public static void modifyDataQueueTimestamp(String msgId) {
        try {
            InParamSCModUploadDataQueue inParam = new InParamSCModUploadDataQueue();
            inParam.MsgUid = msgId;

            DBSContext.localContext.doBusiness(inParam);
        } catch (Exception ex) {
            log.error("-->更新上传队列失败 " + ex.getMessage());
        }
    }

    public class CainiaoPackageDetail {
        public String packageID = "";     //订单号
        public String customerMobile = ""; //取件人手机
        public String ofLogisticsID = ""; //运单所属物流公司
        public String ofLogisticsName = ""; //运单所属物流公司名称
        public String staOrderID = ""; //电商运单号
    }

}
