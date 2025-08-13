package com.solution.brothergroup.ROffer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RofferType implements Serializable {
    @SerializedName("tel")
    @Expose
    private String tel;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("mobileno")
    @Expose
    private String mobileno;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName(value = "records", alternate = "rdata")
    @Expose
    private ArrayList<ROfferObject> records;

    public String getTel() {
        return tel;
    }

    public String getOperator() {
        return operator;
    }

    public int getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<ROfferObject> getRecords() {
        return records;
    }
}
