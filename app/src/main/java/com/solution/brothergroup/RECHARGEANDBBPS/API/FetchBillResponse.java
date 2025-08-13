package com.solution.brothergroup.RECHARGEANDBBPS.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchBillResponse {
    @SerializedName("bBPSResponse")
    @Expose
    public BBPSResponse bBPSResponse;
    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public int checkID;

    public BBPSResponse getbBPSResponse() {
        return bBPSResponse;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }
}
