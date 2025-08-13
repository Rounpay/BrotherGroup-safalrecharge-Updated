package com.solution.brothergroup.Fragments.dto;

import java.io.Serializable;

public class BalanceType implements Serializable {
    String name, amount;
    double balance;

    public BalanceType(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public BalanceType(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
