package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.AccountSummary;

public class Summary {

    @SerializedName("rechargeReport")
    @Expose
    private String rechargeReport;
    @SerializedName("dmtReport")
    @Expose
    private String dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    private String ledgerReport;
    @SerializedName("userDaybookReport")
    @Expose
    private String userDaybookReport;
    @SerializedName("fundDCReport")
    @Expose
    private String fundDCReport;
    @SerializedName("fundOrderReport")
    @Expose
    private String fundOrderReport;
    @SerializedName("slabCommissions")
    @Expose
    private String slabCommissions;
    @SerializedName("slabDetailDisplayLvl")
    @Expose
    private String slabDetailDisplayLvl;
    @SerializedName("userList")
    @Expose
    private String userList;
    @SerializedName("childRoles")
    @Expose
    private String childRoles;
    @SerializedName("fundRequestForApproval")
    @Expose
    private String fundRequestForApproval;
    @SerializedName("newsContent")
    @Expose
    private String newsContent;
    @SerializedName("banners")
    @Expose
    private String banners;
    @SerializedName("notifications")
    @Expose
    private String notifications;
    @SerializedName("refundLog")
    @Expose
    private String refundLog;
    @SerializedName("getActiveSerive")
    @Expose
    private String getActiveSerive;
    @SerializedName("companyProfile")
    @Expose
    private String companyProfile;
    @SerializedName("accountSummary")
    @Expose
    private AccountSummary accountSummary;
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

    public String getRechargeReport() {
        return rechargeReport;
    }

    public void setRechargeReport(String rechargeReport) {
        this.rechargeReport = rechargeReport;
    }

    public String getDmtReport() {
        return dmtReport;
    }

    public void setDmtReport(String dmtReport) {
        this.dmtReport = dmtReport;
    }

    public String getLedgerReport() {
        return ledgerReport;
    }

    public void setLedgerReport(String ledgerReport) {
        this.ledgerReport = ledgerReport;
    }

    public String getUserDaybookReport() {
        return userDaybookReport;
    }

    public void setUserDaybookReport(String userDaybookReport) {
        this.userDaybookReport = userDaybookReport;
    }

    public String getFundDCReport() {
        return fundDCReport;
    }

    public void setFundDCReport(String fundDCReport) {
        this.fundDCReport = fundDCReport;
    }

    public String getFundOrderReport() {
        return fundOrderReport;
    }

    public void setFundOrderReport(String fundOrderReport) {
        this.fundOrderReport = fundOrderReport;
    }

    public String getSlabCommissions() {
        return slabCommissions;
    }

    public void setSlabCommissions(String slabCommissions) {
        this.slabCommissions = slabCommissions;
    }

    public String getSlabDetailDisplayLvl() {
        return slabDetailDisplayLvl;
    }

    public void setSlabDetailDisplayLvl(String slabDetailDisplayLvl) {
        this.slabDetailDisplayLvl = slabDetailDisplayLvl;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public String getChildRoles() {
        return childRoles;
    }

    public void setChildRoles(String childRoles) {
        this.childRoles = childRoles;
    }

    public String getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public void setFundRequestForApproval(String fundRequestForApproval) {
        this.fundRequestForApproval = fundRequestForApproval;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getRefundLog() {
        return refundLog;
    }

    public void setRefundLog(String refundLog) {
        this.refundLog = refundLog;
    }

    public String getGetActiveSerive() {
        return getActiveSerive;
    }

    public void setGetActiveSerive(String getActiveSerive) {
        this.getActiveSerive = getActiveSerive;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public AccountSummary getAccountSummary() {
        return accountSummary;
    }

    public void setAccountSummary(AccountSummary accountSummary) {
        this.accountSummary = accountSummary;
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
}
