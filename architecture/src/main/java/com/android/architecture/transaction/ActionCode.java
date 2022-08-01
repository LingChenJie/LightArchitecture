package com.android.architecture.transaction;

import com.android.architecture.R;
import com.android.architecture.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * File describe:Action结果码
 */
public class ActionCode {
    public static final String SUCCESS = "00";
    public static final String USER_CANCEL = "01";

    static {
        String[] KeyValueSet = {
                SUCCESS, getString(R.string.action_success),
                USER_CANCEL, getString(R.string.action_user_cancel),
        };
        add(KeyValueSet);
    }

    private static final Map<String, String> codeMap = new HashMap<>();

    public static void add(String[] keyValuesSet) {
        if (keyValuesSet == null) return;
        int size = keyValuesSet.length;
        if (size % 2 == 0) {
            for (int i = 0; i < size; i += 2) {
                codeMap.put((String) keyValuesSet[i], keyValuesSet[i + 1]);
            }
        } else {
            throw new RuntimeException("Action code key value pairs must be even numbers");
        }
    }

    public static String getMessage(String code) {
        String message = codeMap.get(code);
        message = message != null ? message : getString(R.string.action_undefined);
        return message;
    }

    private static String getString(int resId) {
        return AppUtils.getApp().getString(resId);
    }

}
