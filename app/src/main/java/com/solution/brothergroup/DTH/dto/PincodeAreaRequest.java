package com.solution.brothergroup.DTH.dto;

public class PincodeAreaRequest {
    private String pincode;
    private String appid;
    private String imei;
    private String regKey;
    private String version;
    private String serialNo;
    private String loginTypeID;
    private String userID;
    private String sessionID;
    private String session;


    public PincodeAreaRequest(String pincode,String appid, String imei, String regKey, String version, String serialNo, String loginTypeID, String userID,
                              String sessionID, String session) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.pincode = pincode;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;

    }
}
