package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlanRequest {
    @SerializedName("appid")
    @Expose
    public String appid = "";
    @SerializedName("imei")
    @Expose
    public String imei = "";
    @SerializedName("regKey")
    @Expose
    public String regKey = "";
    @SerializedName("version")
    @Expose
    public String version = "";
    @SerializedName("serialNo")
    @Expose
    public String serialNo = "";
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("circleID")
    @Expose
    private String circleID;
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("loginTypeID")
    @Expose
    public String  loginTypeID;
    @SerializedName("sessionID")
    @Expose
    public String sessionID;
    @SerializedName("session")
    @Expose
    public String session;
    public PlanRequest(String oid, String circleID, String appid, String imei, String regKey, String version, String serialNo, String userID, String loginTypeID, String sessionID, String session) {
        this.oid = oid;
        this.circleID = circleID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.sessionID = sessionID;
        this.session = session;
    }

}
