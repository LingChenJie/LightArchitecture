package com.android.architecture.data.response;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/28
 * Modify date: 2022/7/28
 * Version: 1
 */
public class DataResult<T> {

    private String code;
    private String message;
    private T data;

    public DataResult(T entity) {
        this.data = entity;
        this.code = ResultCode.SUCCESS;
        this.message = ResultCode.getMessage(code);
    }

    public DataResult(String code) {
        this.code = code;
        this.message = ResultCode.getMessage(code);
    }

    public DataResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public interface Result<T> {
        void onResult(DataResult<T> dataResult);
    }

}
