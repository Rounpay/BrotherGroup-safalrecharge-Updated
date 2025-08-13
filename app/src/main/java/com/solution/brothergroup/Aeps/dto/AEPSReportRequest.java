package com.solution.brothergroup.Aeps.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.brothergroup.Api.Request.BasicRequest;

public class AEPSReportRequest extends BasicRequest {

    @SerializedName("topRows")
    @Expose
    private Integer topRows;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("oid")
    @Expose
    private Integer oid;
    @SerializedName("apiid")
    @Expose
    private Integer apiid;
    @SerializedName("fromDate")
    @Expose
    private Object fromDate;
    @SerializedName("toDate")
    @Expose
    private Object toDate;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("accountNo")
    @Expose
    private String accountNo;
    @SerializedName("childMobile")
    @Expose
    private String childMobile;
    @SerializedName("isExport")
    @Expose
    private Boolean isExport;
    @SerializedName("isRecent")
    @Expose
    private Boolean isRecent;
    @SerializedName("opTypeID")
    @Expose
    private Integer opTypeID;




    public Integer getTopRows() {
        return topRows;
    }

    public void setTopRows(Integer topRows) {
        this.topRows = topRows;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getApiid() {
        return apiid;
    }

    public void setApiid(Integer apiid) {
        this.apiid = apiid;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getChildMobile() {
        return childMobile;
    }

    public void setChildMobile(String childMobile) {
        this.childMobile = childMobile;
    }

    public Boolean getExport() {
        return isExport;
    }

    public void setExport(Boolean export) {
        isExport = export;
    }

    public Boolean getRecent() {
        return isRecent;
    }

    public void setRecent(Boolean recent) {
        isRecent = recent;
    }

    public Integer getOpTypeID() {
        return opTypeID;
    }

    public void setOpTypeID(Integer opTypeID) {
        this.opTypeID = opTypeID;
    }
}
