package com.solution.brothergroup.Api.Request;

/**
 * Created by Vishnu Agarwal on 22,November,2019
 */
public class PurchaseTokenRequest {
    public String oid;
    public String accountNo;
    public String amount;
    public String userID;
    public String sessionID;
    public String session;
    public String appid;
    public String imei;
    public String regKey;
    public String version;
    public String serialNo;
    public String loginTypeID;
    public String outletID;

    public PurchaseTokenRequest(String oid, String accountNo, String amount, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID, String outletID) {
        this.oid = oid;
        this.accountNo = accountNo;
        this.amount = amount;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.outletID = outletID;
    }
}
