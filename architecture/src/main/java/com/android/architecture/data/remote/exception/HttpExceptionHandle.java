package com.android.architecture.data.remote.exception;

import android.text.TextUtils;

/**
 * Created by SuQi on 2022/9/25.
 * Describe:
 */
public class HttpExceptionHandle {

    /**
     * 对应HTTP的状态码
     */
    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int METHOD_NOT_ALLOWED = 405;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int CONFLICT = 409;
    private static final int PRECONDITION_FAILED = 412;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static HttpException handleException(int code, String result) {
        HttpException httpException = new HttpException(code);
        if (!TextUtils.isEmpty(result)) {
            httpException.setMessage(result);
        } else {
            httpException.setMessage("网络错误");
        }
        return httpException;
    }

}
