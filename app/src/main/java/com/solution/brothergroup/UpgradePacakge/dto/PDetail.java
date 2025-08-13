package com.solution.brothergroup.UpgradePacakge.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PDetail {

    @SerializedName("packageId")
    @Expose
    private String packageId;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("packageCost")
    @Expose
    private String packageCost;
    @SerializedName("commission")
    @Expose
    private String commission;
    @SerializedName("isDefault")
    @Expose
    private Boolean isDefault;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("services")
    @Expose
    private ArrayList<ServicesObj> services = null;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(String packageCost) {
        this.packageCost = packageCost;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public ArrayList<ServicesObj> getServices() {
        return services;
    }

    public void setServices(ArrayList<ServicesObj> services) {
        this.services = services;
    }
}
