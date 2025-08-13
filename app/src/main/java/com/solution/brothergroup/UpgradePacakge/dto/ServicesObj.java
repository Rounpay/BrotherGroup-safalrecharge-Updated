package com.solution.brothergroup.UpgradePacakge.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesObj {
    @SerializedName("serviceID")
    @Expose
    private Integer serviceID;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("packageId")
    @Expose
    private String packageId;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isServiceActive")
    @Expose
    private Boolean isServiceActive;
    @SerializedName("isVisible")
    @Expose
    private Boolean isVisible;
    @SerializedName("sCode")
    @Expose
    private Object sCode;
    @SerializedName("walletTypeID")
    @Expose
    private Integer walletTypeID;
    @SerializedName("selfAssigned")
    @Expose
    private Boolean selfAssigned;

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getServiceActive() {
        return isServiceActive;
    }

    public void setServiceActive(Boolean serviceActive) {
        isServiceActive = serviceActive;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Object getsCode() {
        return sCode;
    }

    public void setsCode(Object sCode) {
        this.sCode = sCode;
    }

    public Integer getWalletTypeID() {
        return walletTypeID;
    }

    public void setWalletTypeID(Integer walletTypeID) {
        this.walletTypeID = walletTypeID;
    }

    public Boolean getSelfAssigned() {
        return selfAssigned;
    }

    public void setSelfAssigned(Boolean selfAssigned) {
        this.selfAssigned = selfAssigned;
    }
}
