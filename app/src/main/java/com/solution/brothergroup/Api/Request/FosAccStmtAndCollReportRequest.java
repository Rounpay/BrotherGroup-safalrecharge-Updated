package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FosAccStmtAndCollReportRequest {
    @SerializedName("topRows")
    @Expose
    private String topRows;

    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

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

    @SerializedName("uType")
    @Expose
    private String uType;
    @SerializedName("areaID")
    @Expose
    private int areaID;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("Mobile")
    @Expose
    private String Mobile;

    public FosAccStmtAndCollReportRequest(String appid, String fromDate, String regKey, String serialNo, String session, String sessionID, String toDate, String topRows, String version
            , String uType, int areaID, String userID, String loginTypeID, String imei) {

        this.appid = appid;
        this.fromDate = fromDate;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.toDate = toDate;
        this.topRows = topRows;
        this.version = version;
        this.uType = uType;
        this.areaID = areaID;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.imei = imei;

    }

    public FosAccStmtAndCollReportRequest(String Mobile,String appid, String fromDate, String regKey, String serialNo, String session, String sessionID, String toDate, String topRows, String version
            , String uType, int areaID, String userID, String loginTypeID, String imei) {

        this.Mobile = Mobile;
        this.appid = appid;
        this.fromDate = fromDate;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.toDate = toDate;
        this.topRows = topRows;
        this.version = version;
        this.uType = uType;
        this.areaID = areaID;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.imei = imei;

    }
}
