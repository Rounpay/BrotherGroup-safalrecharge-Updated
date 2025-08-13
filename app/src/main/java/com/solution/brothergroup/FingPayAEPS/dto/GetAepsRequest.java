package com.solution.brothergroup.FingPayAEPS.dto;

public class GetAepsRequest {
    private PidData pidData;
    private String aadhar, amount;
    private int interfaceType, bankIIN;
    private String userID, bankName;
    private String loginTypeID;
    private String appid;
    private String imei;
    private String regKey;
    private String version;
    private String serialNo;
    private String sessionID;
    private String session;
    private String Lattitude, Longitude;
    private String pidDataXML;
    private String pidOptions,deviceName;
    private int bankID;


    public GetAepsRequest(String pidOptions,String deviceName,int bankID,String pidDataXML,String Lattitude, String Longitude,PidData pidData, String aadhar, int interfaceType, int bankIIN, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pidOptions = pidOptions;
        this.deviceName = deviceName;
        this.bankID = bankID;
        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
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

    public GetAepsRequest(String pidDataXML,String Lattitude, String Longitude,PidData pidData, String aadhar,String amount , int interfaceType, int bankIIN, String bankName, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.amount = amount;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
        this.bankName = bankName;
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

    public GetAepsRequest(String pidOptions,String deviceName,int bankID,String pidDataXML,String Lattitude, String Longitude,PidData pidData, String aadhar,  int interfaceType, int bankIIN,String bankName, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pidOptions = pidOptions;
        this.deviceName = deviceName;
        this.bankID = bankID;
        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
        this.bankName = bankName;
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


    public GetAepsRequest(String pidOptions,String deviceName,int bankID,String pidDataXML,String Lattitude, String Longitude,PidData pidData, String aadhar, String amount, int interfaceType, int bankIIN, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.pidOptions = pidOptions;
        this.deviceName = deviceName;
        this.bankID = bankID;
        this.pidDataXML = pidDataXML;
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.amount = amount;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
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


    public GetAepsRequest(String Lattitude, String Longitude, String pidDataXML, PidData pidData, String aadhar, String amount, int interfaceType, int bankIIN, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.Lattitude = Lattitude;
        this.Longitude = Longitude;
        this.pidDataXML = pidDataXML;
        this.pidData = pidData;
        this.aadhar = aadhar;
        this.amount = amount;
        this.interfaceType = interfaceType;
        this.bankIIN = bankIIN;
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
