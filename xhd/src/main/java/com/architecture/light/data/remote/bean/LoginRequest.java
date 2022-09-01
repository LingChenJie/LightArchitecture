package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.RequestBean;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class LoginRequest extends RequestBean {
    private String UserCode;
    private String Password;

    public LoginRequest() {
        setInterfaceId("login");
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

