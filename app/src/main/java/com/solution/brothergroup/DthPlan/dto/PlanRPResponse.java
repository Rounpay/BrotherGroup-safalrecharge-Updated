package com.solution.brothergroup.DthPlan.dto;

import java.io.Serializable;

public class PlanRPResponse implements Serializable {
    String opName;
    double rechargeAmount;
    String rechargeValidity;
    String rechargeType;
    String details;
    String packagelanguage,language;
    int packageId,channelcount,packageCount;

    public String getOpName() {
        return opName;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public String getRechargeValidity() {
        return rechargeValidity;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public String getDetails() {
        return details;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getPackagelanguage() {
        return packagelanguage;
    }

    public String getLanguage() {
        return language;
    }

    public int getPackageCount() {
        return packageCount;
    }

    public int getChannelcount() {
        return channelcount;
    }
}
