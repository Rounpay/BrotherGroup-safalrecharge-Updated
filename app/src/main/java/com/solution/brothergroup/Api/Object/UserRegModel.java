package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegModel {
    @SerializedName("userInfo")
    @Expose
    public UserInfo userInfo;
    @SerializedName("roleSlab")
    @Expose
    public RoleSlab roleSlab;
    @SerializedName("input")
    @Expose
    public String input;
    @SerializedName("isError")
    @Expose
    public Boolean isError;
    @SerializedName("token")
    @Expose
    public String token;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public RoleSlab getRoleSlab() {
        return roleSlab;
    }

    public String getInput() {
        return input;
    }

    public Boolean getError() {
        return isError;
    }

    public String getToken() {
        return token;
    }
}
