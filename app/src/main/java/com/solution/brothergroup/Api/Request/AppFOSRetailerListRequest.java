package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppFOSRetailerListRequest extends BasicRequest
{

    @SerializedName("roleID")
    @Expose
    private String roleID;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobileno")
    @Expose
    private String mobileno;

    @SerializedName("topRows")
    @Expose
    private int topRows;

    public AppFOSRetailerListRequest(String roleID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session , String name , String mobileno, int topRows ) {
        this.roleID = roleID;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.sessionID = sessionID;
        this.session = session;
        this.name = name;
        this.mobileno = mobileno;
        this.topRows = topRows;
    }


}
