package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("isRealAPI")
    @Expose
    public Boolean isRealAPI;
    @SerializedName("resultCode")
    @Expose
    public Integer resultCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("userID")
    @Expose
    public Integer userID;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("outletName")
    @Expose
    public String outletName;
    @SerializedName("emailID")
    @Expose
    public String emailID;
    @SerializedName("roleID")
    @Expose
    public Integer roleID;
    @SerializedName("role")
    @Expose
    public Object role;
    @SerializedName("referalID")
    @Expose
    public Integer referalID;
    @SerializedName("slabID")
    @Expose
    public Integer slabID;
    @SerializedName("isGSTApplicable")
    @Expose
    public Boolean isGSTApplicable;
    @SerializedName("isTDSApplicable")
    @Expose
    public Boolean isTDSApplicable;
    @SerializedName("isVirtual")
    @Expose
    public Boolean isVirtual;
    @SerializedName("isWebsite")
    @Expose
    public Boolean isWebsite;
    @SerializedName("isAdminDefined")
    @Expose
    public Boolean isAdminDefined;
    @SerializedName("isSurchargeGST")
    @Expose
    public Boolean isSurchargeGST;
    @SerializedName("prefix")
    @Expose
    public Object prefix;
    @SerializedName("outletID")
    @Expose
    public Integer outletID;
    @SerializedName("pincode")
    @Expose
    public Object pincode;

    public Boolean getRealAPI() {
        return isRealAPI;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getEmailID() {
        return emailID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public Object getRole() {
        return role;
    }

    public Integer getReferalID() {
        return referalID;
    }

    public Integer getSlabID() {
        return slabID;
    }

    public Boolean getGSTApplicable() {
        return isGSTApplicable;
    }

    public Boolean getTDSApplicable() {
        return isTDSApplicable;
    }

    public Boolean getVirtual() {
        return isVirtual;
    }

    public Boolean getWebsite() {
        return isWebsite;
    }

    public Boolean getAdminDefined() {
        return isAdminDefined;
    }

    public Boolean getSurchargeGST() {
        return isSurchargeGST;
    }

    public Object getPrefix() {
        return prefix;
    }

    public Integer getOutletID() {
        return outletID;
    }

    public Object getPincode() {
        return pincode;
    }
}
