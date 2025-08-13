package com.solution.brothergroup.Api.Request;

public class SubmitSocialDetailsRequest {
    String whatsappNo;
    String telegramNo;
    String hangoutId;
    public String userID;

    public String sessionID;

    public String session;

    public String appid;

    public String imei;

    public String regKey;

    public String version;

    public String serialNo;

    public String loginTypeID;

    public SubmitSocialDetailsRequest(String whatsappNo, String telegramNo, String hangoutId, String userID, String loginTypeID, String appid,
                                      String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.whatsappNo = whatsappNo;
        this.telegramNo = telegramNo;
        this.hangoutId = hangoutId;
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
