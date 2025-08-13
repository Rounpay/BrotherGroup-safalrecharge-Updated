package com.solution.brothergroup.DthPlan.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanInfoRPData implements Serializable {
    String tel;
    String operator;
    PlanInfoRPRecords records;
    int status;
    ArrayList<PlanRPResponse> response = new ArrayList<>();

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public PlanInfoRPRecords getRecords() {
        return records;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<PlanRPResponse> getResponse() {
        return response;
    }
}

