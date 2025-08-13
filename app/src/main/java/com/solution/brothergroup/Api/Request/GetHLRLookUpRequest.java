package com.solution.brothergroup.Api.Request;

/**
 * Created by Vishnu Agarwal on 16,November,2019
 */
public class GetHLRLookUpRequest {
    public String userID;
    public String sessionID;
    public String session;
    public String appid;
    public String imei;
    public String regKey;
    public String version;
    public String serialNo;
    public String loginTypeID;
    String mobile;

    public GetHLRLookUpRequest(String mobile, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.mobile = mobile;
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
