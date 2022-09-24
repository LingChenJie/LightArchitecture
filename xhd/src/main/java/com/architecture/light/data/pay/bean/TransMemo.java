package com.architecture.light.data.pay.bean;

import com.architecture.light.data.bean.BaseBean;

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/19/22
 * Modify date: 9/19/22
 * Version: 1
 */
public class TransMemo extends BaseBean {

    private String appName;
    private String transId;
    private String resultCode;
    private String resultMsg;
    private PayData transData;

    public static class PayData extends BaseBean {
        private String resCode;
        private String resDesc;
        private String orderStatus;
        private String extBillNo;
        private String extOrderNo;
        private String cardType;
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
        private String cardIssuerId;
        private String cardAcquirerId;
        private String cardInputType;
        private String transName;
        private String transChnName;
        private String transEngName;
        private String settleDate;
        private String date;
        private String time;
        private String memInfo;
        private Boolean isReprint;
        private String vendor;
        private String cardOrg;
        private String serviceNo;
        private String model;
        private String version;
        private String ARQC;
        private String UnprNo;
        private String ATC;
        private String TVR;
        private String TSI;
        private String AID;
        private String AIP;
        private String APPLAB;
        private String APPNAME;
        private String CVM;
        private String TermCap;
        private String IAD;
        private String CSN;
        private String CPI;
        private String payCodeNo;
        private String payVoucherCode;
        private String chnSpecInfo;
        private String bCardType;
        private String settleMer;
        private String settleTer;
        private String consumePlatFlow;
        private String isRealAuth;
        private String settleMerName;

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

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getExtBillNo() {
            return extBillNo;
        }

        public void setExtBillNo(String extBillNo) {
            this.extBillNo = extBillNo;
        }

        public String getExtOrderNo() {
            return extOrderNo;
        }

        public void setExtOrderNo(String extOrderNo) {
            this.extOrderNo = extOrderNo;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
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

        public String getCardIssuerId() {
            return cardIssuerId;
        }

        public void setCardIssuerId(String cardIssuerId) {
            this.cardIssuerId = cardIssuerId;
        }

        public String getCardAcquirerId() {
            return cardAcquirerId;
        }

        public void setCardAcquirerId(String cardAcquirerId) {
            this.cardAcquirerId = cardAcquirerId;
        }

        public String getCardInputType() {
            return cardInputType;
        }

        public void setCardInputType(String cardInputType) {
            this.cardInputType = cardInputType;
        }

        public String getTransName() {
            return transName;
        }

        public void setTransName(String transName) {
            this.transName = transName;
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

        public String getSettleDate() {
            return settleDate;
        }

        public void setSettleDate(String settleDate) {
            this.settleDate = settleDate;
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

        public Boolean getReprint() {
            return isReprint;
        }

        public void setReprint(Boolean reprint) {
            isReprint = reprint;
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

        public String getARQC() {
            return ARQC;
        }

        public void setARQC(String ARQC) {
            this.ARQC = ARQC;
        }

        public String getUnprNo() {
            return UnprNo;
        }

        public void setUnprNo(String unprNo) {
            UnprNo = unprNo;
        }

        public String getATC() {
            return ATC;
        }

        public void setATC(String ATC) {
            this.ATC = ATC;
        }

        public String getTVR() {
            return TVR;
        }

        public void setTVR(String TVR) {
            this.TVR = TVR;
        }

        public String getTSI() {
            return TSI;
        }

        public void setTSI(String TSI) {
            this.TSI = TSI;
        }

        public String getAID() {
            return AID;
        }

        public void setAID(String AID) {
            this.AID = AID;
        }

        public String getAIP() {
            return AIP;
        }

        public void setAIP(String AIP) {
            this.AIP = AIP;
        }

        public String getAPPLAB() {
            return APPLAB;
        }

        public void setAPPLAB(String APPLAB) {
            this.APPLAB = APPLAB;
        }

        public String getAPPNAME() {
            return APPNAME;
        }

        public void setAPPNAME(String APPNAME) {
            this.APPNAME = APPNAME;
        }

        public String getCVM() {
            return CVM;
        }

        public void setCVM(String CVM) {
            this.CVM = CVM;
        }

        public String getTermCap() {
            return TermCap;
        }

        public void setTermCap(String termCap) {
            TermCap = termCap;
        }

        public String getIAD() {
            return IAD;
        }

        public void setIAD(String IAD) {
            this.IAD = IAD;
        }

        public String getCSN() {
            return CSN;
        }

        public void setCSN(String CSN) {
            this.CSN = CSN;
        }

        public String getCPI() {
            return CPI;
        }

        public void setCPI(String CPI) {
            this.CPI = CPI;
        }

        public String getPayCodeNo() {
            return payCodeNo;
        }

        public void setPayCodeNo(String payCodeNo) {
            this.payCodeNo = payCodeNo;
        }

        public String getPayVoucherCode() {
            return payVoucherCode;
        }

        public void setPayVoucherCode(String payVoucherCode) {
            this.payVoucherCode = payVoucherCode;
        }

        public String getChnSpecInfo() {
            return chnSpecInfo;
        }

        public void setChnSpecInfo(String chnSpecInfo) {
            this.chnSpecInfo = chnSpecInfo;
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

        public String getConsumePlatFlow() {
            return consumePlatFlow;
        }

        public void setConsumePlatFlow(String consumePlatFlow) {
            this.consumePlatFlow = consumePlatFlow;
        }

        public String getIsRealAuth() {
            return isRealAuth;
        }

        public void setIsRealAuth(String isRealAuth) {
            this.isRealAuth = isRealAuth;
        }

        public String getSettleMerName() {
            return settleMerName;
        }

        public void setSettleMerName(String settleMerName) {
            this.settleMerName = settleMerName;
        }
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

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
}
