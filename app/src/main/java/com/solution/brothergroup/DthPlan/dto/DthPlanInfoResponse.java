package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DthPlanInfoResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private PlanInfoData data;
    @SerializedName("dataRP")
    @Expose
    private PlanInfoRPData dataRP;
    @SerializedName("dataRPDTHWithPackage")
    @Expose
    private PlanInfoRPWithPackage dataRPDTHWithPackage;
    @SerializedName("dataRPDTHChannelList")
    @Expose
    private DataRPDTHChannelList dataRPDTHChannelList;
    @SerializedName("dthhrData")
    @Expose
    private DthHRData dthhrData;

    @SerializedName("dataPA")
    @Expose
    private PlanInfoData dataPA;
    @SerializedName("myPlanData")
    @Expose
    private MyPlanData myPlanData;

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

    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public PlanInfoData getData() {
        return data;
    }

    public void setData(PlanInfoData data) {
        this.data = data;
    }

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

    public PlanInfoRPData getDataRP() {
        return dataRP;
    }

    public MyPlanData getMyPlanData() {
        return myPlanData;
    }

    public DthHRData getDthhrData() {
        return dthhrData;
    }

    public PlanInfoRPWithPackage getDataRPDTHWithPackage() {
        return dataRPDTHWithPackage;
    }

    public PlanInfoData getDataPA() {
        return dataPA;
    }

    public DataRPDTHChannelList getDataRPDTHChannelList() {
        return dataRPDTHChannelList;
    }
}
