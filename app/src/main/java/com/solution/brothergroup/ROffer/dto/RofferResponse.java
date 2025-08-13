package com.solution.brothergroup.ROffer.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RofferResponse implements Serializable {

    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RofferType data;

    @SerializedName("dataPA")
    @Expose
    private RofferType dataPA;
    @SerializedName("rofferData")
    @Expose
    private ArrayList<ROfferObject> rofferData;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;

    public int getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public RofferType getData() {
        return data;
    }

    public ArrayList<ROfferObject> getRofferData() {
        return rofferData;
    }

    public RofferType getDataPA() {
        return dataPA;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }
}
