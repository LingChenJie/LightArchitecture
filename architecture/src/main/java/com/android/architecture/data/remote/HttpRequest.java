package com.android.architecture.data.remote;

import com.android.architecture.data.remote.https.HttpsUtils;
import com.android.architecture.data.remote.https.TrustAllHostname;
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
    private static final int CONNECT_TIME_OUT = 5;// 连接超时时间
    private static final int COMMUNICATION_TIME_OUT = 38;// 超时时间设置为38秒

    public static OkHttpClient getOkHttpClient(int timeout) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .addNetworkInterceptor(getLogInterceptor())
                .sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager)
                .hostnameVerifier(new TrustAllHostname())
                .build();
        return client;
    }

    /**
     * POST 请求，json方式
     *
     * @param url     请求地址
     * @param content 请求数据
     * @return 结果
     * @throws Exception
     */
    public static String postJson(String url, String content) throws Exception {
        return postJson(url, null, content, COMMUNICATION_TIME_OUT);
    }

    /**
     * POST 请求，json方式
     *
     * @param url     请求地址
     * @param content 请求数据
     * @param timeout 超时时间
     * @return 结果
     * @throws Exception
     */
    public static String postJson(String url, Map<String, String> headers, String content, int timeout) throws Exception {
        Logger.i(TAG, "PostJson start");
        //RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content);
        MediaType mediaType = MediaType.Companion.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.Companion.create(content, mediaType);
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        addHeader(requestBuilder, headers);
        Request request = requestBuilder.build();
        Logger.d(TAG, "Http Req --> " + url + content);
        Response response = getOkHttpClient(timeout).newCall(request).execute();
        Logger.i(TAG, "PostJson end");

        if (response.isSuccessful()) {
            String result = response.body().string();
            Logger.d(TAG, "Http Res --> " + result);
            return result;
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * POST 请求，表单方式
     *
     * @param url     请求地址
     * @param content 请求数据
     * @return 结果
     * @throws Exception
     */
    public static String postForm(String url, Map<String, String> content) throws Exception {
        return postForm(url, null, content, COMMUNICATION_TIME_OUT);
    }

    /**
     * POST 请求，表单方式
     *
     * @param url     请求地址
     * @param content 请求数据
     * @param timeout 超时时间
     * @return 结果
     * @throws Exception
     */
    public static String postForm(String url, Map<String, String> headers, Map<String, String> content, int timeout) throws Exception {
        Logger.i(TAG, "PostForm start");
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Set<Map.Entry<String, String>> entries = content.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getValue() != null) {
                bodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = bodyBuilder.build();
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        addHeader(requestBuilder, headers);
        Request request = requestBuilder.build();
        Logger.d(TAG, "Http Req --> " + url + content);
        Response response = getOkHttpClient(timeout).newCall(request).execute();
        Logger.i(TAG, "PostForm end");

        if (response.isSuccessful()) {
            String result = response.body().string();
            Logger.d(TAG, "Http Res --> " + result);
            return result;
        } else {
            throw new IOException("Unexpected code " + response);
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
        HttpLoggingInterceptor sLogInterceptor = new HttpLoggingInterceptor();
        sLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return sLogInterceptor;
    }

}
