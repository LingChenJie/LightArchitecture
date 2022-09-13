package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.BaseBean;
import com.architecture.light.data.remote.bean.base.ResponseBean;

import java.util.List;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class SearchBillResponse extends ResponseBean {

    private String msg;
    private String code;
    private List<DataBean> data;

    public static class DataBean extends BaseBean {
        private String ProjName = "";
        private String AmountString = "";
        private String ProjStagesName = "";
        private String RoomName = "";
        private String ProjShortName = "";
        private String Amount = "";
        private String RoomInfo = "";
        private String CstName = "";
        private String Kpr = "";
        private String Jkr = "";
        private String KpDate = "";
        private String PayMode = "";
        private String PayRemark = "";
        private String PayNo = "";
        private String printNum = "";
        private String customRemark = "";
        private boolean isChecked;

        public String getProjName() {
            return ProjName;
        }

        public void setProjName(String projName) {
            ProjName = projName;
        }

        public String getAmountString() {
            return AmountString;
        }

        public void setAmountString(String amountString) {
            AmountString = amountString;
        }

        public String getProjStagesName() {
            return ProjStagesName;
        }

        public void setProjStagesName(String projStagesName) {
            ProjStagesName = projStagesName;
        }

        public String getRoomName() {
            return RoomName;
        }

        public void setRoomName(String roomName) {
            RoomName = roomName;
        }

        public String getProjShortName() {
            return ProjShortName;
        }

        public void setProjShortName(String projShortName) {
            ProjShortName = projShortName;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getRoomInfo() {
            return RoomInfo;
        }

        public void setRoomInfo(String roomInfo) {
            RoomInfo = roomInfo;
        }

        public String getCstName() {
            return CstName;
        }

        public void setCstName(String cstName) {
            CstName = cstName;
        }

        public String getKpr() {
            return Kpr;
        }

        public void setKpr(String kpr) {
            Kpr = kpr;
        }

        public String getJkr() {
            return Jkr;
        }

        public void setJkr(String jkr) {
            Jkr = jkr;
        }

        public String getKpDate() {
            return KpDate;
        }

        public void setKpDate(String kpDate) {
            KpDate = kpDate;
        }

        public String getPayMode() {
            return PayMode;
        }

        public void setPayMode(String payMode) {
            PayMode = payMode;
        }

        public String getPayRemark() {
            return PayRemark;
        }

        public void setPayRemark(String payRemark) {
            PayRemark = payRemark;
        }

        public String getPayNo() {
            return PayNo;
        }

        public void setPayNo(String payNo) {
            PayNo = payNo;
        }

        public String getPrintNum() {
            return printNum;
        }

        public void setPrintNum(String printNum) {
            this.printNum = printNum;
        }

        public String getCustomRemark() {
            return customRemark;
        }

        public void setCustomRemark(String customRemark) {
            this.customRemark = customRemark;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
