package com.solution.brothergroup.Aeps.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AEPSResponse {

    @SerializedName("aePsDetail")
    @Expose
    private List<AePsDetail> aePsDetail = null;
    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private Boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private Boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private Integer checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private Boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("emailID")
    @Expose
    private Object emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private Boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    private Boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    private Boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    private Object sid;
    @SerializedName("isOTPRequired")
    @Expose
    private Boolean isOTPRequired;
    @SerializedName("getID")
    @Expose
    private Integer getID;


    public List<AePsDetail> getAePsDetail() {
        return aePsDetail;
    }

    public void setAePsDetail(List<AePsDetail> aePsDetail) {
        this.aePsDetail = aePsDetail;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getVersionValid() {
        return isVersionValid;
    }

    public void setVersionValid(Boolean versionValid) {
        isVersionValid = versionValid;
    }

    public Boolean getAppValid() {
        return isAppValid;
    }

    public void setAppValid(Boolean appValid) {
        isAppValid = appValid;
    }

    public Integer getCheckID() {
        return checkID;
    }

    public void setCheckID(Integer checkID) {
        this.checkID = checkID;
    }

    public Boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public void setEmailID(Object emailID) {
        this.emailID = emailID;
    }

    public Boolean getLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setLookUpFromAPI(Boolean lookUpFromAPI) {
        isLookUpFromAPI = lookUpFromAPI;
    }

    public Boolean getDTHInfoCall() {
        return isDTHInfoCall;
    }

    public void setDTHInfoCall(Boolean DTHInfoCall) {
        isDTHInfoCall = DTHInfoCall;
    }

    public Boolean getShowPDFPlan() {
        return isShowPDFPlan;
    }

    public void setShowPDFPlan(Boolean showPDFPlan) {
        isShowPDFPlan = showPDFPlan;
    }

    public Object getSid() {
        return sid;
    }

    public void setSid(Object sid) {
        this.sid = sid;
    }

    public Boolean getOTPRequired() {
        return isOTPRequired;
    }

    public void setOTPRequired(Boolean OTPRequired) {
        isOTPRequired = OTPRequired;
    }

    public Integer getGetID() {
        return getID;
    }

    public void setGetID(Integer getID) {
        this.getID = getID;
    }
}
