package com.solution.brothergroup.Authentication.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceData {
    @SerializedName("balance")
    @Expose
    public Double balance;
    @SerializedName("isBalance")
    @Expose
    public Boolean isBalance;
    @SerializedName("uBalance")
    @Expose
    public Double uBalance;
    @SerializedName("isUBalance")
    @Expose
    public Boolean isUBalance;
    @SerializedName("bBalance")
    @Expose
    public Double bBalance;
    @SerializedName("isBBalance")
    @Expose
    public Boolean isBBalance;
    @SerializedName("cBalance")
    @Expose
    public Double cBalance;
    @SerializedName("isCBalance")
    @Expose
    public Boolean isCBalance;
    @SerializedName("idBalnace")
    @Expose
    public Double idBalnace;
    @SerializedName("isIDBalance")
    @Expose
    public Boolean isIDBalance;
    @SerializedName("aepsBalnace")
    @Expose
    public Double aepsBalnace;
    @SerializedName("isAEPSBalance")
    @Expose
    public Boolean isAEPSBalance;
    @SerializedName("packageBalnace")
    @Expose
    public Double packageBalnace;
    @SerializedName("isPacakgeBalance")
    @Expose
    public Boolean isPacakgeBalance;
    @SerializedName("isP")
    @Expose
    public Boolean isP;
    @SerializedName("isPN")
    @Expose
    public Boolean isPN;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Boolean balance) {
        isBalance = balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getuBalance() {
        return uBalance;
    }

    public void setuBalance(Double uBalance) {
        this.uBalance = uBalance;
    }

    public Boolean getUBalance() {
        return isUBalance;
    }

    public void setUBalance(Boolean UBalance) {
        isUBalance = UBalance;
    }

    public Double getbBalance() {
        return bBalance;
    }

    public void setbBalance(Double bBalance) {
        this.bBalance = bBalance;
    }

    public Boolean getBBalance() {
        return isBBalance;
    }

    public void setBBalance(Boolean BBalance) {
        isBBalance = BBalance;
    }

    public Double getcBalance() {
        return cBalance;
    }

    public void setcBalance(Double cBalance) {
        this.cBalance = cBalance;
    }

    public Boolean getCBalance() {
        return isCBalance;
    }

    public void setCBalance(Boolean CBalance) {
        isCBalance = CBalance;
    }

    public Double getIdBalnace() {
        return idBalnace;
    }

    public void setIdBalnace(Double idBalnace) {
        this.idBalnace = idBalnace;
    }

    public Boolean getIDBalance() {
        return isIDBalance;
    }

    public void setIDBalance(Boolean IDBalance) {
        isIDBalance = IDBalance;
    }

    public Double getAepsBalnace() {
        return aepsBalnace;
    }

    public void setAepsBalnace(Double aepsBalnace) {
        this.aepsBalnace = aepsBalnace;
    }

    public Boolean getAEPSBalance() {
        return isAEPSBalance;
    }

    public void setAEPSBalance(Boolean AEPSBalance) {
        isAEPSBalance = AEPSBalance;
    }

    public Double getPackageBalnace() {
        return packageBalnace;
    }

    public void setPackageBalnace(Double packageBalnace) {
        this.packageBalnace = packageBalnace;
    }

    public Boolean getPacakgeBalance() {
        return isPacakgeBalance;
    }

    public void setPacakgeBalance(Boolean pacakgeBalance) {
        isPacakgeBalance = pacakgeBalance;
    }

    public Boolean getP() {
        return isP;
    }

    public void setP(Boolean p) {
        isP = p;
    }

    public Boolean getPN() {
        return isPN;
    }

    public void setPN(Boolean PN) {
        isPN = PN;
    }
}
