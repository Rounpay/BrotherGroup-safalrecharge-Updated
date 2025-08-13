package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishnu Agarwal on 12/01/2022.
 */
public class DthPlanInfoAllData implements Serializable {
    @SerializedName("pType")
    @Expose
    private String pType;
    @SerializedName("pDetials")
    @Expose
    private ArrayList<PlanInfoPlan> pDetials;

    public String getpType() {
        return pType;
    }

    public ArrayList<PlanInfoPlan> getpDetials() {
        return pDetials;
    }
}


