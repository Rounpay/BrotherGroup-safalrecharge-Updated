package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeReportRequest {
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
    @SerializedName("topRows")
    @Expose
    public String topRows = "";
    @SerializedName("status")
    @Expose
    public int status ;
    @SerializedName("fromDate")
    @Expose
    public String fromDate = "";
    @SerializedName("toDate")
    @Expose
    public String toDate = "";
    @SerializedName("transactionID")
    @Expose
    public String transactionID = "";
    @SerializedName("accountNo")
    @Expose
    public String accountNo = "";
    @SerializedName("isExport")
    @Expose
    public String isExport = "";
    @SerializedName("userID")
    @Expose
    public String userID = "";
    @SerializedName("loginTypeID")
    @Expose
    public String loginTypeID = "";
    @SerializedName("sessionID")
    @Expose
    public String sessionID = "";
    @SerializedName("session")
    @Expose
    public String session = "";
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("opTypeID")
    @Expose
    private String opTypeID;
    @SerializedName("IsRecent")
    @Expose
    private boolean IsRecent;
    @SerializedName("childMobile")
    @Expose
    public String childMobile = "";

    public RechargeReportRequest(boolean IsRecent, String opTypeID, String oid, String appid, String imei, String regKey, String version, String serialNo, String topRows, int status, String fromDate, String toDate, String transactionID, String accountNo, String childMobile, String isExport, String userID, String sessionID, String session, String loginTypeID) {
        this.IsRecent = IsRecent;
        this.opTypeID = opTypeID;
        this.oid = oid;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.topRows = topRows;
        this.status = status;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.transactionID = transactionID;
        this.accountNo = accountNo;
        this.childMobile = childMobile;
        this.isExport = isExport;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.loginTypeID = loginTypeID;
    }

}
