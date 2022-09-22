package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class SearchPaymentRequest extends RequestBean {

    public SearchPaymentRequest() {
        setInterfaceId("charge");
        setTradeType("2");
    }

}

