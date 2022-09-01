package com.architecture.light.data.remote.bean.base;

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
public class ResponseData {
    private String code;
    private String msg;
    private ResponseBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseBean getData() {
        return data;
    }

    public void setData(ResponseBean data) {
        this.data = data;
    }
}
