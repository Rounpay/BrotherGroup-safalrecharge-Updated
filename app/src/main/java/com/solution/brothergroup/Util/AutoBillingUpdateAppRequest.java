package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AutoBillingUpdateAppRequest implements Serializable {

    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("userIdInput")
    @Expose
    private int userIdInput;
    @SerializedName("isAutoBilling")
    @Expose
    private Boolean isAutoBilling;
    @SerializedName("maxBillingCountAB")
    @Expose
    private String maxBillingCountAB;
    @SerializedName("balanceForAB")
    @Expose
    private String balanceForAB;
    @SerializedName("fromFOSAB")
    @Expose
    private Boolean fromFOSAB;
    @SerializedName("maxCreditLimitAB")
    @Expose
    private String maxCreditLimitAB;
    @SerializedName("maxTransferLimitAB")
    @Expose
    private String maxTransferLimitAB;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("appid")
    @Expose
    private String appid;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("regKey")
    @Expose
    private String regKey;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;

    public AutoBillingUpdateAppRequest( int userIdInput, Boolean isAutoBilling, String maxBillingCountAB, String balanceForAB, Boolean fromFOSAB, String maxCreditLimitAB, String maxTransferLimitAB, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.userIdInput = userIdInput;
        this.isAutoBilling = isAutoBilling;
        this.maxBillingCountAB = maxBillingCountAB;
        this.balanceForAB = balanceForAB;
        this.fromFOSAB = fromFOSAB;
        this.maxCreditLimitAB = maxCreditLimitAB;
        this.maxTransferLimitAB = maxTransferLimitAB;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
    }
}

  /*  public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserIdInput() {
        return userIdInput;
    }

    public void setUserIdInput(String userIdInput) {
        this.userIdInput = userIdInput;
    }

    public Boolean getIsAutoBilling() {
        return isAutoBilling;
    }

    public void setIsAutoBilling(Boolean isAutoBilling) {
        this.isAutoBilling = isAutoBilling;
    }

    public String getMaxBillingCountAB() {
        return maxBillingCountAB;
    }

    public void setMaxBillingCountAB(String maxBillingCountAB) {
        this.maxBillingCountAB = maxBillingCountAB;
    }

    public String getBalanceForAB() {
        return balanceForAB;
    }

    public void setBalanceForAB(String balanceForAB) {
        this.balanceForAB = balanceForAB;
    }

    public Boolean getFromFOSAB() {
        return fromFOSAB;
    }

    public void setFromFOSAB(Boolean fromFOSAB) {
        this.fromFOSAB = fromFOSAB;
    }

    public String getMaxCreditLimitAB() {
        return maxCreditLimitAB;
    }

    public void setMaxCreditLimitAB(String maxCreditLimitAB) {
        this.maxCreditLimitAB = maxCreditLimitAB;
    }

    public String getMaxTransferLimitAB() {
        return maxTransferLimitAB;
    }

    public void setMaxTransferLimitAB(String maxTransferLimitAB) {
        this.maxTransferLimitAB = maxTransferLimitAB;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) {
        this.regKey = regKey;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
    }*/


