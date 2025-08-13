package com.solution.brothergroup.DthCustomerInfo.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DTHInfoData implements Serializable {
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName(value = "status", alternate = "error")
    @Expose
    private int status;
    @SerializedName("records")
    @Expose
    private ArrayList<DTHInfoRecords> records;
    @SerializedName("data")
    @Expose
    private DTHInfoRecords data;

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<DTHInfoRecords> getRecords() {
        return records;
    }

    public DTHInfoRecords getData() {
        return data;
    }
}
