package com.solution.brothergroup.DTH.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DTHSubscriptionRequest {

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
    @SerializedName("loginTypeID")
    @Expose
    private String loginTypeID;
    @SerializedName("pid")
    @Expose
    private int pid;
    @SerializedName("customer")
    @Expose
    private String customer;

    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("areaID")
    @Expose
    private int areaID;
    @SerializedName("customerNumber")
    @Expose
    private String customerNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pincode")
    @Expose
    private String pincode;

    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("sessionID")
    @Expose
    private String sessionID;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("geoCode")
    @Expose
    private String geoCode;
    @SerializedName("securityKey")
    @Expose
    private String securityKey;
    @SerializedName("isReal")
    @Expose
    private boolean isReal;

    public DTHSubscriptionRequest(String appid, String imei, String regKey, String version, String serialNo, String loginTypeID,
                                  int pid, String customer, String customerNumber, String address, String pincode, String userID,
                                  String sessionID, String session, String geoCode, String securityKey, boolean isReal, String surname, String gender, int areaID) {
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.loginTypeID = loginTypeID;
        this.pid = pid;
        this.customer = customer;
        this.customerNumber = customerNumber;
        this.address = address;
        this.pincode = pincode;
        this.userID = userID;
        this.sessionID = sessionID;
        this.session = session;
        this.geoCode = geoCode;
        this.securityKey = securityKey;
        this.isReal = isReal;
        this.surname = surname;
        this.gender = gender;
        this.areaID = areaID;
    }
}

