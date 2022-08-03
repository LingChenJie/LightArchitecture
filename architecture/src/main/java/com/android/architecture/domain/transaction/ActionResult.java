package com.android.architecture.domain.transaction;

import java.io.Serializable;

/**
 * Describe:交易操作步骤的结果
 */
public class ActionResult implements Serializable {
    /**
     * 返回结果码
     */
    String code;
    /**
     * 返回结果信息
     */
    String message;
    /**
     * 返回数据
     */
    Object data;

    public ActionResult(String code) {
        this.code = code;
    }

    public ActionResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ActionResult(String code, Object data) {
        this.code = code;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
