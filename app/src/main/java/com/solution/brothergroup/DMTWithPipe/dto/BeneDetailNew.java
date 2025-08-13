package com.solution.brothergroup.DMTWithPipe.dto;

public class BeneDetailNew {

    private String mobileNo;
    private String beneName;
    private String ifsc;
    private String accountNo;
    private String BankID;
    private int transMode;
    private String bankName;
    private String beneID;

    public BeneDetailNew(String beneID) {

        this.beneID = beneID;
    }

    public BeneDetailNew(String mobileNo, String beneName, String ifsc, String accountNo, String bankName,String BankID) {
        this.mobileNo = mobileNo;
        this.beneName = beneName;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.BankID = BankID;
    }

    public BeneDetailNew(String mobileNo, String beneName, String ifsc, String accountNo, String bankName, String bankID, int transMode) {
        this.mobileNo = mobileNo;
        this.beneName = beneName;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.bankName = bankName;
        this.BankID = bankID;
        this.transMode = transMode;
    }


}
