package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateSenderResponse {

    private boolean isSenderNotExists;
    private boolean isEKYCAvailable;
    private boolean isOTPGenerated;
    private boolean isOTPRequired;
    private double remainingLimit;
    private double availbleLimit;
    private String senderName;
    private String senderBalance;
    private String statuscode;
    private String msg;
    private String isVersionValid;
    private String isAppValid;
    @SerializedName(value = "sid",alternate = "SID")
    @Expose
    private String  sid;


    public boolean getIsSenderNotExists() {
        return isSenderNotExists;
    }

    public void setIsSenderNotExists(boolean isSenderNotExists) {
        this.isSenderNotExists = isSenderNotExists;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }



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


    public String getSenderBalance() {
        return senderBalance;
    }

    public void setSenderBalance(String senderBalance) {
        this.senderBalance = senderBalance;
    }


    public String getIsVersionValid() {
        return isVersionValid;
    }

    public String getIsAppValid() {
        return isAppValid;
    }

    public boolean isSenderNotExists() {
        return isSenderNotExists;
    }

    public void setSenderNotExists(boolean senderNotExists) {
        isSenderNotExists = senderNotExists;
    }

    public boolean isEKYCAvailable() {
        return isEKYCAvailable;
    }

    public void setEKYCAvailable(boolean EKYCAvailable) {
        isEKYCAvailable = EKYCAvailable;
    }

    public boolean isOTPGenerated() {
        return isOTPGenerated;
    }

    public void setOTPGenerated(boolean OTPGenerated) {
        isOTPGenerated = OTPGenerated;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public void setOTPRequired(boolean OTPRequired) {
        isOTPRequired = OTPRequired;
    }

    public double getRemainingLimit() {
        return remainingLimit;
    }

    public void setRemainingLimit(double remainingLimit) {
        this.remainingLimit = remainingLimit;
    }

    public double getAvailbleLimit() {
        return availbleLimit;
    }

    public void setAvailbleLimit(double availbleLimit) {
        this.availbleLimit = availbleLimit;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setIsVersionValid(String isVersionValid) {
        this.isVersionValid = isVersionValid;
    }

    public void setIsAppValid(String isAppValid) {
        this.isAppValid = isAppValid;
    }
}
