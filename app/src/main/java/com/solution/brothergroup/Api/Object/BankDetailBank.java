package com.solution.brothergroup.Api.Object;

/**
 * Created by Vishnu on 17-05-2017.
 */

public class BankDetailBank {

    private String id;
    private String BankName;
    private String AccountNumber;
    private String Islogin;
    private String BankId;
    private String SNo;


    public String getBankId() {
        return BankId;
    }

    public void setBankId(String bankId) {
        BankId = bankId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getIslogin() {
        return Islogin;
    }

    public void setIslogin(String islogin) {
        Islogin = islogin;
    }

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
    }
}
