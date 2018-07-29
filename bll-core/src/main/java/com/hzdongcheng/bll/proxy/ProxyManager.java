package com.hzdongcheng.bll.proxy;

import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.exception.error.ErrorTitle;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyManager {
    private static ProxyManager instance;
    private BusinessProxy proxy;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Log4jUtils log = Log4jUtils.createInstanse(ProxyManager.class);

    private ProxyManager() {
        proxy = new Proxy4Dcdzsoft();
    }

    public synchronized static ProxyManager getInstance() {
        if (instance == null) {
            instance = new ProxyManager();
        }
        return instance;
    }

    public void setProxy(BusinessProxy businessProxy) {
        this.proxy = businessProxy;
    }

    public void sendRequest(IRequest request, IResponder responder, int timeout, String secretKey) {
        RequestData requestData = new RequestData();
        requestData.request = request;
        requestData.responder = responder;
        requestData.timeOut = timeout;
        requestData.secretKey = secretKey;
        executorService.submit(new sendData(requestData));

    }

    class sendData implements Runnable {
        RequestData data;

        sendData(RequestData requestData) {
            data = requestData;
        }

        @Override
        public void run() {
            Method method = null;
            if (proxy == null) {
                if (data.responder != null) {
                    FaultResult faultResult = new FaultResult(String.valueOf(DBSErrorCode.ERR_NETWORK_NETWORKLAYER),
                            ErrorTitle.getErrorTitle(DBSErrorCode.ERR_NETWORK_NETWORKLAYER), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                    data.responder.fault(faultResult);
                }
                return;
            }

            try {
                method = proxy.getClass().getMethod("doBusiness", data.request.getClass(), IResponder.class, int.class, String.class);
            } catch (NoSuchMethodException e) {
                log.error("方法不存在");
                if (data.responder != null) {
                    FaultResult faultResult = new FaultResult(e.getMessage(), e.getMessage(), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                    data.responder.fault(faultResult);
                }
            }
            if (method == null) {
                return;
            }
            try {
                method.invoke(proxy, data.request, data.responder, data.timeOut, data.secretKey);
            } catch (IllegalAccessException e) {
                log.error("没有函数调用权限");
                if (data.responder != null) {
                    FaultResult faultResult = new FaultResult(e.getMessage(), e.getMessage(), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                    data.responder.fault(faultResult);
                }
            } catch (InvocationTargetException e) {
                Throwable target = ((InvocationTargetException) e).getTargetException();
                FaultResult faultResult;
                if (target instanceof DcdzSystemException) {
                    faultResult = new FaultResult(((DcdzSystemException) target).getErrorCode() + "",
                            ((DcdzSystemException) target).getErrorTitle(), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                } else {
                    faultResult = new FaultResult(target.getMessage(), target.getMessage(), FaultResult.FAULT_RESULT_TYPE_LOCAL);
                }

                if (data.responder != null) {
                    data.responder.fault(faultResult);
                }
            }
        }
    }

}
