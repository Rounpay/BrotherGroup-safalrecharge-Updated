package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HeavyrefreshRequest {
    @SerializedName("oid")
    @Expose
    private String oid;

    @SerializedName("appid")
    @Expose
    public String appid = "";
    @SerializedName("imei")
    @Expose
    public String imei = "";

    @SerializedName("accountNo")
    @Expose
    public String accountNo = "";
    @SerializedName("regKey")
    @Expose
    public String regKey = "";
    @SerializedName("version")
    @Expose
    public String version = "";
    @SerializedName("serialNo")
    @Expose
    public String serialNo = "";

    public HeavyrefreshRequest(String oid, String accountNo, String appid, String imei, String regKey, String version, String serialNo) {
        this.oid = oid;
        this.accountNo = accountNo;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
    }

}
