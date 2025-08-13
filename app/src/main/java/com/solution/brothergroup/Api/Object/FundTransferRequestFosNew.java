package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FundTransferRequestFosNew  {
    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("sessionID")
    @Expose
    public String sessionID;
    @SerializedName("session")
    @Expose
    public String session;
    @SerializedName("appid")
    @Expose
    public String appid;
    @SerializedName("imei")
    @Expose
    public String imei;
    @SerializedName("regKey")
    @Expose
    public String regKey;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("serialNo")
    @Expose
    public String serialNo;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;
    @SerializedName("oType")
    @Expose
    private boolean oType;
    @SerializedName("uid")
    @Expose
    private int uid;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("walletType")
    @Expose
    private int walletType;
    @SerializedName("paymentID")
    @Expose
    private int paymentID;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("IsMarkCredit")
    @Expose
    private boolean IsMarkCredit;



    public FundTransferRequestFosNew(String securityKey, boolean oType, int uid, String remark, int walletType, int paymentID, String amount, String userID, int loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, boolean IsMarkCredit) {
        this.securityKey = securityKey;
        this.oType = oType;
        this.uid = uid;
        this.remark = remark;
        this.walletType = walletType;
        this.paymentID = paymentID;
        this.amount = amount;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.IsMarkCredit = IsMarkCredit;

    }

}

