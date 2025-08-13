package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SettlementAccountData implements Serializable {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("bankID")
    @Expose
    public int bankID;
    @SerializedName("bankName")
    @Expose
    public String bankName;
    @SerializedName("ifsc")
    @Expose
    public String ifsc;
    @SerializedName("accountNumber")
    @Expose
    public String accountNumber;
    @SerializedName("accountHolder")
    @Expose
    public String accountHolder;
    @SerializedName("entryBy")
    @Expose
    public int entryBy;
    @SerializedName("entryDate")
    @Expose
    public String entryDate;
    @SerializedName("approvedBY")
    @Expose
    public String approvedBY;
    @SerializedName("approvalIp")
    @Expose
    public String approvalIp;
    @SerializedName("approvalDate")
    @Expose
    public String approvalDate;
    @SerializedName("actualname")
    @Expose
    public String actualname;
    @SerializedName("utr")
    @Expose
    public String utr;
    @SerializedName("apiid")
    @Expose
    public String apiid;
    @SerializedName("approvalStatus")
    @Expose
    public int approvalStatus;
    @SerializedName("verificationStatus")
    @Expose
    public int verificationStatus;
    @SerializedName("isdeleted")
    @Expose
    public String isdeleted;
    @SerializedName("isDefault")
    @Expose
    public boolean isDefault;
    @SerializedName("verificationText")
    @Expose
    public String verificationText;
    @SerializedName("approvalText")
    @Expose
    public String approvalText;
    @SerializedName("bankselect")
    @Expose
    public String bankselect;
    @SerializedName("requestdate")
    @Expose
    public String requestdate;
    @SerializedName("mobileNo")
    @Expose
    public String mobileNo;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("requestID")
    @Expose
    public int requestID;
    @SerializedName("loginID")
    @Expose
    public int loginID;
    @SerializedName("loginTypeID")
    @Expose
    public int loginTypeID;
    @SerializedName("userID")
    @Expose
    public int userID;
    @SerializedName("commonInt")
    @Expose
    public int commonInt;
    @SerializedName("commonInt2")
    @Expose
    public int commonInt2;
    @SerializedName("commonStr")
    @Expose
    public String commonStr;
    @SerializedName("commonStr2")
    @Expose
    public String commonStr2;
    @SerializedName("isListType")
    @Expose
    public boolean isListType;
    @SerializedName("str")
    @Expose
    public String str;
    @SerializedName("commonInt3")
    @Expose
    public int commonInt3;
    @SerializedName("commonDecimal")
    @Expose
    public double commonDecimal;
    @SerializedName("commonInt4")
    @Expose
    public int commonInt4;
    @SerializedName("commonStr3")
    @Expose
    public String commonStr3;
    @SerializedName("commonStr4")
    @Expose
    public String commonStr4;
    @SerializedName("commonBool")
    @Expose
    public boolean commonBool;
    @SerializedName("commonBool1")
    @Expose
    public boolean commonBool1;
    @SerializedName("commonBool2")
    @Expose
    public boolean commonBool2;
    @SerializedName("commonChar")
    @Expose
    public String commonChar;


    public int getId() {
        return id;
    }

    public int getBankID() {
        return bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public int getEntryBy() {
        return entryBy;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getApprovedBY() {
        return approvedBY;
    }

    public String getApprovalIp() {
        return approvalIp;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public String getActualname() {
        return actualname;
    }

    public String getUtr() {
        return utr;
    }

    public String getApiid() {
        return apiid;
    }

    public int getApprovalStatus() {
        return approvalStatus;
    }

    public int getVerificationStatus() {
        return verificationStatus;
    }

    public String getIsdeleted() {
        return isdeleted;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getVerificationText() {
        return verificationText;
    }

    public String getApprovalText() {
        return approvalText;
    }

    public String getBankselect() {
        return bankselect;
    }

    public String getRequestdate() {
        return requestdate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getName() {
        return name;
    }

    public int getRequestID() {
        return requestID;
    }

    public int getLoginID() {
        return loginID;
    }

    public int getLoginTypeID() {
        return loginTypeID;
    }

    public int getUserID() {
        return userID;
    }

    public int getCommonInt() {
        return commonInt;
    }

    public int getCommonInt2() {
        return commonInt2;
    }

    public String getCommonStr() {
        return commonStr;
    }

    public String getCommonStr2() {
        return commonStr2;
    }

    public boolean isListType() {
        return isListType;
    }

    public String getStr() {
        return str;
    }

    public int getCommonInt3() {
        return commonInt3;
    }

    public double getCommonDecimal() {
        return commonDecimal;
    }

    public int getCommonInt4() {
        return commonInt4;
    }

    public String getCommonStr3() {
        return commonStr3;
    }

    public String getCommonStr4() {
        return commonStr4;
    }

    public boolean isCommonBool() {
        return commonBool;
    }

    public boolean isCommonBool1() {
        return commonBool1;
    }

    public boolean isCommonBool2() {
        return commonBool2;
    }

    public String getCommonChar() {
        return commonChar;
    }
}

