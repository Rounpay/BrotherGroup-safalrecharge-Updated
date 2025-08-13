package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRoleForReferralResponse {


    @SerializedName("rechargeReport")
    @Expose
    private Object rechargeReport;
    @SerializedName("dthSubscriptions")
    @Expose
    private Object dthSubscriptions;
    @SerializedName("dmtReport")
    @Expose
    private Object dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    private Object ledgerReport;
    @SerializedName("userDaybookReport")
    @Expose
    private Object userDaybookReport;
    @SerializedName("userDaybookDMRReport")
    @Expose
    private Object userDaybookDMRReport;
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
    private List<ChildRolen> childRoles = null;
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
    private Object companyProfile;
    @SerializedName("targetAchieveds")
    @Expose
    private Object targetAchieveds;
    @SerializedName("aePsDetail")
    @Expose
    private Object aePsDetail;
    @SerializedName("incentiveDetails")
    @Expose
    private Object incentiveDetails;
    @SerializedName("moveToWalletReport")
    @Expose
    private Object moveToWalletReport;
    @SerializedName("fosList")
    @Expose
    private Object fosList;
    @SerializedName("videoList")
    @Expose
    private Object videoList;
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
    @SerializedName("isDTHInfo")
    @Expose
    private Boolean isDTHInfo;
    @SerializedName("isRoffer")
    @Expose
    private Boolean isRoffer;

    public Object getRechargeReport() {
        return rechargeReport;
    }

    public void setRechargeReport(Object rechargeReport) {
        this.rechargeReport = rechargeReport;
    }

    public Object getDthSubscriptions() {
        return dthSubscriptions;
    }

    public void setDthSubscriptions(Object dthSubscriptions) {
        this.dthSubscriptions = dthSubscriptions;
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

    public Object getUserDaybookDMRReport() {
        return userDaybookDMRReport;
    }

    public void setUserDaybookDMRReport(Object userDaybookDMRReport) {
        this.userDaybookDMRReport = userDaybookDMRReport;
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

    public List<ChildRolen> getChildRoles() {
        return childRoles;
    }

    public void setChildRoles(List<ChildRolen> childRoles) {
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

    public Object getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(Object companyProfile) {
        this.companyProfile = companyProfile;
    }

    public Object getTargetAchieveds() {
        return targetAchieveds;
    }

    public void setTargetAchieveds(Object targetAchieveds) {
        this.targetAchieveds = targetAchieveds;
    }

    public Object getAePsDetail() {
        return aePsDetail;
    }

    public void setAePsDetail(Object aePsDetail) {
        this.aePsDetail = aePsDetail;
    }

    public Object getIncentiveDetails() {
        return incentiveDetails;
    }

    public void setIncentiveDetails(Object incentiveDetails) {
        this.incentiveDetails = incentiveDetails;
    }

    public Object getMoveToWalletReport() {
        return moveToWalletReport;
    }

    public void setMoveToWalletReport(Object moveToWalletReport) {
        this.moveToWalletReport = moveToWalletReport;
    }

    public Object getFosList() {
        return fosList;
    }

    public void setFosList(Object fosList) {
        this.fosList = fosList;
    }

    public Object getVideoList() {
        return videoList;
    }

    public void setVideoList(Object videoList) {
        this.videoList = videoList;
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

    public Integer getCheckID() {
        return checkID;
    }

    public void setCheckID(Integer checkID) {
        this.checkID = checkID;
    }

    public Boolean getIsPasswordExpired() {
        return isPasswordExpired;
    }

    public void setIsPasswordExpired(Boolean isPasswordExpired) {
        this.isPasswordExpired = isPasswordExpired;
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

    public Object getSid() {
        return sid;
    }

    public void setSid(Object sid) {
        this.sid = sid;
    }

    public Boolean getIsOTPRequired() {
        return isOTPRequired;
    }

    public void setIsOTPRequired(Boolean isOTPRequired) {
        this.isOTPRequired = isOTPRequired;
    }

    public Integer getGetID() {
        return getID;
    }

    public void setGetID(Integer getID) {
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
}
