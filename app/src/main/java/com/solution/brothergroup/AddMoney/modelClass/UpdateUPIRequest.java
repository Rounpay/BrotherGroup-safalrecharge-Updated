package com.solution.brothergroup.AddMoney.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Request.BasicRequest;


public class UpdateUPIRequest extends BasicRequest {

    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("bankStatus")
    @Expose
    private String bankStatus;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(String bankStatus) {
        this.bankStatus = bankStatus;
    }
}
