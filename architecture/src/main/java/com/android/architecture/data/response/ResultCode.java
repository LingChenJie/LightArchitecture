package com.android.architecture.data.response;

import com.android.architecture.R;
import com.android.architecture.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/28
 * Modify date: 2022/7/28
 * Version: 1
 */
public class ResultCode {

    private static final Map<String, String> map = new HashMap<>();

    public static final String SUCCESS = "00";
    public static final String FAIL = "01";

    static {
        map.put(SUCCESS, Utils.getApp().getString(R.string.code_success));
        map.put(FAIL, Utils.getApp().getString(R.string.code_fail));
    }

    public static String getMessage(String code) {
        if (map.containsKey(code)) {
            return map.get(code);
        } else {
            return null;
        }
    }

}
