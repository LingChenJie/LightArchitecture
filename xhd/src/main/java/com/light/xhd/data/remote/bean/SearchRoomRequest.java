package com.light.xhd.data.remote.bean;

import com.light.xhd.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class SearchRoomRequest extends RequestBean {
    private String ProjGUID;
    private String ZygwGUID;
    private String CardID;
    private String Tel;
    private String RoomInfo;

    public SearchRoomRequest() {
        setInterfaceId("search");
        setTradeType("1");
    }

    public String getProjGUID() {
        return ProjGUID;
    }

    public void setProjGUID(String projGUID) {
        ProjGUID = projGUID;
    }

    public String getZygwGUID() {
        return ZygwGUID;
    }

    public void setZygwGUID(String zygwGUID) {
        ZygwGUID = zygwGUID;
    }

    public String getCardID() {
        return CardID;
    }

    public void setCardID(String cardID) {
        CardID = cardID;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getRoomInfo() {
        return RoomInfo;
    }

    public void setRoomInfo(String roomInfo) {
        RoomInfo = roomInfo;
    }
}

