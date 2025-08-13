package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.AreaMaster;

import java.io.Serializable;
import java.util.ArrayList;

public class AppGetAMResponse implements Serializable
{
    @SerializedName("areaMaster")
    @Expose
    public ArrayList<AreaMaster> areaMaster = null;
    @SerializedName("statuscode")
    @Expose
    private int statuscode;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("isVersionValid")
    @Expose
    private boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    private boolean isAppValid;
    @SerializedName("checkID")
    @Expose
    private int checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("isLookUpFromAPI")
    @Expose
    private boolean isLookUpFromAPI;
    @SerializedName("isDTHInfoCall")
    @Expose
    private boolean isDTHInfoCall;
    @SerializedName("isShowPDFPlan")
    @Expose
    private boolean isShowPDFPlan;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("isOTPRequired")
    @Expose
    private boolean isOTPRequired;
    @SerializedName("isResendAvailable")
    @Expose
    private boolean isResendAvailable;
    @SerializedName("getID")
    @Expose
    private int getID;
    @SerializedName("isDTHInfo")
    @Expose
    private boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private boolean isRoffer;
    @SerializedName("isGreen")
    @Expose
    private boolean isGreen;

    public ArrayList<AreaMaster> getAreaMaster() {
        return areaMaster;
    }

    public void setAreaMaster(ArrayList<AreaMaster> areaMaster) {
        this.areaMaster = areaMaster;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public void setIsVersionValid(boolean isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public void setIsAppValid(boolean isAppValid) {
        this.isAppValid = isAppValid;
    }

    public int getCheckID() {
        return checkID;
    }

    public void setCheckID(int checkID) {
        this.checkID = checkID;
    }

    public boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Object getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public boolean getIsLookUpFromAPI() {
        return isLookUpFromAPI;
    }

    public void setIsLookUpFromAPI(boolean isLookUpFromAPI) {
        this.isLookUpFromAPI = isLookUpFromAPI;
    }

    public boolean getIsDTHInfoCall() {
        return isDTHInfoCall;
    }

    public void setIsDTHInfoCall(boolean isDTHInfoCall) {
        this.isDTHInfoCall = isDTHInfoCall;
    }

    public boolean getIsShowPDFPlan() {
        return isShowPDFPlan;
    }

    public void setIsShowPDFPlan(boolean isShowPDFPlan) {
        this.isShowPDFPlan = isShowPDFPlan;
    }

    public Object getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public boolean getIsOTPRequired() {
        return isOTPRequired;
    }

    public void setIsOTPRequired(boolean isOTPRequired) {
        this.isOTPRequired = isOTPRequired;
    }

    public boolean getIsResendAvailable() {
        return isResendAvailable;
    }

    public void setIsResendAvailable(boolean isResendAvailable) {
        this.isResendAvailable = isResendAvailable;
    }

    public int getGetID() {
        return getID;
    }

    public void setGetID(int getID) {
        this.getID = getID;
    }

    public boolean getIsDTHInfo() {
        return isDTHInfo;
    }

    public void setIsDTHInfo(boolean isDTHInfo) {
        this.isDTHInfo = isDTHInfo;
    }

    public boolean getIsRoffer() {
        return isRoffer;
    }

    public void setIsRoffer(boolean isRoffer) {
        this.isRoffer = isRoffer;
    }

    public boolean getIsGreen() {
        return isGreen;
    }

    public void setIsGreen(boolean isGreen) {
        this.isGreen = isGreen;
    }

}