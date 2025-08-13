package com.solution.brothergroup.Authentication.dto;

/**
 * Created by Vishnu on 08-04-2017.
 */

public class LoginData {

    private String OTP;
    private String SessionID;
    private String UName;
    private String UMail;
    private String UMobile;
    private String IsExist;
    private String PinPassword;
    private String PrepaidWallet;
    private String UtilityWallet;
    private String Key;
    private String RoleId;
    private String BusinessModuleID;
    private String DeviceStatus;
    private String supportEmail;
    private String supportNumber;
    private String icon;

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportNumber() {
        return supportNumber;
    }

    public void setSupportNumber(String supportNumber) {
        this.supportNumber = supportNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBusinessModuleID() {
        return BusinessModuleID;
    }

    public void setBusinessModuleID(String businessModuleID) {
        BusinessModuleID = businessModuleID;
    }

    public String getDeviceStatus() {
        return DeviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        DeviceStatus = deviceStatus;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String sessionID) {
        SessionID = sessionID;
    }

    public String getUMail() {
        return UMail;
    }

    public void setUMail(String UMail) {
        this.UMail = UMail;
    }

    public String getUMobile() {
        return UMobile;
    }

    public void setUMobile(String UMobile) {
        this.UMobile = UMobile;
    }

    public String getIsExist() {
        return IsExist;
    }

    public void setIsExist(String isExist) {
        IsExist = isExist;
    }

    public String getPinPassword() {
        return PinPassword;
    }

    public void setPinPassword(String pinPassword) {
        PinPassword = pinPassword;
    }

    public String getPrepaidWallet() {
        return PrepaidWallet;
    }

    public void setPrepaidWallet(String prepaidWallet) {
        PrepaidWallet = prepaidWallet;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getUtilityWallet() {
        return UtilityWallet;
    }

    public void setUtilityWallet(String utilityWallet) {
        UtilityWallet = utilityWallet;
    }
}
