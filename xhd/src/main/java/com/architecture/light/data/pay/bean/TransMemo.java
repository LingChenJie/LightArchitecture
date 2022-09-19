package com.architecture.light.data.pay.bean;

import com.architecture.light.data.remote.bean.base.BaseBean;

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/19/22
 * Modify date: 9/19/22
 * Version: 1
 */
public class TransMemo extends BaseBean {
    private String resultCode;
    private String resultMsg;
    private PayData transData;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public PayData getTransData() {
        return transData;
    }

    public void setTransData(PayData transData) {
        this.transData = transData;
    }

    public static class PayData extends BaseBean {
        private String resCode;
        private String resDesc;
        private String exOrderNo;
        private String cardType;

        private String bCardType;
        private String settleMer;
        private String settleTer;
        private String confirmInfo;
        private String isRealAuth;
        private String userNo;
        private String proName;
        private String clientName;
        private String houseSource;
        private String fundName;
        private String fundDate;

        private String consumePlatFlow;
        private String merchantName;
        private String merchantNo;
        private String terminalNo;
        private String operNo;
        private String amt;
        private String batchNo;
        private String traceNo;
        private String refNo;
        private String authNo;
        private String expDate;
        private String cardNo;
        private String cardIssuerCode;
        private String cardAcquirerCode;
        private String cardInputType;
        private String transChnName;
        private String transEngName;
        private String date;
        private String time;
        private String memInfo;
        private String isReprint;
        private String vendor;
        private String cardOrg;
        private String serviceNo;
        private String model;
        private String version;
        private String qrcode;
        private String eSignJpeg;

        public String getResCode() {
            return resCode;
        }

        public void setResCode(String resCode) {
            this.resCode = resCode;
        }

        public String getResDesc() {
            return resDesc;
        }

        public void setResDesc(String resDesc) {
            this.resDesc = resDesc;
        }

        public String getExOrderNo() {
            return exOrderNo;
        }

        public void setExOrderNo(String exOrderNo) {
            this.exOrderNo = exOrderNo;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }

        public String getbCardType() {
            return bCardType;
        }

        public void setbCardType(String bCardType) {
            this.bCardType = bCardType;
        }

        public String getSettleMer() {
            return settleMer;
        }

        public void setSettleMer(String settleMer) {
            this.settleMer = settleMer;
        }

        public String getSettleTer() {
            return settleTer;
        }

        public void setSettleTer(String settleTer) {
            this.settleTer = settleTer;
        }

        public String getConfirmInfo() {
            return confirmInfo;
        }

        public void setConfirmInfo(String confirmInfo) {
            this.confirmInfo = confirmInfo;
        }

        public String getIsRealAuth() {
            return isRealAuth;
        }

        public void setIsRealAuth(String isRealAuth) {
            this.isRealAuth = isRealAuth;
        }

        public String getUserNo() {
            return userNo;
        }

        public void setUserNo(String userNo) {
            this.userNo = userNo;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getHouseSource() {
            return houseSource;
        }

        public void setHouseSource(String houseSource) {
            this.houseSource = houseSource;
        }

        public String getFundName() {
            return fundName;
        }

        public void setFundName(String fundName) {
            this.fundName = fundName;
        }

        public String getFundDate() {
            return fundDate;
        }

        public void setFundDate(String fundDate) {
            this.fundDate = fundDate;
        }

        public String getConsumePlatFlow() {
            return consumePlatFlow;
        }

        public void setConsumePlatFlow(String consumePlatFlow) {
            this.consumePlatFlow = consumePlatFlow;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getTerminalNo() {
            return terminalNo;
        }

        public void setTerminalNo(String terminalNo) {
            this.terminalNo = terminalNo;
        }

        public String getOperNo() {
            return operNo;
        }

        public void setOperNo(String operNo) {
            this.operNo = operNo;
        }

        public String getAmt() {
            return amt;
        }

        public void setAmt(String amt) {
            this.amt = amt;
        }

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getTraceNo() {
            return traceNo;
        }

        public void setTraceNo(String traceNo) {
            this.traceNo = traceNo;
        }

        public String getRefNo() {
            return refNo;
        }

        public void setRefNo(String refNo) {
            this.refNo = refNo;
        }

        public String getAuthNo() {
            return authNo;
        }

        public void setAuthNo(String authNo) {
            this.authNo = authNo;
        }

        public String getExpDate() {
            return expDate;
        }

        public void setExpDate(String expDate) {
            this.expDate = expDate;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getCardIssuerCode() {
            return cardIssuerCode;
        }

        public void setCardIssuerCode(String cardIssuerCode) {
            this.cardIssuerCode = cardIssuerCode;
        }

        public String getCardAcquirerCode() {
            return cardAcquirerCode;
        }

        public void setCardAcquirerCode(String cardAcquirerCode) {
            this.cardAcquirerCode = cardAcquirerCode;
        }

        public String getCardInputType() {
            return cardInputType;
        }

        public void setCardInputType(String cardInputType) {
            this.cardInputType = cardInputType;
        }

        public String getTransChnName() {
            return transChnName;
        }

        public void setTransChnName(String transChnName) {
            this.transChnName = transChnName;
        }

        public String getTransEngName() {
            return transEngName;
        }

        public void setTransEngName(String transEngName) {
            this.transEngName = transEngName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMemInfo() {
            return memInfo;
        }

        public void setMemInfo(String memInfo) {
            this.memInfo = memInfo;
        }

        public String getIsReprint() {
            return isReprint;
        }

        public void setIsReprint(String isReprint) {
            this.isReprint = isReprint;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getCardOrg() {
            return cardOrg;
        }

        public void setCardOrg(String cardOrg) {
            this.cardOrg = cardOrg;
        }

        public String getServiceNo() {
            return serviceNo;
        }

        public void setServiceNo(String serviceNo) {
            this.serviceNo = serviceNo;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String geteSignJpeg() {
            return eSignJpeg;
        }

        public void seteSignJpeg(String eSignJpeg) {
            this.eSignJpeg = eSignJpeg;
        }
    }
}
