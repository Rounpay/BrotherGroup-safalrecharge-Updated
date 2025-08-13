package com.solution.brothergroup.PSA.UI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPanCharge {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("panType")
    @Expose
    private String panType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPanType() {
        return panType;
    }

    public void setPanType(String panType) {
        this.panType = panType;
    }
}

