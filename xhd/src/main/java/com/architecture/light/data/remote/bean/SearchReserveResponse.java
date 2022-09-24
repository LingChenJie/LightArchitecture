package com.architecture.light.data.remote.bean;

import com.architecture.light.data.bean.BaseBean;
import com.architecture.light.data.remote.bean.base.ResponseBean;

import java.util.List;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class SearchReserveResponse extends ResponseBean {

    private String msg;
    private String code;
    private List<Data> data;

    public static class Data extends BaseBean {
        private String ProjNum;
        private String ProjGUID;
        private String CstName;
        private String Tel;
        private String BookingGUID;
        private String CardID;
        private String CstGUID;
        private boolean isChecked;

        public String getProjNum() {
            return ProjNum;
        }

        public void setProjNum(String projNum) {
            ProjNum = projNum;
        }

        public String getProjGUID() {
            return ProjGUID;
        }

        public void setProjGUID(String projGUID) {
            ProjGUID = projGUID;
        }

        public String getCstName() {
            return CstName;
        }

        public void setCstName(String cstName) {
            CstName = cstName;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getBookingGUID() {
            return BookingGUID;
        }

        public void setBookingGUID(String bookingGUID) {
            BookingGUID = bookingGUID;
        }

        public String getCardID() {
            return CardID;
        }

        public void setCardID(String cardID) {
            CardID = cardID;
        }

        public String getCstGUID() {
            return CstGUID;
        }

        public void setCstGUID(String cstGUID) {
            CstGUID = cstGUID;
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
