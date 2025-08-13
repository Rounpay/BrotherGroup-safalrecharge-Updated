package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.AssignedOpType;
import com.solution.brothergroup.Api.Object.Banners;
import com.solution.brothergroup.Api.Object.CompanyProfile;
import com.solution.brothergroup.Api.Object.DataOpType;
import com.solution.brothergroup.Api.Object.FundRequestForApproval;
import com.solution.brothergroup.Api.Object.NewsContent;
import com.solution.brothergroup.Api.Object.NotificationData;
import com.solution.brothergroup.Api.Object.RefundLog;
import com.solution.brothergroup.Api.Object.UserDaybookReport;
import com.solution.brothergroup.Api.Object.UserList;


import java.util.List;

public class OpTypeResponse {
    @SerializedName("getActiveSerive")
    @Expose
    public List<AssignedOpType> activeSerive = null;
    @SerializedName("refundLog")
    @Expose
    public List<RefundLog> refundLog = null;
    @SerializedName("companyProfile")
    @Expose
    public CompanyProfile companyProfile = null;
    @SerializedName("notifications")
    @Expose
    public List<NotificationData> notifications = null;
    @SerializedName("userDaybookReport")
    @Expose
    public List<UserDaybookReport> userDaybookReport = null;
    @SerializedName("rechargeReport")
    @Expose
    public Object rechargeReport;
    @SerializedName("dmtReport")
    @Expose
    public Object dmtReport;
    @SerializedName("ledgerReport")
    @Expose
    public Object ledgerReport;
    @SerializedName("fundDCReport")
    @Expose
    public Object fundDCReport;
    @SerializedName("fundOrderReport")
    @Expose
    public Object fundOrderReport;
    @SerializedName("slabCommissions")
    @Expose
    public Object slabCommissions;
    @SerializedName("userList")
    @Expose
    public List<UserList> userList = null;
    @SerializedName("childRoles")
    @Expose
    public List<ChildRoles> childRoles = null;
    @SerializedName("fundRequestForApproval")
    @Expose
    public List<FundRequestForApproval> fundRequestForApproval = null;
    @SerializedName("banners")
    @Expose
    public List<Banners> banners = null;
    @SerializedName("newsContent")
    @Expose
    public NewsContent newsContent = null;
    @SerializedName("data")
    @Expose
    private DataOpType data;
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
    private String checkID;
    @SerializedName("isPasswordExpired")
    @Expose
    private boolean isPasswordExpired;
    @SerializedName("isAddMoneyEnable")
    @Expose
    private boolean isAddMoneyEnable;

    @SerializedName("isPaymentGatway")
    @Expose
    private boolean isPaymentGatway;
    @SerializedName("isUPI")
    @Expose
    private boolean isUPI;
    @SerializedName("isUPIQR")
    @Expose
    private boolean isUPIQR;

    @SerializedName("isDMTWithPipe")
    @Expose
    private boolean isDMTWithPipe;
    @SerializedName("isECollectEnable")
    @Expose
    boolean isECollectEnable;

    public void setDMTWithPipe(boolean DMTWithPipe) {
        isDMTWithPipe = DMTWithPipe;
    }

    public boolean isDMTWithPipe() {
        return isDMTWithPipe;
    }

    public boolean getIsVersionValid() {
        return isVersionValid;
    }

    public boolean getIsAppValid() {
        return isAppValid;
    }

    public DataOpType getData() {
        return data;
    }

    public void setData(DataOpType data) {
        this.data = data;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public boolean getAddMoneyEnable() {
        return isAddMoneyEnable;
    }

    public void setAddMoneyEnable(boolean addMoneyEnable) {
        isAddMoneyEnable = addMoneyEnable;
    }

    public boolean getPaymentGatway() {
        return isPaymentGatway;
    }

    public void setPaymentGatway(boolean paymentGatway) {
        isPaymentGatway = paymentGatway;
    }

    public boolean getUPI() {
        return isUPI;
    }

    public boolean getUPIQR() {
        return isUPIQR;
    }

    public void setUPI(boolean UPI) {
        isUPI = UPI;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RefundLog> getRefundLog() {
        return refundLog;
    }

    public Object getRechargeReport() {
        return rechargeReport;
    }

    public Object getDmtReport() {
        return dmtReport;
    }

    public Object getLedgerReport() {
        return ledgerReport;
    }

    public Object getFundDCReport() {
        return fundDCReport;
    }

    public Object getFundOrderReport() {
        return fundOrderReport;
    }

    public Object getSlabCommissions() {
        return slabCommissions;
    }

    public List<UserList> getUserList() {
        return userList;
    }

    public List<ChildRoles> getChildRoles() {
        return childRoles;
    }


    public boolean getVersionValid() {
        return isVersionValid;
    }

    public List<FundRequestForApproval> getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public boolean isAddMoneyEnable() {
        return isAddMoneyEnable;
    }

    public List<UserDaybookReport> getUserDaybookReport() {
        return userDaybookReport;
    }

    public List<Banners> getBanners() {
        return banners;
    }

    public NewsContent getNewsContent() {
        return newsContent;
    }

    public boolean getAppValid() {
        return isAppValid;
    }

    public List<NotificationData> getNotifications() {
        return notifications;
    }


    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public boolean getECollectEnable() {
        return isECollectEnable;
    }
}
