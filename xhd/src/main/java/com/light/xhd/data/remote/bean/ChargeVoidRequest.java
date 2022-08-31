package com.light.xhd.data.remote.bean;

import com.light.xhd.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class ChargeVoidRequest extends RequestBean {
    private String SerialNumber;

    public ChargeVoidRequest() {
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

