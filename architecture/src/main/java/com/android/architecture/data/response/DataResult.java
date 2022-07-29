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
    private T entity;

    public DataResult(T entity) {
        this.entity = entity;
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

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

}
