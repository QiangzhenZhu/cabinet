package com.hzdongcheng.bll.basic.local;


import android.database.SQLException;

import com.hzdongcheng.persistent.DAOFactory;
import com.hzdongcheng.persistent.dao.PTDeliverHistoryDAO;
import com.hzdongcheng.persistent.dao.SCSyncFailWaterDAO;
import com.hzdongcheng.persistent.dao.SCUploadDataQueueDAO;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.SCSyncFailWater;
import com.hzdongcheng.persistent.dto.SCUploadDataQueue;
import com.hzdongcheng.persistent.sequence.SequenceGenerator;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSCDelUploadDataQueue;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.bll.constant.SysDict;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;


    public class SCDelUploadDataQueue extends AbstractLocalBusiness
    {
        public String doBusiness(InParamSCDelUploadDataQueue inParam) throws DcdzSystemException
        {
            String result;
            result = callMethod(inParam);
            return result;
        }

        @SuppressWarnings("unchecked")
		@Override
        protected String handleBusiness(IRequest request) throws DcdzSystemException
        {
            InParamSCDelUploadDataQueue inParam = (InParamSCDelUploadDataQueue)request;
            String result = "";

            //1.	验证输入参数是否有效，如果无效返回-1。
            if (StringUtils.isEmpty(inParam.MsgUid))
                throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);
            
            SCUploadDataQueueDAO dataQueueDAO = DAOFactory.getSCUploadDataQueueDAO();
            SCUploadDataQueue dataQueue = new SCUploadDataQueue();
            dataQueue.MsgUid = inParam.MsgUid;

            try
            {
                JDBCFieldArray findWhereCols = new JDBCFieldArray();
                findWhereCols.add("MsgUid",inParam.MsgUid);
                dataQueue = dataQueueDAO.find(database, findWhereCols);

                if (StringUtils.isNotEmpty(inParam.ErrMsg))
                {
                    SequenceGenerator seqGen = SequenceGenerator.GetIntance();
            
                    SCSyncFailWaterDAO failWaterDAO = DAOFactory.getSCSyncFailWaterDAO();
                    SCSyncFailWater failWater = new SCSyncFailWater();

                    failWater.WaterID = seqGen.GetNextKey(SequenceGenerator.SEQ_WATERID);
                    failWater.TerminalNo = dataQueue.TerminalNo;
                    failWater.ServiceName = dataQueue.ServiceName;
                    failWater.PackageID = dataQueue.PackageID;
                    failWater.MsgContent = dataQueue.MsgContent;
                    failWater.FailReason = inParam.ErrMsg;
                    failWater.LastModifyTime = DateUtils.nowDate();


                    failWaterDAO.insert(database, failWater);
                }
                else
                {
                    //修改上传标志
                    if (DateUtils.isNotNull(dataQueue.StoredTime))
                    {
                        PTDeliverHistoryDAO historyPackDAO = DAOFactory.getPTDeliverHistoryDAO();

                        JDBCFieldArray setCols = new JDBCFieldArray();
                        JDBCFieldArray whereCols = new JDBCFieldArray();

                        setCols.add("UploadFlag", SysDict.UPLOAD_FLAG_YES);
                        whereCols.add("PackageID", dataQueue.PackageID);
                        whereCols.add("StoredTime", dataQueue.StoredTime);


                        historyPackDAO.update(database, setCols, whereCols);
                    }
                }

                //删除上传数据队列
                dataQueueDAO.delete(database, findWhereCols);
            }
            catch (SQLException ex)
            {
                log.error("[Del Queue Data Error]=" + ex.getMessage());
            }
            

            return result;
        }
    }
