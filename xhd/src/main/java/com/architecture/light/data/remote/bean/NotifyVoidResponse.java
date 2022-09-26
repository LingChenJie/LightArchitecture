package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.ResponseBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class NotifyVoidResponse extends ResponseBean {

    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
