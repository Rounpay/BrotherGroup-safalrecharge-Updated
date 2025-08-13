package com.solution.brothergroup.Api.Object;

/**
 * Created by Vishnu on 10-04-2017.
 */

public class LedgerObject {
    int serviceID;
    boolean lType;
    String userID;
    private String tid;
    private String description;
    private String user;
    private String id;
    private String entryDate;
    private String lastAmount;
    private String credit;
    private String debit;
    private String curentBalance;
    private String mobileNo;
    private String remark;

    public int getServiceID() {
        return serviceID;
    }

    public boolean islType() {
        return lType;
    }

    public String getUserID() {
        return userID;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getLastAmount() {
        return lastAmount;
    }

    public void setLastAmount(String lastAmount) {
        this.lastAmount = lastAmount;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCurentBalance() {
        return curentBalance;
    }

    public void setCurentBalance(String curentBalance) {
        this.curentBalance = curentBalance;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
