package com.solution.brothergroup.UserDayBook.dto;

/**
 * Created by Vishnu on 15-04-2017.
 */

public class UserDayBookLedger {

    private String Expectedbal;
    private String OpeningBal;
    private String FundPurchase;
    private String FundSales;
    private String RequestedAmount;
    private String Amount;
    private String Commission;
    private String HCommission;
    private String Refund;


    public String getExpectedbal() {
        return Expectedbal;
    }

    public void setExpectedbal(String expectedbal) {
        Expectedbal = expectedbal;
    }

    public String getFundPurchase() {
        return FundPurchase;
    }

    public void setFundPurchase(String fundPurchase) {
        FundPurchase = fundPurchase;
    }

    public String getFundSales() {
        return FundSales;
    }

    public void setFundSales(String fundSales) {
        FundSales = fundSales;
    }

    public String getRefund() {
        return Refund;
    }

    public void setRefund(String refund) {
        Refund = refund;
    }

    public String getOpeningBal() {
        return OpeningBal;
    }

    public void setOpeningBal(String openingBal) {
        OpeningBal = openingBal;
    }

    public String getRequestedAmount() {
        return RequestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        RequestedAmount = requestedAmount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCommission() {
        return Commission;
    }

    public void setCommission(String commission) {
        Commission = commission;
    }

    public String getHCommission() {
        return HCommission;
    }

    public void setHCommission(String HCommission) {
        this.HCommission = HCommission;
    }
}
