package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeCResponse {
    @SerializedName("liveID")
    @Expose
    private String liveID;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public String getLiveID() {
        return liveID;
    }

    public String getTransactionID() {
        return transactionID;
    }
}
