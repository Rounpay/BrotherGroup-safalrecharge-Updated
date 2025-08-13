package com.solution.brothergroup.Api.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Object.ActiveSerive;
import com.solution.brothergroup.Api.Object.Banners;
import com.solution.brothergroup.Api.Object.CommissionDisplay;
import com.solution.brothergroup.Api.Object.CompanyProfile;
import com.solution.brothergroup.Api.Object.FundRequestForApproval;
import com.solution.brothergroup.Api.Object.IncentiveDetails;
import com.solution.brothergroup.Api.Object.NewsContent;
import com.solution.brothergroup.Api.Object.NotificationData;
import com.solution.brothergroup.Api.Object.PGModelForApp;

import com.solution.brothergroup.Api.Object.RealLapuCommissionSlab;
import com.solution.brothergroup.Api.Object.RefundLog;
import com.solution.brothergroup.Api.Object.TargetAchieved;
import com.solution.brothergroup.Api.Object.UserDaybookDMRReport;
import com.solution.brothergroup.Api.Object.UserDaybookReport;
import com.solution.brothergroup.Api.Object.UserList;
import com.solution.brothergroup.Authentication.ChildRole;
import com.solution.brothergroup.Api.Object.FosList;
import com.solution.brothergroup.AddMoney.modelClass.PaymentGatewayType;

import java.util.ArrayList;
import java.util.List;

public class AppUserListResponse {
    @SerializedName("refferalContent")
    @Expose
    public String refferalContent;
    @SerializedName("refferalImage")
    @Expose
    public ArrayList<Banners> refferalImage;
    @SerializedName("appLogoUrl")
    @Expose
    public String appLogoUrl;
    @SerializedName("pGModelForApp")
    @Expose
    public PGModelForApp pGModelForApp = null;
    @SerializedName("pGs")
    @Expose
    public ArrayList<PaymentGatewayType> pGs = null;
    @SerializedName("incentiveDetails")
    @Expose
    public ArrayList<IncentiveDetails> incentiveDetails = null;
    @SerializedName("slabDetail")
    @Expose
    public RealLapuCommissionSlab realLapuCommissionSlab;

    @SerializedName("commissionDisplay")
    @Expose
    public CommissionDisplay commissionDisplay;

    @SerializedName("fosList")
    @Expose
    private FosList fosList;
    @SerializedName("userListRoleWise")
    @Expose
    public ArrayList<UserListRoleWise> userListRoleWise = null;

    public FosList getFosList() {
        return fosList;
    }

    public void setFosList(FosList fosList) {
        this.fosList = fosList;
    }

    @SerializedName("refundLog")
    @Expose
    public List<RefundLog> refundLog = null;
    @SerializedName("companyProfile")
    @Expose
    public CompanyProfile companyProfile = null;
    @SerializedName("getActiveSerive")
    @Expose
    public List<ActiveSerive> activeSerive = null;
    @SerializedName("notifications")
    @Expose
    public List<NotificationData> notifications = null;

    @SerializedName("targetAchieveds")
    @Expose
    public List<TargetAchieved> targetAchieveds = null;
    @SerializedName("userDaybookReport")
    @Expose
    public List<UserDaybookReport> userDaybookReport = null;
    @SerializedName("userDaybookDMRReport")
    @Expose
    public List<UserDaybookDMRReport> userDaybookDMTReport = null;
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
    public  ArrayList<ChildRole> childRoles = null;
    @SerializedName("fundRequestForApproval")
    @Expose
    public List<FundRequestForApproval> fundRequestForApproval = null;
    @SerializedName("banners")
    @Expose
    public List<Banners> banners = null;

    @SerializedName("newsContent")
    @Expose
    public NewsContent newsContent = null;

    @SerializedName("statuscode")
    @Expose
    public int statuscode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("isVersionValid")
    @Expose
    public boolean isVersionValid;
    @SerializedName("isAppValid")
    @Expose
    public boolean isAppValid;

    public PGModelForApp getpGModelForApp() {
        return pGModelForApp;
    }

    public List<RefundLog> getRefundLog() {
        return refundLog;
    }

    public CommissionDisplay getCommissionDisplay() {
        return commissionDisplay;
    }

    public boolean isVersionValid() {
        return isVersionValid;
    }

    public boolean isAppValid() {
        return isAppValid;
    }

    public RealLapuCommissionSlab getRealLapuCommissionSlab() {
        return realLapuCommissionSlab;
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

    public   List<ChildRole> getChildRoles() {
        return childRoles;
    }

    public Integer getStatuscode() {
        return statuscode;
    }

    public String getMsg() {
        return msg;
    }

    public List<TargetAchieved> getTargetAchieveds() {
        return targetAchieveds;
    }

    public boolean getVersionValid() {
        return isVersionValid;
    }

    public List<FundRequestForApproval> getFundRequestForApproval() {
        return fundRequestForApproval;
    }

    public List<UserDaybookReport> getUserDaybookReport() {
        return userDaybookReport;
    }

    public List<UserDaybookDMRReport> getUserDaybookDMTReport() {
        return userDaybookDMTReport;
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

    public List<ActiveSerive> getActiveSerive() {
        return activeSerive;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }


    public ArrayList<PaymentGatewayType> getpGs() {
        return pGs;
    }

    public ArrayList<IncentiveDetails> getIncentiveDetails() {
        return incentiveDetails;
    }



    @SerializedName("videoList")
    @Expose
    private ArrayList<VideoList> videoList = null;

    public String getRefferalContent() {
        return refferalContent;
    }

    public ArrayList<Banners> getRefferalImage() {
        return refferalImage;
    }

    public ArrayList<VideoList> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<VideoList> videoList) {
        this.videoList = videoList;
    }

    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    public ArrayList<UserListRoleWise> getUserListRoleWise() {
        return userListRoleWise;
    }
}
