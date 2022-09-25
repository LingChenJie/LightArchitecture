package com.android.architecture.data.remote.exception;

import androidx.annotation.Nullable;

/**
 * Created by SuQi on 2022/9/25.
 * Describe:
 */
public class HttpException extends Exception {
    private int code;
    private String message = "网络错误";

    public HttpException(int code) {
        super();
        this.code = code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
