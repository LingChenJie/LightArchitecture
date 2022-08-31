package com.light.xhd.data.remote.bean;

import com.light.xhd.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class ChargeListRequest extends RequestBean {

    public ChargeListRequest() {
        setInterfaceId("charge");
        setTradeType("2");
    }

}

