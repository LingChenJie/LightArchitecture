package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.ResponseBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class NotifyCollectionResponse extends ResponseBean {

    private String msg;
    private String code;
    private Data data;

    public static class Data {
        private String VouchGUID;
        private String PosCode;

        public String getVouchGUID() {
            return VouchGUID;
        }

        public void setVouchGUID(String vouchGUID) {
            VouchGUID = vouchGUID;
        }

        public String getPosCode() {
            return PosCode;
        }

        public void setPosCode(String posCode) {
            PosCode = posCode;
        }
    }

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
