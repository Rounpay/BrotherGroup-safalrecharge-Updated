package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PaymentMode implements Serializable {


    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("bankID")
    @Expose
    public int bankID;
    @SerializedName("modeID")
    @Expose
    public int modeID;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("mode")
    @Expose
    public String mode;
    @SerializedName("isTransactionIdAuto")
    @Expose
    public boolean isTransactionIdAuto;
    @SerializedName("isAccountHolderRequired")
    @Expose
    public boolean isAccountHolderRequired;
    @SerializedName("isChequeNoRequired")
    @Expose
    public boolean isChequeNoRequired;
    @SerializedName("isCardNumberRequired")
    @Expose
    public boolean isCardNumberRequired;
    @SerializedName("isMobileNoRequired")
    @Expose
    public boolean isMobileNoRequired;
    @SerializedName("isBranchRequired")
    @Expose
    public boolean isBranchRequired;
    @SerializedName("isUPIID")
    @Expose
    public boolean isUPIID;
    @SerializedName("cid")
    @Expose
    public String cid;

    public int getId() {
        return id;
    }

    public int getBankID() {
        return bankID;
    }

    public int getModeID() {
        return modeID;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getMode() {
        return mode;
    }

    public boolean isTransactionIdAuto() {
        return isTransactionIdAuto;
    }

    public boolean isAccountHolderRequired() {
        return isAccountHolderRequired;
    }

    public boolean isChequeNoRequired() {
        return isChequeNoRequired;
    }

    public boolean isCardNumberRequired() {
        return isCardNumberRequired;
    }

    public boolean isMobileNoRequired() {
        return isMobileNoRequired;
    }

    public boolean isBranchRequired() {
        return isBranchRequired;
    }

    public boolean isUPIID() {
        return isUPIID;
    }

    public String getCid() {
        return cid;
    }
}