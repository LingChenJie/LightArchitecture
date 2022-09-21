package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class NotifyVoidRequest extends RequestBean {
    private String SerialNumber;

    public NotifyVoidRequest() {
        setInterfaceId("charge");
        setTradeType("4");
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }
}

