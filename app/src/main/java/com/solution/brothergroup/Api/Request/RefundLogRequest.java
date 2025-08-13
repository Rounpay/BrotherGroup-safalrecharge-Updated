package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefundLogRequest {

    @SerializedName("topRows")
    @Expose
    private int topRows;
    @SerializedName("criteria")
    @Expose
    private int criteria;
    @SerializedName("criteriaText")
    @Expose
    private String criteriaText;

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("dateType")
    @Expose
    private int dateType;

    @SerializedName("transactionID")
    @Expose
    private String transactionID;

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

    @SerializedName("oid")
    @Expose
    private String oid;

    public RefundLogRequest(int topRows, int criteria, String criteriaText, int status, String fromDate, String toDate, int dateType, String transactionID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.topRows = topRows;
        this.criteria = criteria;
        this.criteriaText = criteriaText;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dateType = dateType;
        this.transactionID = transactionID;
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

    public RefundLogRequest(String oid, int topRows, int criteria, String criteriaText, int status, String fromDate, String toDate, int dateType, String transactionID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID) {
        this.oid = oid;
        this.topRows = topRows;
        this.criteria = criteria;
        this.criteriaText = criteriaText;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dateType = dateType;
        this.transactionID = transactionID;
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
