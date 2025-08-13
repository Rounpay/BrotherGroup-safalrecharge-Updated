package com.solution.brothergroup.Fragments;

public class DFStatusResponse {

    String refID;
    boolean isOTPRequired;
    Integer statuscode;
    String msg;
    boolean isVersionValid;
    boolean isAppValid;
    Integer checkID;
    boolean isPasswordExpired;
    String mobileNo;
    String emailID;

    public String getRefID() {
        return refID;
    }

    public boolean isOTPRequired() {
        return isOTPRequired;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public Integer getCheckID() {
        return checkID;
    }

    public boolean isPasswordExpired() {
        return isPasswordExpired;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getEmailID() {
        return emailID;
    }
}

