package com.solution.brothergroup.Api.Object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AscReport implements Serializable
{
    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("opening")
    @Expose
    private int opening;
    @SerializedName("purchase")
    @Expose
    private int purchase;
    @SerializedName("sales")
    @Expose
    private int sales;
    @SerializedName("cCollection")
    @Expose
    private int cCollection;
    @SerializedName("closing")
    @Expose
    private int closing;
    @SerializedName("fundDeducted")
    @Expose
    private int fundDeducted;
    @SerializedName("return")
    @Expose
    private int _return;
    @SerializedName("requested")
    @Expose
    private int requested;
    @SerializedName("debited")
    @Expose
    private int debited;
    @SerializedName("debited2202")
    @Expose
    private int debited2202;
    @SerializedName("refunded")
    @Expose
    private int refunded;
    @SerializedName("commission")
    @Expose
    private double commission;
    @SerializedName("ccfCommission")
    @Expose
    private int ccfCommission;
    @SerializedName("surcharge")
    @Expose
    private int surcharge;
    @SerializedName("fundTransfered")
    @Expose
    private int fundTransfered;
    @SerializedName("otherCharge")
    @Expose
    private int otherCharge;
    @SerializedName("ccfCommDebited")
    @Expose
    private int ccfCommDebited;
    @SerializedName("expected")
    @Expose
    private int expected;
    @SerializedName("ccf")
    @Expose
    private int ccf;
    @SerializedName("roleID")
    @Expose
    private int roleID;
    @SerializedName("userID")
    @Expose
    private int userID;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("setTarget")
    @Expose
    private int setTarget;
    @SerializedName("targetTillDate")
    @Expose
    private int targetTillDate;
    @SerializedName("isGift")
    @Expose
    private boolean isGift;
    @SerializedName("dCommission")
    @Expose
    private int dCommission;
    @SerializedName("lsale")
    @Expose
    private int lsale;
    @SerializedName("lCollection")
    @Expose
    private int lCollection;
    @SerializedName("lsDate")
    @Expose
    private String lsDate;
    @SerializedName("lcDate")
    @Expose
    private String lcDate;
    @SerializedName("isPrepaid")
    @Expose
    private boolean isPrepaid;
    @SerializedName("isUtility")
    @Expose
    private boolean isUtility;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("uBalance")
    @Expose
    private int uBalance;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public int getOpening() {
        return opening;
    }

    public void setOpening(int opening) {
        this.opening = opening;
    }

    public int getPurchase() {
        return purchase;
    }

    public void setPurchase(int purchase) {
        this.purchase = purchase;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getcCollection() {
        return cCollection;
    }

    public void setcCollection(int cCollection) {
        this.cCollection = cCollection;
    }

    public int getClosing() {
        return closing;
    }

    public void setClosing(int closing) {
        this.closing = closing;
    }

    public int getFundDeducted() {
        return fundDeducted;
    }

    public void setFundDeducted(int fundDeducted) {
        this.fundDeducted = fundDeducted;
    }

    public int getReturn() {
        return _return;
    }

    public void setReturn(int _return) {
        this._return = _return;
    }

    public int getRequested() {
        return requested;
    }

    public void setRequested(int requested) {
        this.requested = requested;
    }

    public int getDebited() {
        return debited;
    }

    public void setDebited(int debited) {
        this.debited = debited;
    }

    public int getDebited2202() {
        return debited2202;
    }

    public void setDebited2202(int debited2202) {
        this.debited2202 = debited2202;
    }

    public int getRefunded() {
        return refunded;
    }

    public void setRefunded(int refunded) {
        this.refunded = refunded;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getCcfCommission() {
        return ccfCommission;
    }

    public void setCcfCommission(int ccfCommission) {
        this.ccfCommission = ccfCommission;
    }

    public int getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(int surcharge) {
        this.surcharge = surcharge;
    }

    public int getFundTransfered() {
        return fundTransfered;
    }

    public void setFundTransfered(int fundTransfered) {
        this.fundTransfered = fundTransfered;
    }

    public int getOtherCharge() {
        return otherCharge;
    }

    public void setOtherCharge(int otherCharge) {
        this.otherCharge = otherCharge;
    }

    public int getCcfCommDebited() {
        return ccfCommDebited;
    }

    public void setCcfCommDebited(int ccfCommDebited) {
        this.ccfCommDebited = ccfCommDebited;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getCcf() {
        return ccf;
    }

    public void setCcf(int ccf) {
        this.ccf = ccf;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSetTarget() {
        return setTarget;
    }

    public void setSetTarget(int setTarget) {
        this.setTarget = setTarget;
    }

    public int getTargetTillDate() {
        return targetTillDate;
    }

    public void setTargetTillDate(int targetTillDate) {
        this.targetTillDate = targetTillDate;
    }

    public boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(boolean isGift) {
        this.isGift = isGift;
    }

    public int getdCommission() {
        return dCommission;
    }

    public void setdCommission(int dCommission) {
        this.dCommission = dCommission;
    }

    public int getLsale() {
        return lsale;
    }

    public void setLsale(int lsale) {
        this.lsale = lsale;
    }

    public int getlCollection() {
        return lCollection;
    }

    public void setlCollection(int lCollection) {
        this.lCollection = lCollection;
    }

    public String getLsDate() {
        return lsDate;
    }

    public void setLsDate(String lsDate) {
        this.lsDate = lsDate;
    }

    public String getLcDate() {
        return lcDate;
    }

    public void setLcDate(String lcDate) {
        this.lcDate = lcDate;
    }

    public boolean getIsPrepaid() {
        return isPrepaid;
    }

    public void setIsPrepaid(boolean isPrepaid) {
        this.isPrepaid = isPrepaid;
    }

    public boolean getIsUtility() {
        return isUtility;
    }

    public void setIsUtility(boolean isUtility) {
        this.isUtility = isUtility;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int get_return() {
        return _return;
    }

    public boolean isPrepaid() {
        return isPrepaid;
    }

    public boolean isUtility() {
        return isUtility;
    }

    public int getuBalance() {
        return uBalance;
    }

    public void setuBalance(int uBalance) {
        this.uBalance = uBalance;
    }
}
