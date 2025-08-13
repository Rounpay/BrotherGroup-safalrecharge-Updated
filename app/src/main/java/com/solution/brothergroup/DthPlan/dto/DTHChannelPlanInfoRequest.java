package com.solution.brothergroup.DthPlan.dto;

public class DTHChannelPlanInfoRequest {
    String OID,PackageID;
    String userID;
    String sessionID;
    String session;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String loginTypeID;

    public DTHChannelPlanInfoRequest(String PackageID, String OID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.PackageID = PackageID;
        this.OID = OID;
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
}
