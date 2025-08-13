package com.solution.brothergroup.BrowsePlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanTypes implements Serializable {
    @SerializedName("pType")
    @Expose
    private String pType;
    @SerializedName("pDetails")
    @Expose
    private ArrayList<PlanDataDetails> pDetails;

    public String getpType() {
        return pType;
    }

    public ArrayList<PlanDataDetails> getpDetails() {
        return pDetails;
    }
}
