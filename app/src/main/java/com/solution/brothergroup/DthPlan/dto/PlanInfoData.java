package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlanInfoData implements Serializable {

    @SerializedName(value = "records", alternate = "rdata")
    @Expose
    private PlanInfoRecords records;
    @SerializedName(value = "status", alternate = "error")
    @Expose
    private Integer status;

    public PlanInfoRecords getRecords() {
        return records;
    }

    public void setRecords(PlanInfoRecords records) {
        this.records = records;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
