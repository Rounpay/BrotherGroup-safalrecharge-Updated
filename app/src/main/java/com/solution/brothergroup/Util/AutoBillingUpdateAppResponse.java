package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AutoBillingUpdateAppResponse implements Serializable
{

    @SerializedName("statuscode")
    @Expose
    private String statuscode;
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
    private String checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private Boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
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
    private String sid;
    @SerializedName("isOTPRequired")
    @Expose
    private Boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private Boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    private String getID;
    @SerializedName("isDTHInfo")
    @Expose
    private Boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private Boolean isRoffer;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
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

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public Boolean getPasswordExpired() {
        return isPasswordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        isPasswordExpired = passwordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Boolean getOTPRequired() {
        return isOTPRequired;
    }

    public void setOTPRequired(Boolean OTPRequired) {
        isOTPRequired = OTPRequired;
    }

    public Boolean getResendAvailable() {
        return isResendAvailable;
    }

    public void setResendAvailable(Boolean resendAvailable) {
        isResendAvailable = resendAvailable;
    }

    public String getGetID() {
        return getID;
    }

    public void setGetID(String getID) {
        this.getID = getID;
    }

    public Boolean getDTHInfo() {
        return isDTHInfo;
    }

    public void setDTHInfo(Boolean DTHInfo) {
        isDTHInfo = DTHInfo;
    }

    public Boolean getRoffer() {
        return isRoffer;
    }

    public void setRoffer(Boolean roffer) {
        isRoffer = roffer;
    }

    /*  @SerializedName("statuscode")
    @Expose
    private String statuscode;
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
    private String checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private Boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
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
    private String sid;
    @SerializedName("isOTPRequired")
    @Expose
    private Boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private Boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    private String getID;
    @SerializedName("isDTHInfo")
    @Expose
    private Boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private Boolean isRoffer;

    public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(Boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public Boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(Boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public String getCheckID() {
        return checkID;
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID;
    }

    public Boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(Boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public Boolean getIsLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setIsLookUpFromAPI(Boolean isLookUpFromAPI) {
        this.isLookUpFromAPI = isLookUpFromAPI;
    }

    public Boolean getIsDTHInfoCall() {
        return isDTHInfoCall;
    }

    public void setIsDTHInfoCall(Boolean isDTHInfoCall) {
        this.isDTHInfoCall = isDTHInfoCall;
    }

    public Boolean getIsShowPDFPlan() {
        return isShowPDFPlan;
    }

    public void setIsShowPDFPlan(Boolean isShowPDFPlan) {
        this.isShowPDFPlan = isShowPDFPlan;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Boolean getIsOTPRequired() {
        return isOTPRequired;
    }

    public void setIsOTPRequired(Boolean isOTPRequired) {
        this.isOTPRequired = isOTPRequired;
    }

    public Boolean getIsResendAvailable() {
        return isResendAvailable;
    }

    public void setIsResendAvailable(Boolean isResendAvailable) {
        this.isResendAvailable = isResendAvailable;
    }

    public String getGetID() {
        return getID;
    }

    public void setGetID(String getID) {
        this.getID = getID;
    }

    public Boolean getIsDTHInfo() {
        return isDTHInfo;
    }

    public void setIsDTHInfo(Boolean isDTHInfo) {
        this.isDTHInfo = isDTHInfo;
    }

    public Boolean getIsRoffer() {
        return isRoffer;
    }

    public void setIsRoffer(Boolean isRoffer) {
        this.isRoffer = isRoffer;
    }

    public abstract void onResponse(Call<AutoBillingUpdateAppResponse> call, retrofit2.Response<AutoBillingUpdateAppResponse> response);*/


}
