package com.solution.brothergroup.UPIPayment.UI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.CompanyProfile;

public class CompanyProfileResponse
{

    @SerializedName("rechargeReport")
    @Expose
    private Object rechargeReport;
    @SerializedName("dmtReport")
    @Expose
    private Object dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    private Object ledgerReport;
    @SerializedName("userDaybookReport")
    @Expose
    private Object userDaybookReport;
    @SerializedName("fundDCReport")
    @Expose
    private Object fundDCReport;
    @SerializedName("fundOrderReport")
    @Expose
    private Object fundOrderReport;
    @SerializedName("slabCommissions")
    @Expose
    private Object slabCommissions;
    @SerializedName("slabDetailDisplayLvl")
    @Expose
    private Object slabDetailDisplayLvl;
    @SerializedName("userList")
    @Expose
    private Object userList;
    @SerializedName("childRoles")
    @Expose
    private Object childRoles;
    @SerializedName("fundRequestForApproval")
    @Expose
    private Object fundRequestForApproval;
    @SerializedName("newsContent")
    @Expose
    private Object newsContent;
    @SerializedName("banners")
    @Expose
    private Object banners;
    @SerializedName("notifications")
    @Expose
    private Object notifications;
    @SerializedName("refundLog")
    @Expose
    private Object refundLog;
    @SerializedName("companyProfile")
    @Expose
    private CompanyProfile companyProfile;
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


    public Object getRechargeReport() {
        return rechargeReport;
    }

    public void setRechargeReport(Object rechargeReport) {
        this.rechargeReport = rechargeReport;
    }

    public Object getDmtReport() {
        return dmtReport;
    }

    public void setDmtReport(Object dmtReport) {
        this.dmtReport = dmtReport;
    }

    public Object getLedgerReport() {
        return ledgerReport;
    }

    public void setLedgerReport(Object ledgerReport) {
        this.ledgerReport = ledgerReport;
    }

    public Object getUserDaybookReport() {
        return userDaybookReport;
    }

    public void setUserDaybookReport(Object userDaybookReport) {
        this.userDaybookReport = userDaybookReport;
    }

    public Object getFundDCReport() {
        return fundDCReport;
    }

    public void setFundDCReport(Object fundDCReport) {
        this.fundDCReport = fundDCReport;
    }

    public Object getFundOrderReport() {
        return fundOrderReport;
    }

    public void setFundOrderReport(Object fundOrderReport) {
        this.fundOrderReport = fundOrderReport;
    }

    public Object getSlabCommissions() {
        return slabCommissions;
    }

    public void setSlabCommissions(Object slabCommissions) {
        this.slabCommissions = slabCommissions;
    }

    public Object getSlabDetailDisplayLvl() {
        return slabDetailDisplayLvl;
    }

    public void setSlabDetailDisplayLvl(Object slabDetailDisplayLvl) {
        this.slabDetailDisplayLvl = slabDetailDisplayLvl;
    }

    public Object getUserList() {
        return userList;
    }

    public void setUserList(Object userList) {
        this.userList = userList;
    }

    public Object getChildRoles() {
        return childRoles;
    }

    public void setChildRoles(Object childRoles) {
        this.childRoles = childRoles;
    }

    public Object getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public void setFundRequestForApproval(Object fundRequestForApproval) {
        this.fundRequestForApproval = fundRequestForApproval;
    }

    public Object getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(Object newsContent) {
        this.newsContent = newsContent;
    }

    public Object getBanners() {
        return banners;
    }

    public void setBanners(Object banners) {
        this.banners = banners;
    }

    public Object getNotifications() {
        return notifications;
    }

    public void setNotifications(Object notifications) {
        this.notifications = notifications;
    }

    public Object getRefundLog() {
        return refundLog;
    }

    public void setRefundLog(Object refundLog) {
        this.refundLog = refundLog;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
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

}
