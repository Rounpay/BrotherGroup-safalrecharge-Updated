package com.solution.brothergroup.AddMoney.modelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GatwayTransactionResponse {

    @SerializedName("pGs")
    @Expose
    public ArrayList<PaymentGatewayType> pGs = null;
    @SerializedName("pGModelForApp")
    @Expose
    private PGModelForApp pGModelForApp;
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
    private Integer getID;
    @SerializedName("isDTHInfo")
    @Expose
    private Boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private Boolean isRoffer;

    public ArrayList<PaymentGatewayType> getpGs() {
        return pGs;
    }

    public void setpGs(ArrayList<PaymentGatewayType> pGs) {
        this.pGs = pGs;
    }

    public PGModelForApp getpGModelForApp() {
        return pGModelForApp;
    }

    public void setpGModelForApp(PGModelForApp pGModelForApp) {
        this.pGModelForApp = pGModelForApp;
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

    public Integer getGetID() {
        return getID;
    }

    public void setGetID(Integer getID) {
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
}
