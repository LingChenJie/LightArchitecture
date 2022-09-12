package com.architecture.light.data.remote.bean;

import com.architecture.light.data.remote.bean.base.BaseBean;
import com.architecture.light.data.remote.bean.base.ResponseBean;

import java.util.List;

/**
 * Created by SuQi on 2022/8/30.
 * Describe:
 */
public class LoginResponse extends ResponseBean {

    private String msg;
    private String code;
    private Data data;

    public static class Data extends BaseBean {
        private String UserName;
        private String UserGUID;
        private List<Project> ProjectList;

        public static class Project extends BaseBean {
            private String ProjName;
            private String ProjGUID;
            private String BUGUID;
            private List<Bank> BankList;
            private boolean isChecked = false;

            public static class Bank extends BaseBean {
                private String BankName;
                private Boolean IsMrBank;
                private String BankAccount;

                public String getBankName() {
                    return BankName;
                }

                public void setBankName(String bankName) {
                    BankName = bankName;
                }

                public Boolean getMrBank() {
                    return IsMrBank;
                }

                public void setMrBank(Boolean mrBank) {
                    IsMrBank = mrBank;
                }

                public String getBankAccount() {
                    return BankAccount;
                }

                public void setBankAccount(String bankAccount) {
                    BankAccount = bankAccount;
                }
            }

            public String getProjName() {
                return ProjName;
            }

            public void setProjName(String projName) {
                ProjName = projName;
            }

            public String getProjGUID() {
                return ProjGUID;
            }

            public void setProjGUID(String projGUID) {
                ProjGUID = projGUID;
            }

            public String getBUGUID() {
                return BUGUID;
            }

            public void setBUGUID(String BUGUID) {
                this.BUGUID = BUGUID;
            }

            public List<Bank> getBankList() {
                return BankList;
            }

            public void setBankList(List<Bank> bankList) {
                BankList = bankList;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getUserGUID() {
            return UserGUID;
        }

        public void setUserGUID(String userGUID) {
            UserGUID = userGUID;
        }

        public List<Project> getProjectList() {
            return ProjectList;
        }

        public void setProjectList(List<Project> projectList) {
            ProjectList = projectList;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
