package com.solution.brothergroup.UPIPayment.API;

/**
 * Created by Vishnu Agarwal on 04,January,2020
 */
public class AddFundRequest {

    String orderID, upiid;
    String checksum;
    int isImage;
    String bankID;
    String amount;
    String transactionID;
    String mobileNo;
    String chequeNo;
    String cardNo;
    String accountHolderName;
    String accountNumber;
    Integer paymentID;
    private String userID;
    private String loginTypeID;
    private String appid;
    private String imei;
    private String regKey;
    private String version;
    private String serialNo;
    private String sessionID;
    private String session;
    private String walletTypeID;

    public AddFundRequest(String upiid, String orderID, String checksum, int isImage, String bankID, String amount, String transactionID, String mobileNo, String chequeNo, String cardNo, String accountHolderName, String AccountNo, String PaymentModeID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String walletTypeID) {
        this.upiid = upiid;
        this.orderID = orderID;
        this.checksum = checksum;
        this.isImage = isImage;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.bankID = bankID;
        this.amount = amount;
        this.transactionID = transactionID;
        this.mobileNo = mobileNo;
        this.chequeNo = chequeNo;
        this.cardNo = cardNo;
        this.accountHolderName = accountHolderName;
        this.accountNumber = AccountNo;
        this.paymentID = Integer.parseInt(PaymentModeID);
        this.sessionID = sessionID;
        this.session = session;
        this.walletTypeID = walletTypeID;
    }

   /* int isImage;
    String bankID;
    String amount;
    String transactionID;
    String mobileNo;
    String chequeNo;
    String cardNo;
    String accountHolderName;
    String accountNumber;
    Integer paymentID;
    private String userID;
    private String loginTypeID;
    private String appid;
    private String imei;
    private String regKey;
    private String version;
    private String serialNo;
    private String sessionID;
    private String session;
    private String walletTypeID;

    public AddFundRequest(int isImage, String bankID, String amount, String transactionID, String mobileNo, String chequeNo, String cardNo, String accountHolderName, String AccountNo, String PaymentModeID, String userID, String loginTypeID, String appid, String imei, String regKey, String version, String serialNo, String sessionID, String session, String walletTypeID) {
       this.isImage=isImage;
        this.userID = userID;
        this.loginTypeID = loginTypeID;
        this.appid = appid;
        this.imei = imei;
        this.regKey = regKey;
        this.version = version;
        this.serialNo = serialNo;
        this.bankID = bankID;
        this.amount = amount;
        this.transactionID = transactionID;
        this.mobileNo = mobileNo;
        this.chequeNo = chequeNo;
        this.cardNo = cardNo;
        this.accountHolderName = accountHolderName;
        this.accountNumber = AccountNo;
        this.paymentID = Integer.parseInt(PaymentModeID);
        this.sessionID = sessionID;
        this.session = session;
        this.walletTypeID = walletTypeID;
    }*/
}
