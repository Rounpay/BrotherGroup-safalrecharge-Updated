package com.solution.brothergroup.AddMoney.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Request.BasicRequest;

public class InitiateUPIRequest extends BasicRequest {

    @SerializedName("walletID")
    @Expose
    private Integer walletID;
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("upiid")
    @Expose
    private String upiid;

    public Integer getWalletID() {
        return walletID;
    }

    public void setWalletID(Integer walletID) {
        this.walletID = walletID;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpiid() {
        return upiid;
    }

    public void setUpiid(String upiid) {
        this.upiid = upiid;
    }
}
