package com.solution.brothergroup.DthPlan.dto;

/**
 * Created by Vishnu Agarwal on 21,November,2019
 */
public class PlanValidity {
    String amount, validity,details;

    public PlanValidity(String amount, String validity,String details) {
        this.amount = amount;
        this.validity = validity;
        this.details=details;
    }

    public String getAmount() {
        return amount;
    }

    public String getValidity() {
        return validity;
    }

    public String getDetails() {
        return details;
    }
}
