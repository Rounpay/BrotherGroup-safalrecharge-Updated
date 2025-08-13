package com.solution.brothergroup.Api.Response;

public class LedgerObject_m {
    private String transactionid;
    private String paymentid;
    private String terminalid;
    private String amount;
    private String merchantRefInvoiceNo;
    private String transactiontype;
    private String cardnumber;
    private String EntryDate;
    private String Type;
    private String status;

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMerchantRefInvoiceNo() {
        return merchantRefInvoiceNo;
    }

    public void setMerchantRefInvoiceNo(String merchantRefInvoiceNo) {
        this.merchantRefInvoiceNo = merchantRefInvoiceNo;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
