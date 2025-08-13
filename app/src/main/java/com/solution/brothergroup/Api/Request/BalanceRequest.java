package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceRequest {


    String bankID;
    String amount;
    String transactionID;
    String mobileNo;
    String chequeNo;
    String cardNo;
    String accountHolderName;
    String accountNumber;
    Integer paymentID;
    String SlabID;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
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
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("session")
    @Expose
    private String session;
    private String walletTypeID;
    private String parentid;


    public BalanceRequest(String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
    }

    public BalanceRequest(String ParentID, String SlabID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.parentid = ParentID;
        this.SlabID = SlabID;
        this.sessionID = sessionID;
        this.session = session;
    }


   /* public BalanceRequest(String SlabID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String blank, String sessionID, String session) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.SlabID = SlabID;
        this.sessionID = sessionID;
        this.session = session;
    }*/

    public BalanceRequest(String bankID, String amount, String transactionID, String mobileNo, String chequeNo, String cardNo, String accountHolderName, String AccountNo, String PaymentModeID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String walletTypeID) {
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.bankID = bankID;
        this.amount = amount;
        this.transactionID = transactionID;
        this.mobileNo = mobileNo;
        this.chequeNo = chequeNo;
        this.cardNo = cardNo;
        this.accountHolderName = accountHolderName;
        this.accountNumber = AccountNo;
        this.paymentID = Integer.parseInt(PaymentModeID);
        this.sessionID = sessionID;
        this.session = session;
        this.walletTypeID = walletTypeID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLoginTypeID() {
        return loginTypeID;
    }

    public void setLoginTypeID(String loginTypeID) {
        this.loginTypeID = loginTypeID;
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
}
