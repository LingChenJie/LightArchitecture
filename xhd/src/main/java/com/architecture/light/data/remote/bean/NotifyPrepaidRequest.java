package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.RequestBean;

import java.util.List;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class NotifyPrepaidRequest extends RequestBean {
    private String PosNO;
    private String SerialNumber;
    private String ProjGUID;
    private String BookingGUID;
    private String LyrCode;
    private String SkDate;
    private String kpr;
    private String Jkr;
    private List<Getin> GetinList;
    private String Remark;

    public NotifyPrepaidRequest() {
        setInterfaceId("charge");
        setTradeType("1");
    }

    public static class Getin {
        private String ItemNameGUID;
        private String ItemName;
        private String Amount;
        private String FeeGUID;
        private String PosCode;
        private String PosAmount;
        private String RzBank;

        public String getItemNameGUID() {
            return ItemNameGUID;
        }

        public void setItemNameGUID(String itemNameGUID) {
            ItemNameGUID = itemNameGUID;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String itemName) {
            ItemName = itemName;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getFeeGUID() {
            return FeeGUID;
        }

        public void setFeeGUID(String feeGUID) {
            FeeGUID = feeGUID;
        }

        public String getPosCode() {
            return PosCode;
        }

        public void setPosCode(String posCode) {
            PosCode = posCode;
        }

        public String getPosAmount() {
            return PosAmount;
        }

        public void setPosAmount(String posAmount) {
            PosAmount = posAmount;
        }

        public String getRzBank() {
            return RzBank;
        }

        public void setRzBank(String rzBank) {
            RzBank = rzBank;
        }
    }

    public String getPosNO() {
        return PosNO;
    }

    public void setPosNO(String posNO) {
        PosNO = posNO;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getProjGUID() {
        return ProjGUID;
    }

    public void setProjGUID(String projGUID) {
        ProjGUID = projGUID;
    }

    public String getBookingGUID() {
        return BookingGUID;
    }

    public void setBookingGUID(String bookingGUID) {
        BookingGUID = bookingGUID;
    }

    public String getLyrCode() {
        return LyrCode;
    }

    public void setLyrCode(String lyrCode) {
        LyrCode = lyrCode;
    }

    public String getSkDate() {
        return SkDate;
    }

    public void setSkDate(String skDate) {
        SkDate = skDate;
    }

    public String getKpr() {
        return kpr;
    }

    public void setKpr(String kpr) {
        this.kpr = kpr;
    }

    public String getJkr() {
        return Jkr;
    }

    public void setJkr(String jkr) {
        Jkr = jkr;
    }

    public List<Getin> getGetinList() {
        return GetinList;
    }

    public void setGetinList(List<Getin> getinList) {
        GetinList = getinList;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}

