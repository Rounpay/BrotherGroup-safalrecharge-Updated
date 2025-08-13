package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillAdditionalInfo implements Serializable {
    @SerializedName("infoName")
    @Expose
    private String infoName;
    @SerializedName("infoValue")
    @Expose
    private String infoValue;

    @SerializedName("amountName")
    @Expose
    public String amountName;
    @SerializedName("amountValue")
    @Expose
    public double amountValue;

    public String getAmountName() {
        return amountName;
    }

    public double getAmountValue() {
        return amountValue;
    }

    public String getInfoName() {
        return infoName;
    }

    public String getInfoValue() {
        return infoValue;
    }

}
