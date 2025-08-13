package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.BalanceData;

import java.io.Serializable;

public class BalanceResponse implements Serializable {
    @SerializedName("isBulkQRGeneration")
    @Expose
    public boolean isBulkQRGeneration;
    @SerializedName("data")
    @Expose
    public BalanceData balanceData;
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
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName(value = "IsDTHInfoCall",alternate = "isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    boolean isFlatCommission;
    @SerializedName(value = "IsShowPDFPlan",alternate = "isShowPDFPlan")
    @Expose
    private boolean isShowPDFPlan;

    @SerializedName("popup")
    @Expose
    private String popup;

  /*  @SerializedName(value = "IsHeavyRefresh",alternate = "isHeavyRefresh")
    @Expose
    private boolean isHeavyRefresh;*/
    @SerializedName(value = "IsRoffer",alternate = "isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName(value = "IsDTHInfo",alternate = "isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    public BalanceData getBalanceData() {
        return balanceData;
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

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public boolean isBulkQRGeneration() {
        return isBulkQRGeneration;
    }

    public boolean isLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public boolean isDTHInfoCall() {
        return isDTHInfoCall;
    }

   /* public boolean isHeavyRefresh() {
        return isHeavyRefresh;
    }*/

    public boolean isFlatCommission() {
        return isFlatCommission;
    }

    public boolean isRoffer() {
        return isRoffer;
    }

    public boolean isDTHInfo() {
        return isDTHInfo;
    }

    public boolean isShowPDFPlan() {
        return isShowPDFPlan;
    }

    public String getPopup() {
        return popup;
    }
}
