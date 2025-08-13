package com.solution.brothergroup.FingPayAEPS.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MiniStatements implements Serializable {
    @SerializedName(value = "TransactionDate", alternate = "transactionDate")
    @Expose
    public String transactionDate;
    @SerializedName(value = "TransactionType", alternate = "transactionType")
    @Expose
    public String transactionType;
    @SerializedName(value = "Amount", alternate = "amount")
    @Expose
    public String amount;
    @SerializedName(value = "Narration", alternate = "narration")
    @Expose
    public String narration;

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getAmount() {
        return amount;
    }

    public String getNarration() {
        return narration;
    }

    public MiniStatements(String transactionDate, String transactionType, String amount, String narration) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.amount = amount;
        this.narration = narration;
    }
}
