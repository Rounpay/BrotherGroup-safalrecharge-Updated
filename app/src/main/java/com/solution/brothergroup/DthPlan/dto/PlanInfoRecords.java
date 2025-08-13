package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanInfoRecords implements Serializable {
    @SerializedName("plan")
    @Expose
    private ArrayList<PlanInfoPlan> plan = null;
    @SerializedName("Add-On Pack")
    @Expose
    private ArrayList<PlanInfoPlan> addOnPack = null;

    public ArrayList<PlanInfoPlan> getPlan() {
        return plan;
    }

    public void setPlan(ArrayList<PlanInfoPlan> plan) {
        this.plan = plan;
    }

    public ArrayList<PlanInfoPlan> getAddOnPack() {
        return addOnPack;
    }

    public void setAddOnPack(ArrayList<PlanInfoPlan> addOnPack) {
        this.addOnPack = addOnPack;
    }

}
