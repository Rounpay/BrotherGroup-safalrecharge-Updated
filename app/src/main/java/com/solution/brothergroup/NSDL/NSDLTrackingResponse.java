package com.solution.brothergroup.NSDL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NSDLTrackingResponse {

    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("panStatus")
    @Expose
    private String panStatus;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ackNo")
    @Expose
    private String ackNo;
    @SerializedName("panNo")
    @Expose
    private String panNo;
    @SerializedName("errorCode")
    @Expose
    private Integer errorCode;

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public String getPanStatus() {
        return panStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getAckNo() {
        return ackNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
