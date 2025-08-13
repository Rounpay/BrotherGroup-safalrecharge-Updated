package com.solution.brothergroup.Authentication.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Response.Data;

/**
 * Created by Vishnu on 08-03-2017.
 */

public class LoginResponse {

    /*
        @SerializedName("data")
        @Expose
        public BalanceData balanceData;
    */
    @SerializedName("checkID")
    @Expose
    public Integer checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    public boolean isPasswordExpired;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName(value = "isAreaMaster",alternate = "IsAreaMaster")
    @Expose
    public boolean isAreaMaster;
    @SerializedName("otpSession")
    @Expose
    private String otpSession;
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isPlanServiceUpdated")
    @Expose
    public boolean isPlanServiceUpdated;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;
    @SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isReferral")
    @Expose
    private boolean isReferral;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName(value = "IsDTHInfoCall",alternate = "isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName(value = "IsHeavyRefresh",alternate = "isHeavyRefresh")
    @Expose
    private boolean isHeavyRefresh;
    @SerializedName(value = "IsRealAPIPerTransaction",alternate = "isRealAPIPerTransaction")
    @Expose
    private boolean isRealAPIPerTransaction;
    @SerializedName(value = "IsTargetShow",alternate = "isTargetShow")
    @Expose
    private boolean isTargetShow;
    @SerializedName("isAutoBilling")
    @Expose
    private boolean isAutoBilling;
    @SerializedName(value = "isDenominationIncentive",alternate = "IsDenominationIncentive")
    @Expose
    public boolean isDenominationIncentive;
    @SerializedName(value = "isAccountStatement",alternate = "IsAccountStatement")
    @Expose
    public boolean isAccountStatement;

    public boolean isAccountStatement() {
        return isAccountStatement;
    }
    public boolean isAutoBilling() {
        return isAutoBilling;
    }

    public void setAutoBilling(boolean autoBilling) {
        isAutoBilling = autoBilling;
    }

    public Data getData() {
        return data;
    }

    public boolean isTargetShow() {
        return isTargetShow;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(String isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public boolean isPlanServiceUpdated() {
        return isPlanServiceUpdated;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(String isAppValid) {
        this.isAppValid = isAppValid;
    }

    public String getOtpSession() {
        return otpSession;
    }

    public void setOtpSession(String otpSession) {
        this.otpSession = otpSession;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public boolean isDenominationIncentive() {
        return isDenominationIncentive;
    }

    private String resOTP;

    public String getResOTP() {
        return resOTP;
    }




   /* public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }*/

    /*public BalanceData getBalanceData() {
        return balanceData;
    }

    public void setBalanceData(BalanceData balanceData) {
        this.balanceData = balanceData;
    }*/

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCheckID() {
        return checkID;
    }

    public void setCheckID(Integer checkID) {
        this.checkID = checkID;
    }

    public Boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public void setDTHInfo(boolean DTHInfo) {
        isDTHInfo = DTHInfo;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public void setRoffer(boolean roffer) {
        isRoffer = roffer;
    }

    public boolean isHeavyRefresh() {
        return isHeavyRefresh;
    }

    public boolean isRealAPIPerTransaction() {
        return isRealAPIPerTransaction;
    }

    public void setHeavyRefresh(boolean heavyRefresh) {
        isHeavyRefresh = heavyRefresh;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

    public boolean isAreaMaster() {
        return isAreaMaster;
    }
}
