package com.solution.brothergroup.DMTWithPipe.dto;

public class RequestSendMoneyNew {

    private String beneID;
    private String mobileNo;
    private String ifsc;
    private String accountNo;
    private String amount;
    private String channel;
    private String bank;
    private String beneName;
    private String bankID;
    private String beneMobile;
    private String o;
    private String oid;

    public RequestSendMoneyNew(String o, String oid, String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank,
                            String bankID, String beneName, String beneMobile) {
        this.o = o;
        this.oid = oid;
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.bankID = bankID;
        this.beneName = beneName;
        this.beneMobile = beneMobile;
    }

}
