package com.solution.brothergroup.Api.Request;

public class OnboardingRequest {

    String oid;
    String outletID;
    String userID;
    String sessionID;
    String session;
    String appid;
    String imei;
    String regKey;
    String version;
    String serialNo;
    String loginTypeID;
    String otp, OTPRefID, pidData;
    boolean isBio;
    int bioAuthType;
    String Lattitude;
    String Longitude;

    public OnboardingRequest(int bioAuthType,boolean isBio, String otp, String OTPRefID, String pidData, String oid, String outletID, String userID, String sessionID, String session, String appid, String imei, String regKey, String version, String serialNo, String loginTypeID,String Lattitude,String Longitude) {
        this.bioAuthType = bioAuthType;
        this.isBio = isBio;
        this.otp = otp;
        this.OTPRefID = OTPRefID;
        this.pidData = pidData;
        this.oid = oid;
        this.outletID = outletID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
    }

}
