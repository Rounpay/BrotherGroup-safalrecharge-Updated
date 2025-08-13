package com.solution.brothergroup.ROffer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ROfferObject implements Serializable {
    @SerializedName(value = "rs", alternate = {"price", "amount"})
    @Expose
    private String rs;
    @SerializedName(value = "desc", alternate = {"ofrtext", "description"})
    @Expose
    private String desc;

    @SerializedName("commissionUnit")
    @Expose
    private String commissionUnit;
    @SerializedName("logdesc")
    @Expose
    private String logdesc;
    @SerializedName("commissionAmount")
    @Expose
    private String commissionAmount;

    public String getRs() {
        return rs;
    }

    public String getDesc() {
        return desc;
    }

    public String getCommissionUnit() {
        return commissionUnit;
    }

    public String getLogdesc() {
        return logdesc;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }
}
