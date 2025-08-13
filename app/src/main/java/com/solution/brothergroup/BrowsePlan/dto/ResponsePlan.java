package com.solution.brothergroup.BrowsePlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePlan {

    @SerializedName("data")
    @Expose
    private PlanData data;

    @SerializedName("dataRP")
    @Expose
    private PlanData dataRP;

    @SerializedName("dataPA")
    @Expose
    private PlanData dataPA;
    @SerializedName("statuscode")
    @Expose
    private String statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private String isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private String isAppValid;

    public PlanData getData() {
        return data;
    }

    public PlanData getDataRP() {
        return dataRP;
    }

    public PlanData getDataPA() {
        return dataPA;
    }

    public String getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }
}
