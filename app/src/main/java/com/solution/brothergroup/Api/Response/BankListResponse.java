package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.BankListObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vishnu on 12-04-2017.
 */

public class BankListResponse implements Serializable {

    private int statuscode;
    private String msg;
    private boolean isVersionValid;
    private boolean isAppValid;

    @SerializedName(value = "bankMasters",alternate = {"banks","aepsBanks"})
    @Expose
    private ArrayList<BankListObject> bankMasters;
    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public int getStatuscode() {
        return statuscode;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<BankListObject> getBankMasters() {
        return bankMasters;
    }

    public void setBankMasters(ArrayList<BankListObject> bankMasters) {
        this.bankMasters = bankMasters;
    }

}
