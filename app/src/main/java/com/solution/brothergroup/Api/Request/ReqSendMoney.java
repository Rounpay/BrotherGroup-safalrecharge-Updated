package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReqSendMoney {

    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("beneName")
    @Expose
    private String beneName;

    public ReqSendMoney(String accountNo, String amount, String beneName) {
        this.accountNo = accountNo;
        this.amount = amount;
        this.beneName = beneName;
    }
}
