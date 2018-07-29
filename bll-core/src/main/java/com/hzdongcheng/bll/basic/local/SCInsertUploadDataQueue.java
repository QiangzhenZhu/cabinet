package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSCInsertUploadDataQueue;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;


    public class SCInsertUploadDataQueue extends AbstractLocalBusiness
    {
        public String doBusiness(InParamSCInsertUploadDataQueue inParam) throws DcdzSystemException
        {
            String result;
            result = callMethod(inParam);
            return result;
        }

        @Override
        protected String handleBusiness(IRequest request)
        {
            InParamSCInsertUploadDataQueue inParam = (InParamSCInsertUploadDataQueue)request;
            String result = "";

            

            return result;
        }
    }
