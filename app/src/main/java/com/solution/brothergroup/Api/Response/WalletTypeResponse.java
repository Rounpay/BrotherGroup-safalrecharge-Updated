package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.WalletType;

import java.util.List;

public class WalletTypeResponse {
    @SerializedName("walletTypes")
    @Expose
    public List<WalletType> walletTypes = null;
    @SerializedName("moveToWalletMappings")
    @Expose
    public List<WalletType> moveToWalletMappings = null;
    @SerializedName("statuscode")
    @Expose
    public Integer statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public Boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public Boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    public Integer checkID;

    public List<WalletType> getMoveToWalletMappings() {
        return moveToWalletMappings;
    }

    public List<WalletType> getWalletTypes() {
        return walletTypes;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public Boolean getVersionValid() {
        return isVersionValid;
    }

    public Boolean getAppValid() {
        return isAppValid;
    }

    public Integer getCheckID() {
        return checkID;
    }
}
