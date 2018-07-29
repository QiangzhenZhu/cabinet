package com.hzdongcheng.bll.websocket;


import android.os.Handler;

import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.common.FaultResult;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.common.IResponder;
import com.hzdongcheng.bll.common.JsonPacket;
import com.hzdongcheng.bll.common.JsonResult;
import com.hzdongcheng.bll.common.PacketUtils;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.exception.error.ErrorTitle;
import com.hzdongcheng.components.toolkits.utils.Log4jUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClient {
    private static Log4jUtils log = Log4jUtils.createInstanse(SocketClient.class);
    private static WebSocketClient webSocketClient;
    private final static Map<String, IResponder> m_CallBackDict = new HashMap<String, IResponder>();
    private static boolean connected = false;

    public static synchronized void start() {
        if (connected) {
            return;
        }
        String host = String.format("ws://%s", DBSContext.monHost.trim());
        String url = String.format("%s:%s/dbscomm/%s", host, DBSContext.monPort,
                DBSContext.terminalUid);
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            log.error("[WebSocket] 服务器地址格式错误" + e.getMessage());
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                log.error("[WebSocket] 网络连接成功");
                connected = true;
                connectCount = 0;
                if (stateObserver != null) {
                    stateObserver.update(null, true);
                }
                DBSContext.deviceSign();
            }

            @Override
            public void onMessage(String message) {
                messageHandler(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                log.error("[WebSocket] 网络连接断开 -->" + reason + ",code=" + code);
                connected = false;
                if (stateObserver != null) {
                    stateObserver.update(null, false);
                }
                restart();
            }

            @Override
            public void onError(Exception ex) {
                log.error("[WebSocket] 网络错误 -->" + ex.getMessage());
            }
        };

        webSocketClient.connect();
    }

    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static int connectCount = 0;

    private synchronized static void restart() {
        if (connected) {
            return;
        }
        connectCount = connectCount < 36 ? connectCount + 1 : 36;
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                log.error("[WebSocket] 重新连接服务器");
                start();
            }
        }, connectCount * 10, TimeUnit.SECONDS);

    }

    /**
     * 断开网络连接
     */
    public static void stop() {
        if (webSocketClient != null) {
            webSocketClient.close();
            webSocketClient = null;
        }
    }

    /**
     * 处理收到的网络数据
     *
     * @param message 网络数据
     */
    private static void messageHandler(String message) {
        JsonPacket pack = PacketUtils.unPack(message);
        if (pack != null) {
            if (pack._CmdType == JsonPacket.MSG_SENT_BY_CLIENT) // 客户端应答消息
            {
                log.info("[WebSocket] 收到远程应答命令 -->" + message);
                IResponder responder;
                if (m_CallBackDict.containsKey(pack._MessageId)
                        && m_CallBackDict.get(pack._MessageId) != null) {
                    responder = m_CallBackDict.get(pack._MessageId);
                    // 移除回调
                    synchronized (m_CallBackDict) {
                        m_CallBackDict.remove(pack._MessageId);
                    }
                    synchronized (responder.MessageReceiveEvent) {
                        responder.MessageReceiveEvent.notify();
                    }
                    // 调用回调
                    JsonResult jsonResult = PacketUtils
                            .deserializeJsonResult(pack.body);

                    if (jsonResult != null) {
                        if (jsonResult.success) {
                            try {
                                responder.result(jsonResult);
                            } catch (Exception ex) {
                                log.error("[WebSocket]: 数据已收到，业务处理报错 >>"
                                        + ex.getCause()
                                        + ":"
                                        + ex.getMessage());
                            }
                        } else {
                            FaultResult faultResult = new FaultResult(
                                    jsonResult.msg, jsonResult.msg,
                                    FaultResult.FAULT_RESULT_TYPE_REMOTE);
                            try {
                                responder.fault(faultResult);
                            } catch (Exception ex) {
                                log.error("[WebSocket]:数据已收到，业务处理报错 >>"
                                        + ex.getCause()
                                        + ":"
                                        + ex.getMessage());
                            }
                        }
                    } else {
                        log.error("[WebSocket] 不能解析收到的数据 -->" + message);
                    }
                }
            } else if (pack._CmdType == JsonPacket.MSG_SENT_BY_SERVER) // 服务端推送消息
            {
                log.info("[WebSocket] 收到远程业务命令 -->" + message);
                // 调用事件处理函数
                try {
                    if ("InParamAPStoreInOpenBox".equals(pack._ServiceName)
                            || "InParamAPTakeOutOpenBox".equals(pack._ServiceName)) {
                        Future<?> submit = executorServicePack.submit(new invokeCallBack(pack));
                        Object objects = submit.get(2, TimeUnit.SECONDS);
                        if ((Boolean) objects) {
                            // 返回成功处理消息
                            String returnMsg = PacketUtils.buildRemoteJsonResult(pack,
                                    true, "");
                            webSocketClient.send(returnMsg);
                        } else {
                            String returnMsg = PacketUtils.buildRemoteJsonResult(
                                    pack, false, objects.toString());
                            webSocketClient.send(returnMsg);
                        }

                    } else {
                        invoke(pack);
                        String returnMsg = PacketUtils.buildRemoteJsonResult(pack,
                                true, "");
                        webSocketClient.send(returnMsg);
                    }
                } catch (Exception ex) {
                    // 返回失败处理消息
                    log.error("[WebSocket] 应答服务器推送消息出错 >>" + ex.getMessage());
                    try {
                        String returnMsg = PacketUtils.buildRemoteJsonResult(
                                pack, false, ex.getMessage());
                        webSocketClient.send(returnMsg);
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }

    private static Observer stateObserver;
    private static PredicateServiceType invokeCallback;

    public interface PredicateServiceType {
        Class<?> predicate(String serviceName);
    }

    public static void setOnStateChanged(Observer observer) {
        stateObserver = observer;
    }

    public static void setOnInvokeCallback(PredicateServiceType callback) {
        invokeCallback = callback;
    }

    private static ExecutorService executorServicePack = Executors.newScheduledThreadPool(5);

    static class invokeCallBack implements Callable {

        private JsonPacket packet;

        private invokeCallBack(JsonPacket packet) {
            this.packet = packet;
        }

        @Override
        public Object call() {
            try {
                invoke(packet);
                return true;
            } catch (DcdzSystemException e) {
                return e.getMessage();
            }

        }
    }

    /**
     * 根据推送消息调用业务方法
     *
     * @param packet
     */
    private static void invoke(JsonPacket packet) throws DcdzSystemException {
        String serviceName = "com.hzdongcheng.bll.basic.dto." + packet._ServiceName;
        Class<?> aClass = null;
        try {
            aClass = Class.forName(serviceName);
        } catch (ClassNotFoundException e) {
            if (invokeCallback != null) {
                aClass = invokeCallback.predicate(packet._ServiceName);
            }
            if (aClass == null)
                return;
        }
        try {
            Object inParam = PacketUtils.deserializeObject(packet.body, aClass);
            Method method = DBSContext.localContext.getClass().getMethod("doBusiness", aClass);
            if (method != null) {
                method.invoke(DBSContext.localContext, inParam);
            } else {
                log.info("[WebSocket] 没有找到对应的方法");
            }
        } catch (InvocationTargetException e) {
            log.error("[WebSocket] 业务报错 >>" + e.getMessage());

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException e) {
            log.error("[WebSocket] 远程调用本地终端不支持的命令 >>" + e.getMessage());
            throw new DcdzSystemException(DBSErrorCode.ERR_BUSINESS_UNSUPPORT, e.getMessage());
        }
    }

    public static void sendData(IRequest request, IResponder responder, String messageId, String secretKey) {
        // 连接不成功，直接返回
        if (webSocketClient == null || !connected) {
            FaultResult faultResult = new FaultResult(
                    ErrorTitle.getErrorTitle(DBSErrorCode.ERR_NETWORK_OPENLINK), "",
                    FaultResult.FAULT_RESULT_TYPE_REMOTE);
            responder.fault(faultResult);
            return;
        }

        JsonPacket jsonPacket = PacketUtils.CreateRequestPacket(request,
                DBSContext.terminalUid, messageId);

        jsonPacket.body = PacketUtils.serializeObject(request);
        jsonPacket._Sign = secretKey; // md5(m_sSecretKey + body)

        String content = PacketUtils.serializeObject(jsonPacket);

        if (responder != null) {
            m_CallBackDict.put(jsonPacket._MessageId, responder);
        }
        try {
            webSocketClient.send(content);
        } catch (Exception e) {
            log.error("[WebSocket] 发送数据失败 " + e.getMessage());
            synchronized (m_CallBackDict) {
                m_CallBackDict.remove(jsonPacket._MessageId);
            }

        }

        // 超时处理
        if (responder != null) {
            long start = System.currentTimeMillis();
            int timeout = 10 * 1000;
            try {
                synchronized (responder.MessageReceiveEvent) {
                    responder.MessageReceiveEvent.wait(timeout);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            if ((end - start) > timeout) {
                // 移除回调
                synchronized (m_CallBackDict) {
                    m_CallBackDict.remove(jsonPacket._MessageId);
                }
                FaultResult faultResult = new FaultResult(
                        ErrorTitle.getErrorTitle(DBSErrorCode.ERR_NETWORK_OPENLINK), "",
                        FaultResult.FAULT_RESULT_TYPE_REMOTE);
                responder.fault(faultResult);
            }
        }

    }
}
