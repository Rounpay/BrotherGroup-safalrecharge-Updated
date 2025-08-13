package com.solution.brothergroup.NSDL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReciptRequest {
    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("convenientFee")
    @Expose
    private Integer convenientFee;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
    @SerializedName("appid")
    @Expose
    private Object appid;
    @SerializedName("imei")
    @Expose
    private Object imei;
    @SerializedName("regKey")
    @Expose
    private Object regKey;
    @SerializedName("version")
    @Expose
    private Object version;
    @SerializedName("serialNo")
    @Expose
    private Object serialNo;
    @SerializedName("sessionId")
    @Expose
    private Object sessionId;
    @SerializedName("session")
    @Expose
    private Object session;

    /**
     * No args constructor for use in serialization
     *
     */
    public ReciptRequest() {
    }

    public ReciptRequest(String tid, String transactionID, Integer convenientFee, String userID, String loginTypeID, Object appid, Object imei, Object regKey, Object version, Object serialNo, Object sessionId, Object session) {
        this.tid = tid;
        this.transactionID = transactionID;
        this.convenientFee = convenientFee;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionId = sessionId;
        this.session = session;
    }




    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getConvenientFee() {
        return convenientFee;
    }

    public void setConvenientFee(Integer convenientFee) {
        this.convenientFee = convenientFee;
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

    public Object getAppid() {
        return appid;
    }

    public void setAppid(Object appid) {
        this.appid = appid;
    }

    public Object getImei() {
        return imei;
    }

    public void setImei(Object imei) {
        this.imei = imei;
    }

    public Object getRegKey() {
        return regKey;
    }

    public void setRegKey(Object regKey) {
        this.regKey = regKey;
    }

    public Object getVersion() {
        return version;
    }

    public void setVersion(Object version) {
        this.version = version;
    }

    public Object getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Object serialNo) {
        this.serialNo = serialNo;
    }

}

