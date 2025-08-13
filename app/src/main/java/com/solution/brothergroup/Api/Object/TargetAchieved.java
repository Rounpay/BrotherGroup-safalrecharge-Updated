package com.solution.brothergroup.Api.Object;

/**
 * Created by Vishnu Agarwal on 23,December,2019
 */
public class TargetAchieved {
    int sid;
    String service;
    double target,todaySale;
    double targetTillDate;

    public int getSid() {
        return sid;
    }

    public String getService() {
        return service;
    }

    public double getTarget() {
        return target;
    }

    public double getTodaySale() {
        return todaySale;
    }

    public double getTargetTillDate() {
        return targetTillDate;
    }
}
