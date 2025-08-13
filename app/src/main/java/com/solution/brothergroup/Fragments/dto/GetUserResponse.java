package com.solution.brothergroup.Fragments.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetUserResponse implements Serializable {
    @SerializedName("userInfo")
    @Expose
    public UserDetailInfo userInfo;
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
    @SerializedName("isPasswordExpired")
    @Expose
    public Boolean isPasswordExpired;
    @SerializedName("checkID")
    @Expose
    public Integer checkID;

    public UserDetailInfo getUserInfo() {
        return userInfo;
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

    public Boolean getPasswordExpired() {
        return isPasswordExpired;
    }
}
