package com.solution.brothergroup.Util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetail {

    @SerializedName("wid")
    @Expose
    private int wid;
    @SerializedName("supportEmail")
    @Expose
    private String supportEmail;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("channel")
    @Expose
    private String channel;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("bankName")
    @Expose
    private String bankName;
    @SerializedName("senderNo")
    @Expose
    private String senderNo;
    @SerializedName("beneName")
    @Expose
    private String beneName;
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pinCode")
    @Expose
    private String pinCode;
    @SerializedName("entryDate")
    @Expose
    private String entryDate;
    @SerializedName("lists")
    @Expose
    private java.util.List<ListOblect> lists = null;

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSenderNo() {
        return senderNo;
    }

    public void setSenderNo(String senderNo) {
        this.senderNo = senderNo;
    }

    public String getBeneName() {
        return beneName;
    }

    public void setBeneName(String beneName) {
        this.beneName = beneName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public java.util.List<ListOblect> getLists() {
        return lists;
    }

    public void setLists(java.util.List<ListOblect> lists) {
        this.lists = lists;
    }

}