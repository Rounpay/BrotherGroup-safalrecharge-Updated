package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BalanceData implements Serializable {
    @SerializedName("isQRMappedToUser")
    @Expose
    public boolean isQRMappedToUser;
    @SerializedName("isPackageDeducionForRetailor")
    @Expose
    public boolean isPackageDeducionForRetailor;
    @SerializedName("balance")
    @Expose
    public double balance;
    @SerializedName("isBalance")
    @Expose
    public boolean isBalance;
    @SerializedName("uBalance")
    @Expose
    public double uBalance;
    @SerializedName("isUBalance")
    @Expose
    public boolean isUBalance;
    @SerializedName("bBalance")
    @Expose
    public double bBalance;
    @SerializedName("isBBalance")
    @Expose
    public boolean isBBalance;
    @SerializedName("cBalance")
    @Expose
    public double cBalance;
    @SerializedName("isCBalance")
    @Expose
    public boolean isCBalance;
    @SerializedName("idBalnace")
    @Expose
    public double idBalnace;
    @SerializedName("isIDBalance")
    @Expose
    public boolean isIDBalance;
    @SerializedName("aepsBalnace")
    @Expose
    public double aepsBalnace;
    @SerializedName("isAEPSBalance")
    @Expose
    public boolean isAEPSBalance;
    @SerializedName("packageBalnace")
    @Expose
    public double packageBalnace;
    @SerializedName("isPacakgeBalance")
    @Expose
    public boolean isPacakgeBalance;
    @SerializedName("isP")
    @Expose
    public boolean isP;
    @SerializedName("isPN")
    @Expose
    public boolean isPN;
    @SerializedName("isLowBalance")
    @Expose
    public boolean isLowBalance;

    @SerializedName("osBalance")
    @Expose
    public double osBalance;

    @SerializedName("isBalanceFund")
    @Expose
    private boolean isBalanceFund;

    @SerializedName("isUBalanceFund")
    @Expose
    private boolean isUBalanceFund;

    @SerializedName("isBBalanceFund")
    @Expose
    private boolean isBBalanceFund;

    @SerializedName("isCBalanceFund")
    @Expose
    private boolean isCBalanceFund;

    @SerializedName("isIDBalanceFund")
    @Expose
    private boolean isIDBalanceFund;

    @SerializedName("isPacakgeBalanceFund")
    @Expose
    private boolean isPacakgeBalanceFund;

    @SerializedName("pacakgeBalance")
    @Expose
    private double pacakgeBalance;

    public boolean isPackageDeducionForRetailor() {
        return isPackageDeducionForRetailor;
    }

    public void setPackageDeducionForRetailor(boolean packageDeducionForRetailor) {
        isPackageDeducionForRetailor = packageDeducionForRetailor;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isBalance() {
        return isBalance;
    }

    public void setBalance(boolean balance) {
        isBalance = balance;
    }

    public double getuBalance() {
        return uBalance;
    }

    public boolean isQRMappedToUser() {
        return isQRMappedToUser;
    }

    public void setuBalance(double uBalance) {
        this.uBalance = uBalance;
    }

    public boolean isUBalance() {
        return isUBalance;
    }

    public void setUBalance(boolean UBalance) {
        isUBalance = UBalance;
    }

    public double getbBalance() {
        return bBalance;
    }

    public void setbBalance(double bBalance) {
        this.bBalance = bBalance;
    }

    public boolean isBBalance() {
        return isBBalance;
    }

    public void setBBalance(boolean BBalance) {
        isBBalance = BBalance;
    }

    public double getcBalance() {
        return cBalance;
    }

    public void setcBalance(double cBalance) {
        this.cBalance = cBalance;
    }

    public boolean isCBalance() {
        return isCBalance;
    }

    public void setCBalance(boolean CBalance) {
        isCBalance = CBalance;
    }

    public double getIdBalnace() {
        return idBalnace;
    }

    public void setIdBalnace(double idBalnace) {
        this.idBalnace = idBalnace;
    }

    public boolean isIDBalance() {
        return isIDBalance;
    }

    public void setIDBalance(boolean IDBalance) {
        isIDBalance = IDBalance;
    }

    public double getAepsBalnace() {
        return aepsBalnace;
    }

    public void setAepsBalnace(double aepsBalnace) {
        this.aepsBalnace = aepsBalnace;
    }

    public boolean isAEPSBalance() {
        return isAEPSBalance;
    }

    public void setAEPSBalance(boolean AEPSBalance) {
        isAEPSBalance = AEPSBalance;
    }

    public double getPackageBalnace() {
        return packageBalnace;
    }

    public void setPackageBalnace(double packageBalnace) {
        this.packageBalnace = packageBalnace;
    }

    public boolean isPacakgeBalance() {
        return isPacakgeBalance;
    }

    public void setPacakgeBalance(boolean pacakgeBalance) {
        isPacakgeBalance = pacakgeBalance;
    }

    public boolean isP() {
        return isP;
    }

    public void setP(boolean p) {
        isP = p;
    }

    public boolean isPN() {
        return isPN;
    }

    public void setPN(boolean PN) {
        isPN = PN;
    }

    public boolean isLowBalance() {
        return isLowBalance;
    }

    public void setLowBalance(boolean lowBalance) {
        isLowBalance = lowBalance;
    }

    public double getOsBalance() {
        return osBalance;
    }

    public void setOsBalance(double osBalance) {
        this.osBalance = osBalance;
    }

    public boolean isBalanceFund() {
        return isBalanceFund;
    }

    public void setBalanceFund(boolean balanceFund) {
        isBalanceFund = balanceFund;
    }

    public boolean isUBalanceFund() {
        return isUBalanceFund;
    }

    public void setUBalanceFund(boolean UBalanceFund) {
        isUBalanceFund = UBalanceFund;
    }

    public boolean isBBalanceFund() {
        return isBBalanceFund;
    }

    public void setBBalanceFund(boolean BBalanceFund) {
        isBBalanceFund = BBalanceFund;
    }

    public boolean isCBalanceFund() {
        return isCBalanceFund;
    }

    public void setCBalanceFund(boolean CBalanceFund) {
        isCBalanceFund = CBalanceFund;
    }

    public boolean isIDBalanceFund() {
        return isIDBalanceFund;
    }

    public void setIDBalanceFund(boolean IDBalanceFund) {
        isIDBalanceFund = IDBalanceFund;
    }

    public boolean isPacakgeBalanceFund() {
        return isPacakgeBalanceFund;
    }

    public void setPacakgeBalanceFund(boolean pacakgeBalanceFund) {
        isPacakgeBalanceFund = pacakgeBalanceFund;
    }

    public double getPacakgeBalance() {
        return pacakgeBalance;
    }

    public void setPacakgeBalance(double pacakgeBalance) {
        this.pacakgeBalance = pacakgeBalance;
    }
}
