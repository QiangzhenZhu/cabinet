package com.hzdongcheng.bll.utils;

import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

import okhttp3.CertificatePinner;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {
    private static final Charset charset = Charset.forName("utf8");
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
           .build();

    private static String executePost(String url, RequestBody requestBody) throws DcdzSystemException {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return Objects.requireNonNull(response.body()).string();
            }
            throw new DcdzSystemException(DBSErrorCode.ERR_NETWORK_SENDMSGFAIL, response.code() + response.message());
        } catch (IOException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_NETWORK_NETWORKLAYER, e.getMessage());
        }
    }

    public static String postJSON(String url, String json) throws DcdzSystemException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        return executePost(url, requestBody);
    }

    public static String get(String url) throws DcdzSystemException {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
            throw new DcdzSystemException(DBSErrorCode.ERR_NETWORK_NETWORKLAYER);
        } catch (IOException e) {
            throw new DcdzSystemException(DBSErrorCode.ERR_NETWORK_NETWORKLAYER);
        }
    }


    public static String post(String url, String content, String terminalNo, String action) throws DcdzSystemException {
        if(StringUtils.isEmpty(action)){
            action = "";
        }
        RequestBody requestBody = new FormBody.Builder(charset)
                .add("TERMINALNO", terminalNo)
                .add("RECDATA", content)
                .add("ACTION", action)
                .build();
        return executePost(url, requestBody);
    }
}
