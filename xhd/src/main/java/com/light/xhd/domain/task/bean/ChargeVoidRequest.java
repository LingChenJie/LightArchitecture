package com.light.xhd.domain.task.bean;

import com.light.xhd.domain.task.bean.base.RequestBean;

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

