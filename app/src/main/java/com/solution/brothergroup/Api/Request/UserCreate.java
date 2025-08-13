package com.solution.brothergroup.Api.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCreate {
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("websiteName")
    @Expose
    public String websiteName;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("isRealAPI")
    @Expose
    public Boolean isRealAPI;
    @SerializedName("commRate")
    @Expose
    public Double commRate;
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
    /* @SerializedName("role")
     @Expose
     public String role;*/
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
    @SerializedName("pincode")
    @Expose
    public String pincode;

    public UserCreate(String address, String password, String websiteName, String token, Boolean isRealAPI, Double commRate, String mobileNo, String name, String outletName, String emailID, Integer roleID, Integer slabID, Boolean isGSTApplicable, Boolean isTDSApplicable, Boolean isVirtual, Boolean isWebsite, Boolean isAdminDefined, String pincode) {
        this.address = address;
        this.password = password;
        this.websiteName = websiteName;
        this.token = token;
        this.isRealAPI = isRealAPI;
        this.commRate = commRate;
        this.mobileNo = mobileNo;
        this.name = name;
        this.outletName = outletName;
        this.emailID = emailID;
        this.roleID = roleID;
        /* this.role = role;*/
        this.slabID = slabID;
        this.isGSTApplicable = isGSTApplicable;
        this.isTDSApplicable = isTDSApplicable;
        this.isVirtual = isVirtual;
        this.isWebsite = isWebsite;
        this.isAdminDefined = isAdminDefined;
        this.pincode = pincode;
    }
}
