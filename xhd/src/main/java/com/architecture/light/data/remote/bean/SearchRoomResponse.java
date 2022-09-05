package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.ResponseBean;

import java.util.List;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class SearchRoomResponse extends ResponseBean {

    private String msg;
    private String code;
    private List<DataDTO> data;

    public static class DataDTO {
        private String ProjGUID;
        private List<FeeListDTO> FeeList;
        private String CstName;
        private String RoomInfo;
        private String Tel;
        private String CardID;
        private String RoomGUID;
        private String CstGUID;

        public static class FeeListDTO {
            private String FeeGUID;
            private String ItemType;
            private Double Amount;
            private String ItemNameGUID;
            private String ItemName;
            private Double YeAmount;
            private String ItemTypeGUID;

            public String getFeeGUID() {
                return FeeGUID;
            }

            public void setFeeGUID(String feeGUID) {
                FeeGUID = feeGUID;
            }

            public String getItemType() {
                return ItemType;
            }

            public void setItemType(String itemType) {
                ItemType = itemType;
            }

            public Double getAmount() {
                return Amount;
            }

            public void setAmount(Double amount) {
                Amount = amount;
            }

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

            public Double getYeAmount() {
                return YeAmount;
            }

            public void setYeAmount(Double yeAmount) {
                YeAmount = yeAmount;
            }

            public String getItemTypeGUID() {
                return ItemTypeGUID;
            }

            public void setItemTypeGUID(String itemTypeGUID) {
                ItemTypeGUID = itemTypeGUID;
            }
        }

        public String getProjGUID() {
            return ProjGUID;
        }

        public void setProjGUID(String projGUID) {
            ProjGUID = projGUID;
        }

        public List<FeeListDTO> getFeeList() {
            return FeeList;
        }

        public void setFeeList(List<FeeListDTO> feeList) {
            FeeList = feeList;
        }

        public String getCstName() {
            return CstName;
        }

        public void setCstName(String cstName) {
            CstName = cstName;
        }

        public String getRoomInfo() {
            return RoomInfo;
        }

        public void setRoomInfo(String roomInfo) {
            RoomInfo = roomInfo;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getCardID() {
            return CardID;
        }

        public void setCardID(String cardID) {
            CardID = cardID;
        }

        public String getRoomGUID() {
            return RoomGUID;
        }

        public void setRoomGUID(String roomGUID) {
            RoomGUID = roomGUID;
        }

        public String getCstGUID() {
            return CstGUID;
        }

        public void setCstGUID(String cstGUID) {
            CstGUID = cstGUID;
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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }
}
