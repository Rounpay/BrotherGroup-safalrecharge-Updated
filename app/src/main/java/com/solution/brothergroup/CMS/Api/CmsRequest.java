package com.solution.brothergroup.CMS.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Request.BasicRequest;

public class CmsRequest extends BasicRequest {

    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("convenientFee")
    @Expose
    private Integer convenientFee;

    public CmsRequest(String tid, String transactionID, Integer convenientFee, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session) {
        this.tid = tid;
        this.transactionID = transactionID;
        this.convenientFee = convenientFee;
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
