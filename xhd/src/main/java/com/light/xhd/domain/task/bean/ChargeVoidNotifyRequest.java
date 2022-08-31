package com.light.xhd.domain.task.bean;

import com.light.xhd.domain.task.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class ChargeVoidNotifyRequest extends RequestBean {
    private String contractNo;
    private String payTypeNum;
    private String tradeNo;
    private String tradingDate;
    private String bankId;
    private String bankName;
    private String batchNo;
    private String paySno;
    private String refNo;
    private String orderNo;
    private String operatorId;

    public ChargeVoidNotifyRequest() {
        setInterfaceId("charge_off");
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getPayTypeNum() {
        return payTypeNum;
    }

    public void setPayTypeNum(String payTypeNum) {
        this.payTypeNum = payTypeNum;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getPaySno() {
        return paySno;
    }

    public void setPaySno(String paySno) {
        this.paySno = paySno;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}

