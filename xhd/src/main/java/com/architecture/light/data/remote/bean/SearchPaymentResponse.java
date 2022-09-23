package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.ResponseBean;

import java.util.List;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class SearchPaymentResponse extends ResponseBean {

    private String msg;
    private String code;
    private List<Data> data;

    public static class Data extends ResponseBean {
        private String ParentGUID;
        private String FeeItemGUID;
        private String FeeItemName;
        private boolean isParentLevel;
        private boolean isSubLevel;
        private boolean isChecked;

        public String getParentGUID() {
            return ParentGUID;
        }

        public void setParentGUID(String parentGUID) {
            ParentGUID = parentGUID;
        }

        public String getFeeItemGUID() {
            return FeeItemGUID;
        }

        public void setFeeItemGUID(String feeItemGUID) {
            FeeItemGUID = feeItemGUID;
        }

        public String getFeeItemName() {
            return FeeItemName;
        }

        public void setFeeItemName(String feeItemName) {
            FeeItemName = feeItemName;
        }

        public boolean isParentLevel() {
            return isParentLevel;
        }

        public void setParentLevel(boolean parentLevel) {
            isParentLevel = parentLevel;
        }

        public boolean isSubLevel() {
            return isSubLevel;
        }

        public void setSubLevel(boolean subLevel) {
            isSubLevel = subLevel;
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
