package com.solution.brothergroup.DthPlan.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PlanInfoRPWithPackage implements Serializable {
    String tel;
    String operator;
    PlanInfoRPRecords records;
    int status;

    ArrayList<PlanRPResponse> response = new ArrayList<>();
    @SerializedName("package")
    @Expose
    ArrayList<PlanRPResponse> packageList = new ArrayList<>();
    ArrayList<DthPlanLanguages> languages = new ArrayList<>();

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
        if(response!=null && response.size()>0){
            Collections.sort(response, (o1, o2) ->
                    Integer.compare(o2.getChannelcount(),o1.getChannelcount()));
        }
        return response;
    }

    public ArrayList<PlanRPResponse> getPackageList() {
        if(packageList!=null && packageList.size()>0){
            Collections.sort(packageList, (o1, o2) ->
                    Integer.compare(o2.getChannelcount(),o1.getChannelcount()));
        }
        return packageList;
    }

    public ArrayList<DthPlanLanguages> getLanguages() {
        return languages;
    }
}

