package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletType {
    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("isActive")
    @Expose
    public boolean isActive;
    @SerializedName("inFundProcess")
    @Expose
    public boolean inFundProcess;
    @SerializedName("isPackageDedectionForRetailor")
    @Expose
    public boolean  isPackageDedectionForRetailor;

    ////////////////////////new wallet response
    @SerializedName("fromWalletID")
    @Expose
    private int fromWalletID;
    @SerializedName("toWalletID")
    @Expose
    private int toWalletID;
    @SerializedName("fromWalletType")
    @Expose
    private String fromWalletType;
    @SerializedName("toWalletType")
    @Expose
    private String toWalletType;

    public int getFromWalletID() {
        return fromWalletID;
    }

    public void setFromWalletID(int fromWalletID) {
        this.fromWalletID = fromWalletID;
    }

    public int getToWalletID() {
        return toWalletID;
    }

    public void setToWalletID(int toWalletID) {
        this.toWalletID = toWalletID;
    }

    public String getFromWalletType() {
        return fromWalletType;
    }

    public void setFromWalletType(String fromWalletType) {
        this.fromWalletType = fromWalletType;
    }

    public String getToWalletType() {
        return toWalletType;
    }

    public void setToWalletType(String toWalletType) {
        this.toWalletType = toWalletType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getActive() {
        return isActive;
    }

    public boolean getInFundProcess() {
        return inFundProcess;
    }

    public boolean isPackageDedectionForRetailor() {
        return isPackageDedectionForRetailor;
    }
}
