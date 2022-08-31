package com.light.xhd.data.remote.bean.base;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class RequestBean {
    private String ip;
    private String termSn;
    private String sysType;
    private String interfaceId;
    private String tradeType;

    public RequestBean() {
        //TODO
        setTermSn("1234567890");
        setSysType("17");
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTermSn() {
        return termSn;
    }

    public void setTermSn(String termSn) {
        this.termSn = termSn;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
