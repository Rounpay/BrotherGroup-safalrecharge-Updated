package com.solution.brothergroup.Util;

public class RequestSendMoney {

    private String beneID;
    private String mobileNo;
    private String ifsc;
    private String accountNo;
    private String amount;
    private String channel;
    private String bank;
    private String beneName;

    public RequestSendMoney(String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank,String beneName) {
        this.beneID = beneID;
        this.mobileNo = mobileNo;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.amount = amount;
        this.channel = channel;
        this.bank = bank;
        this.beneName = beneName;
    }

}
