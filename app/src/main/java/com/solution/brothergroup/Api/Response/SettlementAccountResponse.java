package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.SettlementAccountData;

import java.util.ArrayList;

public class SettlementAccountResponse {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("isSattlemntAccountVerify")
    @Expose
    private boolean isSattlemntAccountVerify;
    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("data")
    @Expose
    private ArrayList<SettlementAccountData> data;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public boolean isSattlemntAccountVerify() {
        return isSattlemntAccountVerify;
    }

    public ArrayList<SettlementAccountData> getData() {
        return data;
    }
}
