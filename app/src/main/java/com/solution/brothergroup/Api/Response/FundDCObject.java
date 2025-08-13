package com.solution.brothergroup.Api.Response;

public class FundDCObject {
    private String statusCode;
    private String description;
    private String transactionID;
    private String userName;
    private String mobileNo;
    private String entryDate;
    private String currentAmount;
    private String remark;
    private String amount;
    private Integer serviceTypeID = 0;
    private Integer walletID = 0;
    private String otherUser = "";


    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(String currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getServiceTypeID() {
        return serviceTypeID != null ? serviceTypeID : 0;
    }

    public Integer getWalletID() {
        return walletID != null ? walletID : 0;
    }

    public String getOtherUser() {
        return otherUser;
    }
}
