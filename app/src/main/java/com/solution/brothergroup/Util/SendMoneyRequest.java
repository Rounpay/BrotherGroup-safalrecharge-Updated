package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendMoneyRequest {

    public SendMoneyRequest(RequestSendMoney reqSendMoney, String securityKey, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.reqSendMoney = reqSendMoney;
        this.securityKey = securityKey;
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

    private RequestSendMoney reqSendMoney;
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
    @SerializedName("securityKey")
    @Expose
    private String securityKey;

}
