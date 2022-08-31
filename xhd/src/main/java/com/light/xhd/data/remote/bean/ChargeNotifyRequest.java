package com.light.xhd.data.remote.bean;

import com.light.xhd.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class ChargeNotifyRequest extends RequestBean {
    private String contractNo;
    private String amount;
    private String paySno;
    private String tradingDate;
    private String name;
    private String merchantNo;
    private String chargeOffType;

    public ChargeNotifyRequest() {
        setInterfaceId("charge_off");
        setChargeOffType("3");
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaySno() {
        return paySno;
    }

    public void setPaySno(String paySno) {
        this.paySno = paySno;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getChargeOffType() {
        return chargeOffType;
    }

    public void setChargeOffType(String chargeOffType) {
        this.chargeOffType = chargeOffType;
    }
}

