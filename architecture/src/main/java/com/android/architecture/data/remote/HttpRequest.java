package com.android.architecture.data.remote;

import static com.android.architecture.data.remote.exception.HttpExceptionHandle.handleException;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.architecture.data.remote.ssl.SSLUtils;
import com.android.architecture.data.remote.ssl.TrustAllHostname;
import com.android.architecture.helper.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by SuQi on 2022/8/29.
 * Describe:
 */
public class HttpRequest {

    private static final String TAG = HttpRequest.class.getSimpleName();
    private int timeout = 38;// 超时时间
    private String url;// 通讯地址
    private final OkHttpClient okHttpClient;

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpRequest() {
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .addNetworkInterceptor(getLogInterceptor())
                .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new TrustAllHostname())
                .build();
    }

    public HttpRequest(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * GET 请求
     *
     * @return 结果
     * @throws Exception
     */
    public String get() throws Exception {
        return get(null);
    }

    /**
     * GET 请求
     *
     * @param headers 报文头
     * @return 结果
     * @throws Exception
     */
    public String get(Map<String, String> headers) throws Exception {
        if (url == null) {
            throw new RuntimeException("url cannot be null");
        }
        Logger.i(TAG, "Get start");
        Request.Builder requestBuilder = new Request.Builder().url(url).get();
        addHeader(requestBuilder, headers);
        Request request = requestBuilder.build();
        Logger.d(TAG, "Get Req --> url:" + url);
        Response response = okHttpClient.newCall(request).execute();
        Logger.i(TAG, "Get end");

        if (response.isSuccessful()) {
            String result = response.body().string();
            Logger.d(TAG, "Get Res --> " + result);
            return result;
        } else {
            throw new IOException("Get code " + response);
        }
    }

    /**
     * POST 请求，json方式
     *
     * @param jsonStr 请求数据
     * @return 结果
     * @throws Exception
     */
    public String postJson(String jsonStr) throws Exception {
        return postJson(null, jsonStr);
    }

    /**
     * POST 请求，json方式
     *
     * @param headers 报文头
     * @param jsonStr 请求数据
     * @return 结果
     * @throws Exception
     */
    public String postJson(Map<String, String> headers, String jsonStr) throws Exception {
        if (url == null) {
            throw new RuntimeException("url cannot be null");
        }
        Logger.i(TAG, "PostJson start");
        //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        MediaType mediaType = MediaType.Companion.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.Companion.create(jsonStr, mediaType);
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        addHeader(requestBuilder, headers);
        Request request = requestBuilder.build();
        Logger.d(TAG, "Http Req --> url:" + url + "; jsonStr:" + jsonStr);
        Response response = okHttpClient.newCall(request).execute();
        Logger.i(TAG, "PostJson end");

        if (response.isSuccessful()) {
            String result = response.body().string();
            Logger.d(TAG, "Http Res --> " + result);
            return result;
        } else {
            int code = response.code();
            String result = null;
            try {
                result = response.body().string();
                Logger.d(TAG, "Http Res --> code:" + code + "; result:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw handleException(code, result);
        }
    }

    /**
     * POST 请求，表单方式
     *
     * @param params 请求数据
     * @return 结果
     * @throws Exception
     */
    public String postForm(Map<String, String> params) throws Exception {
        return postForm(null, params);
    }

    /**
     * POST 请求，表单方式
     *
     * @param params 请求数据
     * @return 结果
     * @throws Exception
     */
    public String postForm(Map<String, String> headers, Map<String, String> params) throws Exception {
        if (url == null) {
            throw new RuntimeException("url cannot be null");
        }
        Logger.i(TAG, "PostForm start");
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getValue() != null) {
                bodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = bodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        addHeader(requestBuilder, headers);
        Request request = requestBuilder.build();
        Logger.d(TAG, "Http Req --> url:" + url);
        Response response = okHttpClient.newCall(request).execute();
        Logger.i(TAG, "PostForm end");

        if (response.isSuccessful()) {
            String result = response.body().string();
            Logger.d(TAG, "Http Res --> " + result);
            return result;
        } else {
            int code = response.code();
            String result = null;
            try {
                result = response.body().string();
                Logger.d(TAG, "Http Res --> code:" + code + "; result:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw handleException(code, result);
        }
    }

    private static void addHeader(Request.Builder requestBuilder, Map<String, String> headers) {
        if (headers != null && headers.size() > 0) {
            Set<Map.Entry<String, String>> entries = headers.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if (entry.getValue() != null) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor sLogInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        sLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return sLogInterceptor;
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(@NonNull String message) {
            Log.i(TAG, message);
        }
    }

}
