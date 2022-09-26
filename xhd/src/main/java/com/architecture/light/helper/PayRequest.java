package com.architecture.light.helper;

import com.android.architecture.helper.Logger;
import com.architecture.light.settings.PayCache;
import com.chinaums.mis.bank.BankDAO;
import com.chinaums.mis.bank.ICallBack;
import com.chinaums.mis.bean.RequestPojo;
import com.chinaums.mis.bean.ResponsePojo;
import com.chinaums.mis.bean.TransCfx;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/20/22
 * Modify date: 9/20/22
 * Version: 1
 */
public class PayRequest {

    private static final String TAG = "PayRequest";
    private static final TransCfx transCfx = new TransCfx();
    private static final BankDAO bankDAO = new BankDAO();

    public PayRequest() {
        transCfx.setPosConnMode(PayCache.INSTANCE.getPosConnMode());
        transCfx.setIp(PayCache.INSTANCE.getIp());
        transCfx.setDevPath(PayCache.INSTANCE.getComNo());
        transCfx.setBaudRate(PayCache.INSTANCE.getBoundNo());
    }

    public ResponsePojo execute(RequestPojo request) throws UnknownHostException, UnsupportedEncodingException, JSONException {
        bankDAO.getCallBack(new ICallBack() {
            @Override
            public void getCallBack(String stateCode, String stateTips) {
                Logger.i(TAG, "stateCode=" + stateCode + "|" + "stateTips=" + stateTips);
            }
        });
        return bankDAO.bankall(transCfx, request);
    }

}
