package com.solution.brothergroup.MoveToWallet.dto;

public class MoveToWalletRequest {

    String appid;
    String imei;
    String loginTypeID;
    String regKey;
    String serialNo;
    String session;
    String sessionID;
    String userID;
    String version;
    String actionType;
    String TransMode, OID;
    String amount;
    int MTWID;

    public MoveToWalletRequest(String appid, String imei, String loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version, String actionType, String transMode, String amount, int MTWID) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.actionType = actionType;
        this.amount = amount;
        TransMode = transMode;
        this.MTWID = MTWID;
    }

    public MoveToWalletRequest(String appid, String imei, String loginTypeID, String regKey, String serialNo, String session, String sessionID, String userID, String version, String actionType, String transMode, String OID, String amount, int MTWID) {
        this.appid = appid;
        this.imei = imei;
        this.loginTypeID = loginTypeID;
        this.regKey = regKey;
        this.serialNo = serialNo;
        this.session = session;
        this.sessionID = sessionID;
        this.userID = userID;
        this.version = version;
        this.actionType = actionType;
        this.amount = amount;
        this.TransMode = transMode;
        this.OID = OID;
        this.MTWID = MTWID;
    }
}
