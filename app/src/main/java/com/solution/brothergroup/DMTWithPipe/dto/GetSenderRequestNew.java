package com.solution.brothergroup.DMTWithPipe.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.solution.brothergroup.Util.Senderobject;


public class GetSenderRequestNew {

    private Senderobject senderRequest;
    com.solution.brothergroup.DMTWithPipe.dto.BeneDetailNew beneDetail;
    String oid;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
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
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("session")
    @Expose
    private String session;

    @SerializedName("sid")
    @Expose
    private String sid;

    @SerializedName("otp")
    @Expose
    private String otp;

    public GetSenderRequestNew(String oid, Senderobject senderRequest, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.senderRequest = senderRequest;
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

    public GetSenderRequestNew(String oid, Senderobject senderRequest, com.solution.brothergroup.DMTWithPipe.dto.BeneDetailNew beneDetail, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.senderRequest = senderRequest;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.beneDetail = beneDetail;
    }

    public GetSenderRequestNew(String oid, Senderobject senderRequest, com.solution.brothergroup.DMTWithPipe.dto.BeneDetailNew beneDetail, String sid, String otp, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.sid = sid;
        this.otp = otp;
        this.senderRequest = senderRequest;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.beneDetail = beneDetail;
    }


    public GetSenderRequestNew(String oid, String sid, Senderobject senderRequest, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.oid = oid;
        this.sid = sid;
        this.senderRequest = senderRequest;
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
