package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPlanData {
    @SerializedName("status")
    @Expose
    boolean status;
    @SerializedName("msg")
    @Expose
    String msg;
    @SerializedName("result")
    @Expose
     MyPlanDataResult result;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public  MyPlanDataResult getResult() {
        return result;
    }
}
