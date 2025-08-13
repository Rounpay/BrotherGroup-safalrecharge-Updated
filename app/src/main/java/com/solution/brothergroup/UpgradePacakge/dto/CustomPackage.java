package com.solution.brothergroup.UpgradePacakge.dto;

import java.util.ArrayList;
import java.util.List;

public class CustomPackage {
    String packageId;
    String packageName;
    String packageAmount;
    Boolean isDefault;
    List<ServicesObj> customList=new ArrayList<>();
    List<String> serviceNameList=new ArrayList<>();

    String packageButton;


    public List<String> getServiceNameList() {
        return serviceNameList;
    }

    public void setServiceNameList(List<String> serviceNameList) {
        this.serviceNameList = serviceNameList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(String packageAmount) {
        this.packageAmount = packageAmount;
    }

    public List<ServicesObj> getCustomList() {
        return customList;
    }

    public void setCustomList(List<ServicesObj> customList) {
        this.customList = customList;
    }

    public String getPackageButton() {
        return packageButton;
    }

    public void setPackageButton(String packageButton) {
        this.packageButton = packageButton;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
