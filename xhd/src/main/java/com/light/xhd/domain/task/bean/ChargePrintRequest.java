package com.light.xhd.domain.task.bean;

import com.light.xhd.domain.task.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class ChargePrintRequest extends RequestBean {
    private String CardID;
    private String Tel;

    public ChargePrintRequest() {
        setInterfaceId("charge");
        setTradeType("3");
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
}

