package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AssignedOpType {
    @SerializedName("isServiceActive")
    @Expose
    boolean isServiceActive;

    @SerializedName("upline")
    @Expose
    String upline;
    @SerializedName("uplineMobile")
    @Expose
    String uplineMobile;
    @SerializedName("ccContact")
    @Expose
    String ccContact;

    @SerializedName("serviceID")
    @Expose
    private int serviceID;
    @SerializedName("parentID")
    @Expose
    private int parentID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("isActive")
    @Expose
    private boolean isActive;
    @SerializedName("isDisplayService")
    @Expose
    private boolean isDisplayService;
    @SerializedName("subOpTypeList")
    @Expose
    private ArrayList<AssignedOpType> subOpTypeList;
    public AssignedOpType(int serviceID, String name, boolean isActive, boolean isServiceActive) {
        this.serviceID = serviceID;
        this.name = name;
        this.isActive = isActive;
        this.isServiceActive = isServiceActive;
    }

    public AssignedOpType(int serviceID, int parentID, String name, String service, boolean isServiceActive, boolean isActive, boolean isDisplayService, String upline, String uplineMobile, String ccContact, ArrayList<AssignedOpType> subOpTypeList) {
        this.serviceID = serviceID;
        this.parentID = parentID;
        this.name = name;
        this.service = service;
        this.isServiceActive = isServiceActive;
        this.isActive = isActive;
        this.isDisplayService = isDisplayService;
        this.subOpTypeList = subOpTypeList;
        this.upline = upline;
        this.uplineMobile = uplineMobile;
        this.ccContact = ccContact;
    }

    public String getUpline() {
        return upline;
    }

    public String getUplineMobile() {
        return uplineMobile;
    }

    public String getCcContact() {
        return ccContact;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getParentID() {
        return parentID;
    }

    public String getService() {
        return service;
    }


    public boolean getIsDisplayService() {
        return isDisplayService;
    }

    public boolean getIsServiceActive() {
        return isServiceActive;
    }

    public ArrayList<AssignedOpType> getSubOpTypeList() {
        return subOpTypeList;
    }
}
