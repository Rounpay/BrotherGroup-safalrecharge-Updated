package com.solution.brothergroup.BrowsePlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlanData {

    @SerializedName(value = "records", alternate = "rdata")
    @Expose
    PlanDataRecords records;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("types")
    @Expose
    private ArrayList<PlanTypes> types;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("circle")
    @Expose
    private String circle;
    @SerializedName("message")
    @Expose
    private String message;

    public int getError() {
        return error;
    }

    public String getOperator() {
        return operator;
    }

    public String getCircle() {
        return circle;
    }

    public ArrayList<PlanTypes> getTypes() {
        return types;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public PlanDataRecords getRecords() {
        return records;
    }
}
