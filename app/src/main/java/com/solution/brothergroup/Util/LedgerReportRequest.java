package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LedgerReportRequest {
    @SerializedName("topRows")
    @Expose
    private String topRows;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("isExport")
    @Expose
    private String isExport;
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
    @SerializedName("tMode")
    @Expose
    private String tMode;
    @SerializedName("isSelf")
    @Expose
    private String isSelf;
    @SerializedName("uMobile")
    @Expose
    private String uMobile;
    @SerializedName("WalletTypeID")
    @Expose
    private Integer WalletTypeID;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;

    public LedgerReportRequest(String topRows, String status, String oid, String fromDate, String toDate, String transactionID, String accountNo, String isExport, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID, int walletTypeID) {
        this.topRows = topRows;
        this.status = status;
        this.oid = oid;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.transactionID = transactionID;
        this.accountNo = accountNo;
        this.isExport = isExport;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.WalletTypeID = walletTypeID;
    }


    public LedgerReportRequest(String topRows, String status, String oid, String fromDate, String toDate, String transactionID, String accountNo, String isExport, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID, String tMode, String isSelf, String uMobile) {
        this.topRows = topRows;
        this.status = status;
        this.oid = oid;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.transactionID = transactionID;
        this.accountNo = accountNo;
        this.isExport = isExport;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.isSelf = isSelf;
        this.tMode = tMode;
        this.uMobile = uMobile;
    }

}
