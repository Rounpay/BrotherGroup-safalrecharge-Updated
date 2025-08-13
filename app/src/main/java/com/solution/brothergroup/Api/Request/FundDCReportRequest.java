package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundDCReportRequest {

    @SerializedName("isSelf")
    @Expose
    private boolean isSelf;
    @SerializedName("walletTypeID")
    @Expose
    private Integer walletTypeID;
    @SerializedName("otherUserMob")
    @Expose
    private String otherUserMob;

    @SerializedName("serviceID")
    @Expose
    private Integer serviceID;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("accountNo")
    @Expose
    private String accountNo;

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

    public FundDCReportRequest(boolean isSelf, Integer walletTypeID, Integer serviceID, String otherUserMob, String fromDate, String toDate, String accountNo, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.isSelf = isSelf;
        this.walletTypeID = walletTypeID;
        this.serviceID = serviceID;

        this.otherUserMob = otherUserMob;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.accountNo = accountNo;
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
