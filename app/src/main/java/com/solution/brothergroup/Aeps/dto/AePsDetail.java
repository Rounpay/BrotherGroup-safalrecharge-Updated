package com.solution.brothergroup.Aeps.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AePsDetail {
    @SerializedName("_ServiceID")
    @Expose
    private Integer serviceID;
    @SerializedName("tid")
    @Expose
    private Integer tid;
    @SerializedName("transactionID")
    @Expose
    private String transactionID;
    @SerializedName("prefix")
    @Expose
    private Object prefix;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("outletNo")
    @Expose
    private String outletNo;
    @SerializedName("outlet")
    @Expose
    private String outlet;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("oid")
    @Expose
    private Integer oid;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("lastBalance")
    @Expose
    private Double lastBalance;
    @SerializedName("requestedAmount")
    @Expose
    private Double requestedAmount;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("slabCommType")
    @Expose
    private String slabCommType;
    @SerializedName("commission")
    @Expose
    private Double commission;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("api")
    @Expose
    private String api;
    @SerializedName("liveID")
    @Expose
    private String liveID;
    @SerializedName("type_")
    @Expose
    private String type;

    @SerializedName("_Type")
    @Expose
    private String Type;

    @SerializedName("vendorID")
    @Expose
    private String vendorID;
    @SerializedName("apiRequestID")
    @Expose
    private String apiRequestID;
    @SerializedName("modifyDate")
    @Expose
    private String modifyDate;
    @SerializedName("optional1")
    @Expose
    private String optional1;
    @SerializedName("optional2")
    @Expose
    private String optional2;
    @SerializedName("optional3")
    @Expose
    private String optional3;
    @SerializedName("optional4")
    @Expose
    private String optional4;
    @SerializedName("display1")
    @Expose
    private String display1;
    @SerializedName("display2")
    @Expose
    private String display2;
    @SerializedName("display3")
    @Expose
    private String display3;
    @SerializedName("display4")
    @Expose
    private String display4;
    @SerializedName("refundStatus")
    @Expose
    private Integer refundStatus;

    @SerializedName("refundStatus_")
    @Expose
    private String refundStatus_;
    @SerializedName("isWTR")
    @Expose
    private Boolean isWTR;
    @SerializedName("commType")
    @Expose
    private Boolean commType;
    @SerializedName("gstAmount")
    @Expose
    private Double gstAmount;
    @SerializedName("tdsAmount")
    @Expose
    private Double tdsAmount;
    @SerializedName("customerNo")
    @Expose
    private Object customerNo;
    @SerializedName("ccName")
    @Expose
    private String ccName;
    @SerializedName("ccMobile")
    @Expose
    private String ccMobile;
    @SerializedName("requestMode")
    @Expose
    private String requestMode;


    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Object getPrefix() {
        return prefix;
    }

    public void setPrefix(Object prefix) {
        this.prefix = prefix;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public String getOutletNo() {
        return outletNo;
    }

    public void setOutletNo(String outletNo) {
        this.outletNo = outletNo;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getLastBalance() {
        return lastBalance;
    }

    public void setLastBalance(Double lastBalance) {
        this.lastBalance = lastBalance;
    }

    public Double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getSlabCommType() {
        return slabCommType;
    }

    public void setSlabCommType(String slabCommType) {
        this.slabCommType = slabCommType;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getLiveID() {
        return liveID;
    }

    public void setLiveID(String liveID) {
        this.liveID = liveID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public void setApiRequestID(String apiRequestID) {
        this.apiRequestID = apiRequestID;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOptional1() {
        return optional1;
    }

    public void setOptional1(String optional1) {
        this.optional1 = optional1;
    }

    public String getOptional2() {
        return optional2;
    }

    public void setOptional2(String optional2) {
        this.optional2 = optional2;
    }

    public String getOptional3() {
        return optional3;
    }

    public void setOptional3(String optional3) {
        this.optional3 = optional3;
    }

    public String getOptional4() {
        return optional4;
    }

    public void setOptional4(String optional4) {
        this.optional4 = optional4;
    }

    public String getDisplay1() {
        return display1;
    }

    public void setDisplay1(String display1) {
        this.display1 = display1;
    }

    public String getDisplay2() {
        return display2;
    }

    public void setDisplay2(String display2) {
        this.display2 = display2;
    }

    public String getDisplay3() {
        return display3;
    }

    public void setDisplay3(String display3) {
        this.display3 = display3;
    }

    public String getDisplay4() {
        return display4;
    }

    public void setDisplay4(String display4) {
        this.display4 = display4;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatus_() {
        return refundStatus_;
    }

    public void setRefundStatus_(String refundStatus_) {
        this.refundStatus_ = refundStatus_;
    }

    public Boolean getWTR() {
        return isWTR;
    }

    public void setWTR(Boolean WTR) {
        isWTR = WTR;
    }

    public Boolean getCommType() {
        return commType;
    }

    public void setCommType(Boolean commType) {
        this.commType = commType;
    }

    public Double getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(Double gstAmount) {
        this.gstAmount = gstAmount;
    }

    public Double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(Double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public Object getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Object customerNo) {
        this.customerNo = customerNo;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getCcMobile() {
        return ccMobile;
    }

    public void setCcMobile(String ccMobile) {
        this.ccMobile = ccMobile;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }
}
